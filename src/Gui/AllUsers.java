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

public class AllUsers {
    private JFrame frame;

    private Insert_User insertUserGui;
    private Edit_User editGui;
    private Delete deleteGui;

    private ArrayList<User> users;
    private DefaultTableModel model;
    private String[] head = {"Id","Name","EGN","Phone","Address"};

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;
    private JLabel Title;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton Search;

    public AllUsers()    {
        frame = new JFrame("All Users");
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
