package Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static DataBase.DBUtilities.*;

public class Insert_User {
    private JFrame frame;

    private JPanel Main_Frame;
    private JTextField Name;
    private JTextField EGN;
    private JTextField Phone;
    private JTextField Address;
    private JTextArea Description;
    private JButton addButton;

    public Insert_User() {
        frame = new JFrame("Insert_User");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Name.getText().isEmpty()){
                    insertUser(Name.getText(), EGN.getText(), Phone.getText(), Address.getText(), Description.getText());
                    frame.dispose();
                }
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
}
