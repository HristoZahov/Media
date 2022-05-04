package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertEditUser {
    JFrame frame1 = new JFrame();

    JTextField InsertEditUser = new JTextField("INSERT/EDIT USER");

    JButton InsertNewUser = new JButton("INSERT NEW USER");

    JTextArea EditExistingUser = new JTextArea("EDIT EXISTING USER:");

    JButton SearchByName = new JButton("SEARCH BY NAME");

    JButton SearchByID = new JButton("SEARCH BY ID");

    JButton Back = new JButton("BACK");


    public InsertEditUser() {

        InsertNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
                JTextField answer1 = new JTextField();
                answer1.setBounds(210, 70, 155, 20);
                answer1.setBorder(border);
                answer1.setVisible(true);
                frame1.add(answer1);
            }
        });

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                frame1.dispose();
            }
        });

        Border border = BorderFactory.createLineBorder(Color.GRAY, 2);

        frame1.add(InsertEditUser);
        frame1.add(InsertNewUser);
        frame1.add(EditExistingUser);
        frame1.add(SearchByName);
        frame1.add(SearchByID);
        frame1.add(Back);


        InsertEditUser.setBounds(0, 0, 384, 30);
        InsertEditUser.setBackground(Color.WHITE);
        InsertEditUser.setFont(new Font("", Font.PLAIN, 22));
        InsertEditUser.setHorizontalAlignment(JTextField.CENTER);
        InsertEditUser.setEditable(false);
        InsertEditUser.setBorder(border);
        InsertEditUser.setOpaque(false);



        EditExistingUser.setBounds(55, 150, 150, 20);
        EditExistingUser.setOpaque(false);


        InsertNewUser.setBounds(45, 70, 150, 20);

        SearchByName.setBounds(45, 185, 150, 20);

        SearchByID.setBounds(45, 235, 150, 20);

        Back.setBounds(285, 280, 85, 20);




        frame1.setLocation(500, 200);
        frame1.setTitle("Media Library");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setSize(400, 350);
        frame1.setLayout(null);
        frame1.setVisible(true);
        frame1.setResizable(false);


    }

    public static void main(String[] args) {
        InsertEditUser usr = new InsertEditUser();
    }

}
