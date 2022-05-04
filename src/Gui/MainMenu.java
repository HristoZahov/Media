package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu{
    JFrame frame = new JFrame();
    JTextField mainmenu = new JTextField("MAIN MENU");
    JButton inseditusr = new JButton("INSERT/EDIT USER");
    JButton inseditmedia = new JButton("INSERT/EDIT MEDIA");
    JButton rent = new JButton("RENT");
    JButton reports = new JButton("REPORTS");

    public MainMenu() {

        inseditusr.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertEditUser usr1 = new InsertEditUser();
                frame.dispose();

            }
        });

        inseditmedia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InsertEditMedia usr1 = new InsertEditMedia();
                frame.dispose();
            }
        });

        rent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Rent rent = new Rent();
                frame.dispose();
            }
        });

        reports.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Reports reports1 = new Reports();
                frame.dispose();
            }
        });

        Border border = BorderFactory.createLineBorder(Color.GRAY, 2);

        frame.add(mainmenu);
        mainmenu.setBounds(0, 0, 384, 30);
        mainmenu.setBackground(Color.WHITE);
        mainmenu.setFont(new Font("", Font.PLAIN, 22));
        mainmenu.setHorizontalAlignment(JTextField.CENTER);
        mainmenu.setEditable(false);
        mainmenu.setBorder(border);
        mainmenu.setOpaque(false);


        frame.add(inseditusr);
        inseditusr.setBounds(45, 70, 150, 20);
        inseditusr.setBorder(border);


        frame.add(inseditmedia);
        inseditmedia.setBounds(45, 130, 150, 20);
        inseditmedia.setBorder(border);


        frame.add(rent);
        rent.setBounds(45, 185, 150, 20);
        rent.setBorder(border);


        frame.add(reports);
        reports.setBounds(45, 245, 150, 20);
        reports.setBorder(border);


        frame.setLocation(500, 200);
        frame.setTitle("Media Library");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setResizable(false);


    }
}
