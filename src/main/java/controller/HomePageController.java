package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceHeaderTableModel;
import model.InvoiceLine;
import view.HomePageGUI;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.DateFormatter;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Vector;

import static model.Globals.dateFormat;

public class HomePageController {
    HomePageGUI homePageGUI;
    InvoiceHeader currentHeader;

    public HomePageController(HomePageGUI homePageGUI)
    {
        this.homePageGUI = homePageGUI;
        initController();
    }

    private void initController() {
        homePageGUI.getLoadFile().addActionListener(actionEvent -> {
            Vector<InvoiceHeader> headers;
            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File file) {
                        if(file.exists() && file.canRead() && ( file.getName().toLowerCase(Locale.ROOT).endsWith(".csv") || file.isDirectory()))
                            return true;
                        return false;
                    }

                    @Override
                    public String getDescription() {
                        return "CSV files";
                    }
                });
                fileChooser.setDialogTitle("Select Invoice Lines CSV File");
                fileChooser.setAcceptAllFileFilterUsed(false);

                if(fileChooser.showOpenDialog(homePageGUI) != JFileChooser.APPROVE_OPTION)
                    return;
                File invoiceLineCSV = fileChooser.getSelectedFile();

                fileChooser.setDialogTitle("Select Invoice Header CSV File");

                if(fileChooser.showOpenDialog(homePageGUI) != JFileChooser.APPROVE_OPTION)
                    return;
                File invoiceHeaderCSV = fileChooser.getSelectedFile();

                headers = FileOperations.loadFile(invoiceLineCSV, invoiceHeaderCSV);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(homePageGUI, "IO Error\n" + e.getMessage());
                return;
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(homePageGUI, "Parse Error\n" + e.getMessage());
                return;
            }

            ((InvoiceHeaderTableModel)homePageGUI.getInvoicesTable().getModel()).setInvoiceHeaders(headers);
        });

        homePageGUI.getSaveFile().addActionListener(actionEvent -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fileChooser.setDialogTitle("Choose Folder to save csv files");
            fileChooser.setAcceptAllFileFilterUsed(false);

            if(fileChooser.showSaveDialog(homePageGUI) != JFileChooser.APPROVE_OPTION)
                return;

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

        homePageGUI.getCreateInvoiceBtn().addActionListener(actionEvent -> {
            ((InvoiceHeaderTableModel)homePageGUI.getInvoicesTable().getModel()).getData().addElement(new InvoiceHeader());
            homePageGUI.getInvoicesTable().updateUI();
        });

        homePageGUI.getInvoicesTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent lse) {
                int selected = homePageGUI.getInvoicesTable().getSelectedRow();
                currentHeader = homePageGUI.getInvoicesTableModel().getData().get(selected);
                loadInvoiceItems();
            }
        });

        homePageGUI.getDeleteInvoiceBtn().addActionListener(actionEvent -> {
            if(homePageGUI.getInvoicesTable().getModel().getRowCount() < 1)
            {
                JOptionPane.showMessageDialog(homePageGUI, "No Rows Available");
                return;
            }

            if(currentHeader == null)
            {
                JOptionPane.showMessageDialog(homePageGUI, "No Row Selected");
                return;
            }

            homePageGUI.getInvoicesTableModel().getData().removeElement(currentHeader);
            homePageGUI.getInvoicesTableModel().fireTableDataChanged();
            homePageGUI.getInvoicesTable().updateUI();
        });

        homePageGUI.getAddItemBtn().addActionListener(actionEvent -> {
            InvoiceLine item = new InvoiceLine();
            item.setInvoiceNumber(currentHeader.getInvoiceNum());
            homePageGUI.getInvoiceItemsTableModel().getData().add(item);
            homePageGUI.getInvoiceItemsTable().updateUI();
        });

        homePageGUI.getSaveBtn().addActionListener(actionEvent -> {
            currentHeader.setCustName(homePageGUI.getCustomerNameTxt().getText());
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

        homePageGUI.getCancelBtn().addActionListener(actionEvent -> {
            loadInvoiceItems();
        });

    }

    private void loadInvoiceItems() {
        homePageGUI.getInvoiceItemsTableModel().getData().clear();
        homePageGUI.getInvoiceItemsTableModel().getData().addAll(currentHeader.getInvoiceLines());
        homePageGUI.getInvoiceItemsTable().updateUI();
        homePageGUI.getInvoiceNumberLbl().setText(String.valueOf(currentHeader.getInvoiceNum()));
        homePageGUI.getDateTxt().setText(dateFormat.format(currentHeader.getInvoiceDate()));
        homePageGUI.getCustomerNameTxt().setText(currentHeader.getCustName());
        homePageGUI.getTotalLbl().setText(String.valueOf(currentHeader.getTotal()));
    }
}