package Gui;

import Classes.GuiInt;
import Classes.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Utilities.UserUtilities.*;

public class Edit_User implements GuiInt {
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

    public Edit_User() {
        frame = new JFrame("Edit User");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        EditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(user != null){
                    user.setName(Name.getText());
                    user.setEgn(EGN.getText());
                    user.setPhone(Phone.getText());
                    user.setAddress(Address.getText());
                    user.setDescription(Description.getText());

                    updateUser(user);
                    Run.menu.getUserGui().filter();
                    //frame.dispose();
                }
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IdField.getText().isEmpty()){
                    user = getOneUser(Integer.parseInt(IdField.getText()));
                    if(user != null){
                        Name.setText(user.getName());
                        EGN.setText(user.getEgn());
                        Phone.setText(user.getPhone());
                        Address.setText(user.getAddress());
                        Description.setText(user.getDescription());
                    }
                }else{
                    user = null;
                    clear();
                }
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
    public void clear(){
        IdField.setText("");
        Name.setText("");
        EGN.setText("");
        Phone.setText("");
        Address.setText("");
        Description.setText("");
    }
}
