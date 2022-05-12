package Gui;

import Classes.GuiInt;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Utilities.MediaUtilities.insertMedia;

public class Insert_Media implements GuiInt {
    private JFrame frame;

    private JPanel Main_Frame;
    private JTextField Name;
    private JTextField Author;
    private JTextField Year;
    private JTextField Genre;
    private JTextArea Description;
    private JButton addButton;
    private JTextField Quantity;

    public Insert_Media() {
        frame = new JFrame("Insert Media");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!Name.getText().isEmpty()){
                    insertMedia(Name.getText(),Author.getText(), Integer.parseInt(Year.getText()), Genre.getText(), Description.getText(), Integer.parseInt(Quantity.getText()));
                    Run.menu.getMediaGui().filter();
                    clear();

                    //frame.dispose();
                }
            }
        });
    }

    public void visible(){
        frame.setVisible(true);
    }
    public void clear(){
        Name.setText("");
        Author.setText("");
        Year.setText("");
        Genre.setText("");
        Quantity.setText("");
        Description.setText("");
    }
}
