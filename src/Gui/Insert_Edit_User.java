package Gui;

import Classes.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Utilities.TableUtilities.*;
import static Utilities.UserUtilities.getAllUsers;
import static Utilities.UserUtilities.getSearchedUsers;

public class Insert_Edit_User {
    private JFrame frame;

    private Insert_User insertUserGui;
    private Edit_User editUserGui;
    private Delete deleteGui;

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
    private JButton deleteButton;

    public Insert_Edit_User()    {
        frame = new JFrame("Insert Edit User");
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
        users = getAllUsers();

        crateTable(makeTablePartUsers(users));
        makeSearchComboBox();
        addLiseners();
    }

    private void addLiseners(){
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(insertUserGui != null){
                    insertUserGui.dispose();
                }
                if(editUserGui != null){
                    editUserGui.dispose();
                }
                if(deleteGui != null){
                    deleteGui.dispose();
                }
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
                    insertUserGui.clear();
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

        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editUserGui == null){
                    editUserGui = new Edit_User();
                }else{
                    editUserGui.clear();
                    editUserGui.visible();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deleteGui == null){
                    deleteGui = new Delete("User");
                }else{
                    deleteGui.clear();
                    deleteGui.visible();
                }
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });
    }
    private void makeSearchComboBox(){
        String[] type = {"Name","EGN","Phone","Address"};
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
    public void filter(){
        model = new DefaultTableModel(makeTablePartUsers(getSearchedUsers(comboBox1, textField1.getText())),head);
        Table.setModel(model);
    }

    public void visible(){
        frame.setVisible(true);
    }
}
