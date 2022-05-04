package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reports {

    JFrame frame4 = new JFrame();

    JTextField Reports = new JTextField("REPORTS");

    JButton AllMedia = new JButton("ALL MEDIA");

    JButton RentedMedia = new JButton("RENTED MEDIA");

    JButton AllUsers = new JButton("ALL USERS");

    JButton Back = new JButton("BACK");

    Reports(){

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                frame4.dispose();
            }
        });
        Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
        Reports.setBounds(0, 0, 384, 30);
        Reports.setBackground(Color.WHITE);
        Reports.setFont(new Font("", Font.PLAIN, 22));
        Reports.setHorizontalAlignment(JTextField.CENTER);
        Reports.setEditable(false);
        Reports.setBorder(border);
        Reports.setOpaque(false);

        AllMedia.setBounds(45, 65, 150, 20);

        RentedMedia.setBounds(45, 115, 150, 20);

        AllUsers.setBounds(45, 160, 150, 20 );



        Back.setBounds(285, 177, 85, 20);

        frame4.add(Reports);
        frame4.add(AllMedia);
        frame4.add(RentedMedia);
        frame4.add(AllUsers);
        frame4.add(Back);

        frame4.setLocation(500, 200);
        frame4.setTitle("Media Library");
        frame4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame4.setSize(400, 250);
        frame4.setLayout(null);
        frame4.setVisible(true);
        frame4.setResizable(false);

    }
}
