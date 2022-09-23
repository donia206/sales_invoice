package model;

import com.opencsv.CSVReader;

import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class FileOperations {
    public static void saveData(Vector<InvoiceHeader> headers) {
        // TODO: Save Two Files for invoices
    }
    private static FileReader getFile(String title, Frame parent) throws FileNotFoundException {
        FileDialog fileDialog = new FileDialog(parent);
        fileDialog.setTitle(title);
        fileDialog.setMode(FileDialog.LOAD);
        fileDialog.setFilenameFilter((file, s) -> s.endsWith(".csv"));
        fileDialog.setVisible(true);
        if(fileDialog.getFile() == null)
            throw new FileNotFoundException();

        return new FileReader(fileDialog.getDirectory() + fileDialog.getFile());
    }

    public static Vector<InvoiceHeader> loadFile(Frame parent) throws IOException, ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        // Load Invoice Lines
        Map<Integer, Vector<InvoiceLine>> linesMap = new TreeMap<>();

        CSVReader reader = new CSVReader(getFile("Open Invoice Lines CSV", parent));
        String[] nextLine;
        while ((nextLine = reader.readNext()) != null) {
            InvoiceLine line = new InvoiceLine();
            int invoiceNum = Integer.parseInt(nextLine[0]);
            line.setInvoiceNumber(invoiceNum);
            line.setItemName(nextLine[1]);
            line.setItemPrice(Float.parseFloat(nextLine[2]));
            line.setCount(Integer.parseInt(nextLine[3]));
            Vector<InvoiceLine> v = linesMap.putIfAbsent(invoiceNum, new Vector<>());
            if (v != null) {
                v.add(line);
            }
        }

        // Load Invoice Headers
        Vector<InvoiceHeader> headers = new Vector<>();
        reader = new CSVReader(getFile("Open Invoice Headers CSV", parent));

        while((nextLine = reader.readNext()) != null) {
            int invoiceNum = Integer.parseInt(nextLine[0]);
            if(InvoiceHeader.maxInvoice < invoiceNum)
                InvoiceHeader.maxInvoice = invoiceNum;

            InvoiceHeader header = new InvoiceHeader(invoiceNum);
            header.setInvoiceNum(invoiceNum);
            header.setInvoiceDate(formatter.parse(nextLine[1]));
            header.setCustName(nextLine[2]);
            header.setTotal(Float.parseFloat(nextLine[3]));

            Vector<InvoiceLine> lines = linesMap.get(invoiceNum);

            if(lines != null)
                header.setInvoiceLines(lines);

            headers.add(header);
        }

        return headers;
    }
}