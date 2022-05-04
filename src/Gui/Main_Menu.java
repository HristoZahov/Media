package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Menu {
    private Insert_Edit_User user;
    private JFrame frame;

    private JPanel Frame;
    private JPanel Header;
    private JLabel Title;
    private JButton User;
    private JButton Media;
    private JButton Rent;
    private JButton Report;

    public Main_Menu() {
        frame = new JFrame("Main_Menu");
        frame.setContentPane(Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        //Border
        Border border = new LineBorder(Color.BLACK, 3, false);
        Header.setBorder(border);

        //Size
        frame.setBounds(650,300,400,400); //370,300
        frame.setResizable(false);

        User.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user == null){
                    user = new Insert_Edit_User();
                }else{
                    user.visible();
                }
                frame.dispose();
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
}
