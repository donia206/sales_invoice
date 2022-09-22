package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class InvoiceHeader {
    public static int maxInvoice = 0;

    private int invoiceNum;
    private Date invoiceDate;
    private String custName;
    private float total;
    private Vector<InvoiceLine> invoiceLines;

    public InvoiceHeader(int invoiceNum)
    {
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

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
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
