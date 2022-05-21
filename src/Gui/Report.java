package Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Report {
    private AllUsers users;
    private Dedicated_Medias dedicated_medias;
    private Existed_Medias allMedias;

    private JFrame frame;

    private JButton allUsersButton;
    private JPanel Main_Frame;
    private JButton rentedMediasButton;
    private JButton allMediasButton;
    private JButton backButton;


    public Report() {
        frame = new JFrame("Report");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        frame.setBounds(650,300,400,300); //370,300
        frame.setResizable(false);

        allUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(users == null){
                    users = new AllUsers();
                }else{
                    users.filter();
                    users.visible();
                }
                frame.dispose();
            }
        });

        rentedMediasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(dedicated_medias == null){
                    dedicated_medias = new Dedicated_Medias();
                }else{
                    dedicated_medias.filter();
                    dedicated_medias.visible();
                }
                frame.dispose();
            }
        });

        allMediasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(allMedias == null){
                    allMedias = new Existed_Medias();
                }else{
                    allMedias.update();
                    allMedias.visible();
                }
                frame.dispose();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.visible();
                frame.dispose();
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        new Report();
    }
}
