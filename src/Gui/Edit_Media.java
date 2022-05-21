package Gui;

import Classes.GuiInt;
import Classes.Media;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Utilities.MediaUtilities.*;

public class Edit_Media implements GuiInt {
    private Media media;
    private JFrame frame;

    private JPanel Main_Frame;
    private JTextField Name;
    private JTextField Author;
    private JTextField Year;
    private JTextField Genre;
    private JTextArea Description;
    private JButton EditBtn;
    private JTextField IdField;
    private JButton enterButton;
    private JTextField Quantity;

    public Edit_Media() {
        frame = new JFrame("Edit Media");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        EditBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(media != null){
                    media.setName(Name.getText());
                    media.setAuthor(Author.getText());
                    media.setYear(Integer.parseInt(Year.getText()));
                    media.setGenre(Genre.getText());
                    media.setQuantity(Integer.parseInt(Quantity.getText()));
                    media.setDescription(Description.getText());
                    media.setPicture_path(createQRCode(media));

                    updateMedia(media);
                    Run.menu.getMediaGui().filter();
                    clear();
                }
            }
        });

        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!IdField.getText().isEmpty()){
                    media = getOneMedia(Integer.parseInt(IdField.getText()));
                    if(media != null){
                        Name.setText(media.getName());
                        Author.setText(media.getAuthor());
                        Year.setText(String.valueOf(media.getYear()));
                        Genre.setText(media.getGenre());
                        Quantity.setText(String.valueOf(media.getQuantity()));
                        Description.setText(media.getDescription());
                    }
                }else{
                    media = null;
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
        Author.setText("");
        Year.setText("");
        Genre.setText("");
        Quantity.setText("");
        Description.setText("");
    }
    public void dispose(){
        frame.dispose();
    }
}
