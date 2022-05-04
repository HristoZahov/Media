package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Insert_Edit_User {
    private JFrame frame;
    private Insert_User insertUser;

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable table;
    private JButton Back;
    private JButton Insert;
    private JLabel Title;

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

        addLiseners();
    }

    public void visible(){
        frame.setVisible(true);
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
                if(insertUser == null){
                    insertUser = new Insert_User();
                }else{
                    insertUser.visible();
                }
            }
        });
    }
}
