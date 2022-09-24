package model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class InvoiceHeader {
    public static int maxInvoice = 0;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    private int invoiceNum;
    private Date invoiceDate;
    private String customerName;
    private float total;
    private Vector<InvoiceLine> invoiceLines;

    public InvoiceHeader() {
        invoiceNum = ++maxInvoice;
        invoiceLines = new Vector<>();
        invoiceDate = new Date();
    }

    public InvoiceHeader(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public int getInvoiceNum() {
        return invoiceNum;
    }

    public void setInvoiceNum(int invoiceNum) {
        this.invoiceNum = invoiceNum;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Vector<InvoiceLine> getInvoiceLines() {
        return invoiceLines;
    }

    public void setInvoiceLines(Vector<InvoiceLine> invoiceLines) {
        this.invoiceLines = invoiceLines;
    }
}
