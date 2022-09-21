package view;
import javax.print.attribute.standard.JobMessageFromOperator;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
    private JFrame f;
    private JButton b1;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private JButton b6;
    private JTextField inv_date;
    private JTextField cust_name;
    private JFrame f2;


    private JTable table;
    private JTable table2;
    private String[]columns={"No","Date","Customer","Total"};
    private String[][]rows={{"1","20/6/2022","donia","20"}};
    private String[]col={"No","Item name","Item price","count","Total"};
    private String[][]data={{"3","cizaro","30","4","5"}};
    GUI listen;

    public GUI()  {
        super("sales invoice");
        setLayout(new FlowLayout());

        b1= new JButton("load file");
        b2 = new JButton("save file");
        b3 = new JButton("delete invoice");
        b4 = new JButton("create invoice");
        b5 = new JButton("save");
        b6 = new JButton("cancel");
        b1.setSize(30,20);
        b1.setLocation(5,30);
        b2.setSize(30,20);
        b2.setLocation(20,10);
        b3.setSize(30,20);
        b4.setSize(30,20);
        b5.setSize(30,20);
        b6.setSize(30,20);
        b3.setLocation(30,-15);
        b4.setLocation(35,-15);
        b5.setLocation(50,-15);
        b6.setLocation(55,-15);
        inv_date = new JTextField(10);
        cust_name = new JTextField(10);
        inv_date.setSize(30,30);
        cust_name.setSize(30,30);
        cust_name.setLocation(60,30);
        inv_date.setLocation(65,30);
        Actions listen = new Actions();
        b2.addActionListener(listen);



        add(b1);
        add(b2);
        add(b3);
        add(b4);
        add(b5);
        add(b6);
        add(new JLabel("invoice date"));
        add(inv_date);
        add(new JLabel("customer name"));
        add(cust_name);


        table = new JTable(rows,columns);
        add(new JScrollPane(table));
        table.setSize(200,200);
        table.setLocation(0,0);

        setSize(800,800);
        setLocation(200,200);


        table2=new JTable(data,col);
        add(new JScrollPane(table2));
        table2.setSize(100,100);
        table2.setLocation(20,20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main (String[] args){


        new GUI().setVisible(true);
    }


}
