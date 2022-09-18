package model;

import java.util.ArrayList;
import java.util.Date;

public class InvoiceHeader extends InvoiceLine{
    Date InvoiceDate;
    String CustomerName;
    ArrayList<InvoiceLine>Lines;

}
