package model;

import javax.swing.table.AbstractTableModel;
import java.util.Date;
import java.util.Vector;

public class InvoiceHeaderTableModel extends AbstractTableModel {
    private final String[] colNames = {"No.", "Date", "Customer", "Total"};

    private Vector<InvoiceHeader> invoiceHeaders = new Vector<>();

    public void setInvoiceHeaders(Vector<InvoiceHeader> invoiceHeaders) {
        this.invoiceHeaders = invoiceHeaders;
        fireTableDataChanged();
    }

    public Vector<InvoiceHeader> getData() {
        return invoiceHeaders;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex)
        {
            case 0:
            case 3:
                return Integer.class;
            case 1:
                return Date.class;
            default:
                return String.class;
        }
    }

    @Override
    public int getRowCount() {
        return invoiceHeaders.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        if(i >= invoiceHeaders.size())
            return null;
        switch (i1)
        {
            case 0:
                return invoiceHeaders.get(i).getInvoiceNum();
            case 1:
                return invoiceHeaders.get(i).getInvoiceDate();
            case 2:
                return invoiceHeaders.get(i).getCustName();
            case 3:
                return invoiceHeaders.get(i).getTotal();
            default:
                return null;
        }
    }
}