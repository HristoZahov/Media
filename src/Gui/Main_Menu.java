package Gui;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Menu {
    private Insert_Edit_User userGui;
    private Insert_Edit_Media mediaGui;
    private Report report;
    private Rent_Return rent_return;
    private JFrame frame;

    private JPanel Frame;
    private JPanel Header;
    private JLabel Title;
    private JButton User;
    private JButton Media;
    private JButton Rent;
    private JButton Report;

    public Main_Menu() {
        frame = new JFrame("Main Menu");
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
                if(userGui == null){
                    userGui = new Insert_Edit_User();
                }else{
                    userGui.visible();
                }
                frame.dispose();
            }
        });
        Media.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(mediaGui == null){
                    mediaGui = new Insert_Edit_Media();
                }else{
                    mediaGui.visible();
                }
                frame.dispose();
            }
        });
        Report.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(report == null){
                    report = new Report();
                }else{
                    report.visible();
                }
                frame.dispose();
            }
        });
        Rent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(rent_return == null){
                    rent_return = new Rent_Return();
                }else{
                    rent_return.visible();
                }
                frame.dispose();
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
    public Insert_Edit_User getUserGui() {
        return userGui;
    }
    public Insert_Edit_Media getMediaGui() {
        return mediaGui;
    }
    public Gui.Report getReport() {
        return report;
    }

    public Rent_Return getRent_return() {
        return rent_return;
    }
}
