package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceHeaderTableModel;
import model.InvoiceLine;
import view.HomePageGUI;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import java.util.Vector;

import static model.InvoiceHeader.dateFormat;

public class HomePageController {
    private final HomePageGUI homePageGUI;
    private InvoiceHeader currentHeader;
    private InvoiceLine currentItem;

    public HomePageController(HomePageGUI homePageGUI)
    {
        this.homePageGUI = homePageGUI;
        currentHeader = null;
        currentItem = null;
        initController();
    }

    private void initController() {
        initMenuBar();
        initLeftPanel();
        initRightPanel();

    }

    private void initRightPanel() {
        // Keep up with current selected header and save it in currentItem variable
        homePageGUI.getInvoiceItemsTable().getSelectionModel().addListSelectionListener(lse -> {
            int selected = homePageGUI.getInvoiceItemsTable().getSelectedRow();
            currentItem = homePageGUI.getInvoiceItemsTableModel().getData().get(selected);
            loadItemValues();
        });

        // Add new item
        homePageGUI.getAddItemBtn().addActionListener(actionEvent -> {
            InvoiceLine item = new InvoiceLine();
            item.setInvoiceNumber(currentHeader.getInvoiceNum());
            if(!validateItem())
                return;
            item.setItemName(homePageGUI.getItemNameTxt().getText());
            if(!homePageGUI.getPriceTxt().getText().isEmpty())
                item.setItemPrice(Float.parseFloat(homePageGUI.getPriceTxt().getText()));
            if(!homePageGUI.getCountTxt().getText().isEmpty())
                item.setCount(Integer.parseInt(homePageGUI.getCountTxt().getText()));
            homePageGUI.getInvoiceItemsTableModel().getData().add(item);
            homePageGUI.getInvoiceItemsTable().updateUI();

            float headerTotal = Float.parseFloat(homePageGUI.getTotalLbl().getText()) + item.getTotal();
            homePageGUI.getTotalLbl().setText(String.valueOf(headerTotal));
        });

        // save changes to header
        homePageGUI.getSaveBtn().addActionListener(actionEvent -> {
            currentHeader.setCustomerName(homePageGUI.getCustomerNameTxt().getText());
            try {
                currentHeader.setInvoiceDate(dateFormat.parse(homePageGUI.getDateTxt().getText()));
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(homePageGUI, "Wrong date format");
            }
            currentHeader.setTotal(Float.parseFloat(homePageGUI.getTotalLbl().getText()));
            currentHeader.getInvoiceLines().clear();
            currentHeader.getInvoiceLines().addAll(homePageGUI.getInvoiceItemsTableModel().getData());
            homePageGUI.getInvoicesTable().updateUI();
        });

        // cancel changes to header
        homePageGUI.getCancelBtn().addActionListener(actionEvent -> {
            loadInvoiceItems();
        });

        // Update selected item
        homePageGUI.getUpdateBtn().addActionListener(actionEvent -> {
            if(currentItem == null)
            {
                JOptionPane.showMessageDialog(homePageGUI, "No Item Selected");
                return;
            }
            if(!validateItem())
                return;

            float prevTotal = currentItem.getTotal();
            currentItem.setItemName(homePageGUI.getItemNameTxt().getText());
            if(!homePageGUI.getPriceTxt().getText().isEmpty())
                currentItem.setItemPrice(Float.parseFloat(homePageGUI.getPriceTxt().getText()));
            if(!homePageGUI.getCountTxt().getText().isEmpty())
                currentItem.setCount(Integer.parseInt(homePageGUI.getCountTxt().getText()));
            homePageGUI.getInvoiceItemsTable().updateUI();

            float totalDiff = currentItem.getTotal() - prevTotal;
            float headerTotal = currentHeader.getTotal() - totalDiff;
            homePageGUI.getTotalLbl().setText(String.valueOf(headerTotal));
        });
    }

    private boolean validateItem() {
        if(homePageGUI.getItemNameTxt().getText().length() < 1) {
            JOptionPane.showMessageDialog(homePageGUI, "Name cannot be empty");
            return false;
        }
        if(!homePageGUI.getPriceTxt().getText().matches("[0-9]+(\\.[0-9]+)?")) {
            JOptionPane.showMessageDialog(homePageGUI, "Invalid Price");
            return false;
        }
        if(!homePageGUI.getCountTxt().getText().matches("[0-9]+"))
        {
            JOptionPane.showMessageDialog(homePageGUI, "Invalid Count");
            return false;
        }
        return true;
    }

    private void initLeftPanel() {
        // Create new Invoice Header
        homePageGUI.getCreateInvoiceBtn().addActionListener(actionEvent -> {
            ((InvoiceHeaderTableModel)homePageGUI.getInvoicesTable().getModel()).getData().addElement(new InvoiceHeader());
            homePageGUI.getInvoicesTable().updateUI();
        });

        // Keep up with current selected header and save it in currentHeader Variable
        homePageGUI.getInvoicesTable().getSelectionModel().addListSelectionListener(lse -> {
            int selected = homePageGUI.getInvoicesTable().getSelectedRow();
            try {
                currentHeader = homePageGUI.getInvoicesTableModel().getData().get(selected);
                loadInvoiceItems();
            } catch (ArrayIndexOutOfBoundsException ex) {
                clearInvoiceItems();
            }
        });

        // Delete selected header
        homePageGUI.getDeleteInvoiceBtn().addActionListener(actionEvent -> {
            if(homePageGUI.getInvoicesTable().getModel().getRowCount() < 1) {
                JOptionPane.showMessageDialog(homePageGUI, "No Rows Available");
                return;
            }

            if(currentHeader == null) {
                JOptionPane.showMessageDialog(homePageGUI, "No Row Selected");
                return;
            }
            homePageGUI.getInvoicesTableModel().getData().removeElement(currentHeader);
            homePageGUI.getInvoicesTableModel().fireTableDataChanged();
            homePageGUI.getInvoicesTable().updateUI();
        });
    }

    private void initMenuBar() {
        // Load File Action
        homePageGUI.getLoadFile().addActionListener(actionEvent -> {
            Vector<InvoiceHeader> headers;
            try {
                File invoiceHeaderCSV = getCSVFile("Select Invoice Header CSV File");
                File invoiceLineCSV = getCSVFile("Select Invoice Lines CSV File");

                headers = FileOperations.loadFile(invoiceLineCSV, invoiceHeaderCSV);
                homePageGUI.getInvoicesTableModel().setInvoiceHeaders(headers);
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(homePageGUI, "Operation canceled");
            } catch (IOException e) {
                JOptionPane.showMessageDialog(homePageGUI, "IO Error\n" + e.getMessage());
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(homePageGUI, "Parse Error\n" + e.getMessage());
            }
        });

        // Save Data File Dialog
        homePageGUI.getSaveFile().addActionListener(actionEvent -> {
            JFileChooser fileChooser = homePageGUI.getFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Choose Folder to save csv files");
            fileChooser.setAcceptAllFileFilterUsed(false);

            if(fileChooser.showSaveDialog(homePageGUI) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(homePageGUI, "Operation Canceled");
                return;
            }

            boolean saved = true;
            try {
                FileOperations.saveData(fileChooser.getSelectedFile(), homePageGUI.getInvoicesTableModel().getData());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(homePageGUI, "Error Saving Data\n" + e.getMessage());
                saved = false;
            }
            if(saved) {
                JOptionPane.showMessageDialog(homePageGUI, "Saved Successfully");
            }
        });
    }

    private File getCSVFile(String dialogTitle) throws NullPointerException {
        JFileChooser fileChooser = homePageGUI.getFileChooser();
        fileChooser.setDialogTitle(dialogTitle);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.exists() && file.canRead() && (file.getName().toLowerCase(Locale.ROOT).endsWith(".csv") || file.isDirectory());
            }

            @Override
            public String getDescription() {
                return "CSV files";
            }
        });


        if(fileChooser.showOpenDialog(homePageGUI) != JFileChooser.APPROVE_OPTION)
            throw new NullPointerException();
        return fileChooser.getSelectedFile();
    }

    private void loadItemValues() {
        homePageGUI.getItemNameTxt().setText(currentItem.getItemName());
        homePageGUI.getPriceTxt().setText(String.valueOf(currentItem.getItemPrice()));
        homePageGUI.getCountTxt().setText(String.valueOf(currentItem.getCount()));
    }

    private void loadInvoiceItems() {
        homePageGUI.getInvoiceItemsTableModel().getData().clear();
        homePageGUI.getInvoiceItemsTableModel().getData().addAll(currentHeader.getInvoiceLines());
        homePageGUI.getInvoiceItemsTable().updateUI();
        homePageGUI.getInvoiceNumberLbl().setText(String.valueOf(currentHeader.getInvoiceNum()));
        homePageGUI.getDateTxt().setText(dateFormat.format(currentHeader.getInvoiceDate()));
        homePageGUI.getCustomerNameTxt().setText(currentHeader.getCustomerName());
        homePageGUI.getTotalLbl().setText(String.valueOf(currentHeader.getTotal()));
    }

    private void clearInvoiceItems() {
        homePageGUI.getInvoiceItemsTableModel().getData().clear();
        homePageGUI.getInvoiceItemsTable().updateUI();
        homePageGUI.getInvoiceNumberLbl().setText("");
        homePageGUI.getDateTxt().setText("");
        homePageGUI.getCustomerNameTxt().setText("");
        homePageGUI.getTotalLbl().setText("0.0");
    }
}