package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertEditMedia {
    JFrame frame2 = new JFrame();

    JTextField InsertEditMedia = new JTextField("INSERT/EDIT MEDIA");

    JButton InsertNewMedia = new JButton("INSERT NEW MEDIA");

    JTextArea EditExistingMedia = new JTextArea("EDIT EXISTING MEDIA:");

    JButton SearchByName = new JButton("SEARCH BY NAME");

    JButton SearchByAutor = new JButton("SEARCH BY AUTOR");

    JButton SearchByIDorISBN = new JButton("SEARCH BY ID/ISBN");

    JButton Back = new JButton("BACK");


    public InsertEditMedia() {

        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainMenu mainMenu = new MainMenu();
                frame2.dispose();
            }
        });

        Border border = BorderFactory.createLineBorder(Color.GRAY, 2);
        frame2.add(SearchByAutor);
        frame2.add(InsertEditMedia);
        frame2.add(InsertNewMedia);
        frame2.add(EditExistingMedia);
        frame2.add(SearchByName);
        frame2.add(SearchByIDorISBN);
        frame2.add(Back);


        InsertEditMedia.setBounds(0, 0, 384, 30);
        InsertEditMedia.setBackground(Color.WHITE);
        InsertEditMedia.setFont(new Font("", Font.PLAIN, 22));
        InsertEditMedia.setHorizontalAlignment(JTextField.CENTER);
        InsertEditMedia.setEditable(false);
        InsertEditMedia.setBorder(border);
        InsertEditMedia.setOpaque(false);



        EditExistingMedia.setBounds(55, 150, 150, 20);
        EditExistingMedia.setOpaque(false);


        InsertNewMedia.setBounds(45, 70, 150, 20);

        SearchByName.setBounds(45, 185, 150, 20);

        SearchByIDorISBN.setBounds(45, 235, 150, 20);

        SearchByAutor.setBounds(45, 282, 150, 20);

        Back.setBounds(285, 328, 85, 20);




        frame2.setLocation(500, 200);
        frame2.setTitle("Media Library");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(400, 400);
        frame2.setLayout(null);
        frame2.setVisible(true);
        frame2.setResizable(false);


    }

    public static void main(String[] args) {
        InsertEditMedia usr1 = new InsertEditMedia();
    }
}
