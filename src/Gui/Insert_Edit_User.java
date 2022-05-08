package Gui;

import Classes.User;
import DataBase.DBUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Utilities.TableUtilities.*;

public class Insert_Edit_User {
    private JFrame frame;
    private Insert_User insertUserGui;
    private Edit editGui;
    private ArrayList<User> users;
    private DefaultTableModel model;
    private String[] head = {"Id","Name","EGN","Phone","Address"};

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;
    private JButton Insert;
    private JButton Edit;
    private JLabel Title;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton Search;

    public Insert_Edit_User() {
        frame = new JFrame("Insert/Edit User");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Border
        Border border = new LineBorder(Color.BLACK, 3, false);
        Header.setBorder(border);

        //Size
        frame.setBounds(650,300,400,400); //370,300
        //frame.setResizable(false);
        users = DBUtilities.getUsers();

        crateTable(makeTablePart(users));
        makeSearchComboBox();
        addLiseners();
        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editGui == null){
                    editGui = new Edit();
                }else{
                    editGui.visible();
                }
            }
        });
    }

    private void addLiseners(){
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.visible();
                frame.dispose();
            }
        });

        Insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(insertUserGui == null){
                    insertUserGui = new Insert_User();
                }else{
                    insertUserGui.visible();
                }
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });
    }
    private void makeSearchComboBox(){
        String[] type = {"Id","Name"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox1.setModel(model);
    }
    private void crateTable(Object[][] users){
        model = new DefaultTableModel(users,head){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Table.getTableHeader().setReorderingAllowed(false);
        Table.setModel(model);
    }
    private void filter(){
        model = new DefaultTableModel(makeTablePart(searchFilter(users, comboBox1, textField1.getText())),head);
        Table.setModel(model);
    }

    public void addUser(User user){
        users.add(user);
        filter();
    }
    public void visible(){
        frame.setVisible(true);
    }
}
