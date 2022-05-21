package Gui;

import Classes.Order;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import static Utilities.MediaUtilities.*;

public class Dedicated_Medias {
    private JFrame frame;

    private ArrayList<Order> orders = new ArrayList<>();

    private DefaultTableModel model;
    private String[] head = {"Media","Start Date","End Date"};

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField textField1;
    private JButton Search;

    public Dedicated_Medias() {
        frame = new JFrame("Dedicated Medias");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Border
        Border border = new LineBorder(Color.BLACK, 3, false);
        Header.setBorder(border);

        frame.setBounds(650,300,400,400); //370,300

        makeSearchComboBox();
        makeInspiredComboBox();
        filter();
        addLiseners();
    }

    private void addLiseners(){
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.getReport().visible();
                frame.dispose();
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });

    }
    private void makeSearchComboBox(){
        String[] type = {"Media","Start Date","End Date"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox1.setModel(model);
    }
    private void makeInspiredComboBox(){
        String[] type = {"Rent","Expired"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox2.setModel(model);
    }
    private void crateTable(Object[][] medias){
        model = new DefaultTableModel(medias,head){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0,1,2,3:
                        return String.class;
                    default:
                        return Boolean.class;
                }

            }
        };
        Table.getTableHeader().setReorderingAllowed(false);
        Table.setModel(model);
    }
    public void filter(){
        crateTable(getDedicated(comboBox1.getSelectedItem().toString(), textField1.getText(),comboBox2));
    }

    public void visible(){
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Dedicated_Medias();
    }
}
