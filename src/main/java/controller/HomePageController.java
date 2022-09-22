package controller;

import model.FileOperations;
import view.HomePageGUI;

public class HomePageController {
    HomePageGUI homePageGUI;

    public HomePageController(HomePageGUI homePageGUI)
    {
        this.homePageGUI = homePageGUI;
        initController();
    }

    private void initController() {
        homePageGUI.getLoadFile().addActionListener(actionEvent ->
                homePageGUI.getInvoicesTableModel().setInvoiceHeaders(FileOperations.loadFile(homePageGUI))
        );

        homePageGUI.getSaveFile().addActionListener(actionEvent ->
            FileOperations.saveData(homePageGUI.getInvoicesTableModel().getData())
        );

        homePageGUI.getCreateInvoiceBtn().addActionListener(actionEvent -> {
            // TODO: Add New Invoice Header
        });

        homePageGUI.getDeleteInvoiceBtn().addActionListener(actionEvent -> {
            // TODO: Remove Current Selected Invoice
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
    }
}