package controller;

import model.FileOperations;
import model.InvoiceHeader;
import model.InvoiceLine;
import view.HomePageGUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

public class HomePageController {
    HomePageGUI homePageGUI;

    public HomePageController(HomePageGUI homePageGUI)
    {
        this.homePageGUI = homePageGUI;
        initController();
    }

    private void initController() {
        homePageGUI.getLoadFile().addActionListener(actionEvent -> {
            Vector<InvoiceHeader> headers;
            try {
                headers = FileOperations.loadFile(homePageGUI);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(homePageGUI, "IO Error\n" + e.getMessage());
                return;
            } catch (ParseException e) {
                JOptionPane.showMessageDialog(homePageGUI, "Parse Error\n" + e.getMessage());
                return;
            }

            homePageGUI.getInvoicesTableModel().setInvoiceHeaders(headers);
        });

        homePageGUI.getSaveFile().addActionListener(actionEvent -> {
            FileOperations.saveData(homePageGUI.getInvoicesTableModel().getData());
        });

        homePageGUI.getCreateInvoiceBtn().addActionListener(actionEvent -> {
            homePageGUI.getInvoicesTableModel().getData().addElement(new InvoiceHeader());
            homePageGUI.getInvoicesTable().updateUI();
        });

        homePageGUI.getDeleteInvoiceBtn().addActionListener(actionEvent -> {
            if(homePageGUI.getInvoicesTableModel().getRowCount() < 1)
            {
                JOptionPane.showMessageDialog(homePageGUI, "No Rows Available");
                return;
            }

            int selectedInvoice = homePageGUI.getInvoicesTable().getSelectedRow();

            if(selectedInvoice == -1)
            {
                JOptionPane.showMessageDialog(homePageGUI, "No Row Selected");
                return;
            }

            homePageGUI.getInvoicesTableModel().getData().removeElementAt(selectedInvoice);
            homePageGUI.getInvoicesTableModel().fireTableDataChanged();
            homePageGUI.getInvoicesTable().updateUI();
        });

        homePageGUI.getAddItemBtn().addActionListener(actionEvent -> {
            // TODO: Add Invoice Item
        });

        homePageGUI.getSaveBtn().addActionListener(actionEvent -> {
            // TODO: Save Current Invoice Header with edited invoices
        });

        homePageGUI.getCancelBtn().addActionListener(actionEvent -> {
            // TODO: Cancel Changes
        });

        homePageGUI.getInvoiceItemsTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int selectedInvoice = homePageGUI.getInvoiceItemsTable().rowAtPoint(e.getPoint());
                if (selectedInvoice == -1)
                    return;
                Vector<InvoiceLine> lines = homePageGUI.getInvoicesTableModel().getData().get(selectedInvoice).getInvoiceLines();
                homePageGUI.getInvoiceItemsTableModel().setLines(lines);
            }
        });
    }
}