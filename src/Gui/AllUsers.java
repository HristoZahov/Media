package Gui;

import Classes.User;
import Utilities.UserUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Utilities.TableUtilities.*;

public class AllUsers {
    private JFrame frame;

    private ArrayList<User> users;

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;

    public AllUsers() {
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
        users = UserUtilities.getAllUsers();

        crateTable(makeTablePartUsers(users));
        addLiseners();
    }

    private void addLiseners(){
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.visible();
                frame.dispose();
            }
        });
    }
    private void crateTable(Object[][] users){
        String[] head = {"Id","Name","EGN","Phone","Address"};
        DefaultTableModel model = new DefaultTableModel(users,head){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Table.getTableHeader().setReorderingAllowed(false);
        Table.setModel(model);
    }

    public static void main(String[] args) {
        new AllUsers();
    }
}
