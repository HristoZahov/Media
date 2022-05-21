package Gui;

import Classes.GuiInt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Utilities.MediaUtilities.checkMediaHaveRentsSQL;
import static Utilities.MediaUtilities.deleteMedia;
import static Utilities.UserUtilities.checkUserHaveRentsSQL;
import static Utilities.UserUtilities.deleteUser;

public class Delete implements GuiInt {
    private JFrame frame;

    private JTextField IdField;
    private JPanel Main_Frame;
    private JButton deleteButton;

    private String type;

    public Delete(String type) {
        this.type = type;
        frame = new JFrame("Delete_" + type);
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IdField.getText().isEmpty()){
                    if(type.equals("User")){
                        if(checkUserHaveRentsSQL(Integer.parseInt(IdField.getText()))){
                            deleteUser(Integer.parseInt(IdField.getText()));
                            Run.menu.getUserGui().filter();
                            IdField.setText("");
                        }else{
                            System.out.println("Error");
                        }
                    }else{
                        if(checkMediaHaveRentsSQL(Integer.parseInt(IdField.getText()))){
                            deleteMedia(Integer.parseInt(IdField.getText()));
                            Run.menu.getMediaGui().filter();
                            IdField.setText("");
                        }else{
                            System.out.println("Error");
                        }
                    }
                }
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
    public void clear() {
        IdField.setText("");
    }
    public void dispose(){
        frame.dispose();
    }
}
