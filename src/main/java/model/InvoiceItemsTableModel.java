package model;

import javax.swing.table.AbstractTableModel;
import java.util.Vector;

public class InvoiceItemsTableModel extends AbstractTableModel {
    private Vector<InvoiceLine> lines = new Vector<>();
    private final String[] colNames = {"No.", "Item Name", "Item Price", "Count", "Total"};

    public Vector<InvoiceLine> getData() {
        return lines;
    }

    public void setLines(Vector<InvoiceLine> lines) {
        this.lines = lines;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
            case 3:
                return Integer.class;
            case 2:
            case 4:
                return Float.class;
            default:
                return String.class;
        }
    }

    @Override
    public int getRowCount() {
        return lines.size();
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        switch (i1) {
            case 0:
                return lines.get(i).getInvoiceNumber();
            case 1:
                return lines.get(i).getItemName();
            case 2:
                return lines.get(i).getItemPrice();
            case 3:
                return lines.get(i).getCount();
            case 4:
                return lines.get(i).getTotal();
            default:
                return null;
        }
    }
}
