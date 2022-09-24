package view;

import model.InvoiceHeaderTableModel;
import model.InvoiceItemsTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.DateFormatter;
import javax.swing.text.DefaultFormatterFactory;
import java.awt.*;
import java.nio.file.Paths;

import static model.InvoiceHeader.dateFormat;

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
    private JTextField itemNameTxt;
    private JTextField priceTxt;
    private JTextField countTxt;
    private JButton updateBtn;
    private JMenuItem saveFile;
    private JMenuItem loadFile;
    private InvoiceItemsTableModel invoiceItemsTableModel;
    private InvoiceHeaderTableModel invoicesTableModel;
    private final JFileChooser fileChooser = new JFileChooser();

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
        initFileChooser();
    }

    private void initFileChooser() {
        fileChooser.setCurrentDirectory(Paths.get(".").toFile());
    }

    private void initFormattedText() {
        DateFormatter formatter = new DateFormatter(dateFormat);
        DefaultFormatterFactory formatterFactory = new DefaultFormatterFactory(formatter);
        dateTxt.setFormatterFactory(formatterFactory);
    }

    private void initTables() {
        invoicesTableModel = new InvoiceHeaderTableModel();
        invoicesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        invoicesTable.setModel(invoicesTableModel);
        // Render Date with this format dd-MM-yyyy
        invoicesTable.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                return super.getTableCellRendererComponent(table, dateFormat.format(value), isSelected, hasFocus, row, column);
            }
        });
        invoiceItemsTableModel = new InvoiceItemsTableModel();
        invoiceItemsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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

    public JTable getInvoicesTable() {
        return invoicesTable;
    }

    public JTable getInvoiceItemsTable() {
        return invoiceItemsTable;
    }

    public InvoiceItemsTableModel getInvoiceItemsTableModel() {
        return invoiceItemsTableModel;
    }

    public InvoiceHeaderTableModel getInvoicesTableModel() {
        return invoicesTableModel;
    }

    public JTextField getItemNameTxt() {
        return itemNameTxt;
    }

    public JTextField getPriceTxt() {
        return priceTxt;
    }

    public JTextField getCountTxt() {
        return countTxt;
    }

    public JButton getUpdateBtn() {
        return updateBtn;
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }
}