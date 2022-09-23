package model;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import static model.Globals.dateFormat;

public class FileOperations {
    public static void saveData(File folderPath, Vector<InvoiceHeader> headers) throws IOException {
        CSVWriter linesWriter = new CSVWriter(new FileWriter(folderPath + String.valueOf(File.separatorChar) + "invoiceLines.csv"));
        linesWriter.writeNext(InvoiceItemsTableModel.colNames);

        CSVWriter headerWriter = new CSVWriter(new FileWriter(folderPath + String.valueOf(File.separatorChar) + "invoiceHeaders.csv"));
        headerWriter.writeNext(InvoiceHeaderTableModel.colNames);

        for (InvoiceHeader header: headers) {
            String[] headerRow = {String.valueOf(header.getInvoiceNum()), dateFormat.format(header.getInvoiceDate()), header.getCustName(), String.valueOf(header.getTotal())};
            headerWriter.writeNext(headerRow);
            for (InvoiceLine line : header.getInvoiceLines()) {
                String[] lineRow = {String.valueOf(line.getInvoiceNumber()), line.getItemName(), String.valueOf(line.getItemPrice()), String.valueOf(line.getCount()), String.valueOf(line.getTotal())};
                linesWriter.writeNext(lineRow);
            }
        }
        linesWriter.close();
        headerWriter.close();
    }

    public static Vector<InvoiceHeader> loadFile(File invoiceLinesCSV, File invoiceHeadersCSV) throws IOException, ParseException {
        // Load Invoice Lines
        Map<Integer, Vector<InvoiceLine>> linesMap = new TreeMap<>();

        CSVReader reader = new CSVReader(new FileReader(invoiceLinesCSV));
        String[] nextLine = reader.readNext(); // ignore column names
        while ((nextLine = reader.readNext()) != null) {
            try {
                InvoiceLine line = new InvoiceLine();
                int invoiceNum = Integer.parseInt(nextLine[0]);
                line.setInvoiceNumber(invoiceNum);
                line.setItemName(nextLine[1]);
                line.setItemPrice(Float.parseFloat(nextLine[2]));
                line.setCount(Integer.parseInt(nextLine[3]));
                Vector<InvoiceLine> v = linesMap.putIfAbsent(invoiceNum, new Vector<>());
                if(v != null)
                    v.add(line);

            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

        // Load Invoice Headers
        Vector<InvoiceHeader> headers = new Vector<>();
        reader = new CSVReader(new FileReader(invoiceHeadersCSV));
        nextLine = reader.readNext(); // ignore column names
        while((nextLine = reader.readNext()) != null) {
            try {
                int invoiceNum = Integer.parseInt(nextLine[0]);
                if(InvoiceHeader.maxInvoice < invoiceNum)
                    InvoiceHeader.maxInvoice = invoiceNum;

                InvoiceHeader header = new InvoiceHeader(invoiceNum);
                header.setInvoiceNum(invoiceNum);
                header.setInvoiceDate(dateFormat.parse(nextLine[1]));
                header.setCustName(nextLine[2]);
                header.setTotal(Float.parseFloat(nextLine[3]));

                Vector<InvoiceLine> lines = linesMap.get(invoiceNum);
                header.setInvoiceLines(lines == null ? new Vector<>(): lines);

                headers.add(header);

            } catch (ArrayIndexOutOfBoundsException ignored) {}
        }

        return headers;
    }
}