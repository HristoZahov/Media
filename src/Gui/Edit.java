package Gui;

import Classes.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static DataBase.DBUtilities.*;

public class Edit {
    private User user;
    private JFrame frame;

    private JPanel Main_Frame;
    private JTextField Name;
    private JTextField EGN;
    private JTextField Phone;
    private JTextField Address;
    private JTextArea Description;
    private JButton EditBtn;
    private JTextField IdField;
    private JButton enterButton;

    public Edit() {
        frame = new JFrame("Edit");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        EditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user != null){
                    updateUser(user.getId(), Name.getText(), EGN.getText(), Phone.getText(), Address.getText(), Description.getText());

                    //frame.dispose();
                }
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IdField.getText().isEmpty()){
                    user = getUser(Integer.parseInt(IdField.getText()));
                    if(user != null){
                        Name.setText(user.getName());
                        EGN.setText(user.getEgn());
                        Phone.setText(user.getPhone());
                        Address.setText(user.getAddress());
                        Description.setText(user.getDescription());
                    }
                }else{
                    user = null;
                    Name.setText("");
                    EGN.setText("");
                    Phone.setText("");
                    Address.setText("");
                    Description.setText("");
                }
            }
        });
    }

    public static void main(String[] args) {
        new Edit();
    }

    public void visible(){
        frame.setVisible(true);
    }
}
