package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Rent {

    JFrame frame3 = new JFrame();

    JTextField rent = new JTextField("RENT");

    JButton RentMedia = new JButton("RENT MEDIA");

    JButton ReturnMedia = new JButton("RETURN MEDIA");

    JButton Back = new JButton("BACK");

    Rent() {

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                frame3.dispose();
            }
        });


        Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
        rent.setBounds(0, 0, 384, 30);
        rent.setBackground(Color.WHITE);
        rent.setFont(new Font("", Font.PLAIN, 22));
        rent.setHorizontalAlignment(JTextField.CENTER);
        rent.setEditable(false);
        rent.setBorder(border);
        rent.setOpaque(false);

        RentMedia.setBounds(45, 70, 150, 20);

        ReturnMedia.setBounds(45, 120, 150, 20);

        Back.setBounds(285, 177, 85, 20);


        frame3.add(rent);
        frame3.add(RentMedia);
        frame3.add(ReturnMedia);
        frame3.add(Back);

        frame3.setLocation(500, 200);
        frame3.setTitle("Media Library");
        frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame3.setSize(400, 250);
        frame3.setLayout(null);
        frame3.setVisible(true);
        frame3.setResizable(false);

    }
}

