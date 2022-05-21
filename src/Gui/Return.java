package Gui;

import Classes.Media;
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

public class Return {
    private JFrame frame;

    private ArrayList<Order> orders;

    private DefaultTableModel model;
    private String[] head = {"User","Media","Start Date","End Date","Select"};

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton Search;
    private JButton returnButton;

    public Return() {
        frame = new JFrame("Return");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Border
        Border border = new LineBorder(Color.BLACK, 3, false);
        Header.setBorder(border);

        frame.setBounds(650,300,400,400); //370,300
        orders = new ArrayList<>();

        crateTable(getOrdersArray(false));
        makeSearchComboBox();
        addLiseners();
    }

    private void addLiseners(){
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.getRent_return().visible();
                frame.dispose();
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrRemove();
                filter();
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrRemove();

                if(!orders.isEmpty()){
                    makeReturn(orders);
                    orders.clear();
                    filter();
                }
            }
        });
    }
    private void makeSearchComboBox(){
        String[] type = {"User","Media","Start Date","End Date"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox1.setModel(model);
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
        crateTable(getSearchedInArray(comboBox1.getSelectedItem().toString(), textField1.getText(),false, orders));
    }

    private void addOrRemove() {
        boolean isExist = false;

        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 4)) {
                for(Order order:orders){
                    if(order.getUser().equals(model.getValueAt(i, 0).toString()) &&
                            order.getMedia().equals(model.getValueAt(i, 1).toString()) &&
                            order.getStart_date().equals( model.getValueAt(i, 2)) &&
                            order.getEnd_date().equals( model.getValueAt(i, 3))){
                        isExist = true;
                        break;
                    }
                }

                if (!isExist) {
                    orders.add(new Order(model.getValueAt(i, 0).toString(),model.getValueAt(i, 1).toString(),
                            (Date) model.getValueAt(i, 2), (Date) model.getValueAt(i, 3)));
                }
            } else {
                int count = 0;
                for(Order order:orders){

                    if(order.getUser().equals(model.getValueAt(i, 0).toString()) &&
                            order.getMedia().equals(model.getValueAt(i, 1).toString()) &&
                            order.getStart_date().equals(model.getValueAt(i, 2)) &&
                            order.getEnd_date().equals( model.getValueAt(i, 3))){
                        isExist = true;
                        break;
                    }
                    count++;
                }

                if (isExist) {
                    orders.remove(orders.get(count));
                }
            }
            isExist = false;
        }
    }

    public void visible(){
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Return();
    }
}
