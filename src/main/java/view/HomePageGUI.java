package view;

import model.InvoiceHeaderTableModel;
import model.InvoiceItemsTableModel;

import javax.swing.*;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.text.SimpleDateFormat;

public class HomePageGUI extends JFrame {
    private JPanel mainPanel;
    private JTable invoicesTable;
    private JButton createInvoiceBtn;
    private JButton deleteInvoiceBtn;
    private JTable invoiceItemsTable;
    private JLabel invoiceNumberLbl;
    private JTextField customerNameTxt;
    private JFormattedTextField dateTxt;
    private JButton saveBtn;
    private JButton cancelBtn;
    private JLabel totalLbl;
    private JButton addItemBtn;
    private JMenuItem saveFile;
    private JMenuItem loadFile;
    private InvoiceItemsTableModel invoiceItemsTableModel;
    private InvoiceHeaderTableModel invoicesTableModel;

    public HomePageGUI()
    {
        super("Sales Invoices");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocation(200, 200);
        this.add(mainPanel);
        initMenu();
        initTables();
        initFormattedText();
    }

    private void initFormattedText() {
        DateFormatter formatter = new DateFormatter(new SimpleDateFormat("dd-MM-yyyy"));
        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(formatter);
        dateTxt.setFormatterFactory(formatterFactory);
    }

    private void initTables() {
        invoicesTableModel = new InvoiceHeaderTableModel();
        invoicesTable.setModel(invoicesTableModel);

        invoiceItemsTableModel = new InvoiceItemsTableModel();
        invoiceItemsTable.setModel(invoiceItemsTableModel);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        saveFile = new JMenuItem("Save File");
        loadFile = new JMenuItem("Load File");
        fileMenu.add(loadFile);
        fileMenu.add(saveFile);
        menuBar.add(fileMenu);
        this.setJMenuBar(menuBar);
    }

    public JButton getCreateInvoiceBtn() {
        return createInvoiceBtn;
    }

    public JButton getDeleteInvoiceBtn() {
        return deleteInvoiceBtn;
    }

    public JButton getAddItemBtn() {
        return addItemBtn;
    }

    public JLabel getInvoiceNumberLbl() {
        return invoiceNumberLbl;
    }

    public JTextField getCustomerNameTxt() {
        return customerNameTxt;
    }

    public JFormattedTextField getDateTxt() {
        return dateTxt;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getCancelBtn() {
        return cancelBtn;
    }

    public JLabel getTotalLbl() {
        return totalLbl;
    }

    public JMenuItem getSaveFile() {
        return saveFile;
    }

    public JMenuItem getLoadFile() {
        return loadFile;
    }

    public InvoiceItemsTableModel getInvoiceItemsTableModel() {
        return invoiceItemsTableModel;
    }

    public InvoiceHeaderTableModel getInvoicesTableModel() {
        return invoicesTableModel;
    }
}