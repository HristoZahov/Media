package Gui;

import Classes.Media;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Utilities.MediaUtilities.getSearchedMedias;
import static Utilities.TableUtilities.*;

public class Insert_Edit_Media {
    private JFrame frame;

    private Insert_Media insertMediaGui;
    private Edit_Media editMediaGui;
    private Delete deleteGui;

    private ArrayList<Media> medias;
    private DefaultTableModel model;
    private String[] head = {"Id","Name","Author","Year","Genre","Quantity"};

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;
    private JButton Insert;
    private JButton Edit;
    private JLabel Title;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton Search;
    private JButton deleteButton;

    public Insert_Edit_Media() {
        frame = new JFrame("Insert Edit Media");
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
        medias = new ArrayList<>();

        crateTable(makeTablePartMedias(medias));
        makeSearchComboBox();
        addLiseners();
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
                if(insertMediaGui == null){
                    insertMediaGui = new Insert_Media();
                }else{
                    insertMediaGui.clear();
                    insertMediaGui.visible();
                }
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
            }
        });

        Edit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(editMediaGui == null){
                    editMediaGui = new Edit_Media();
                }else{
                    editMediaGui.clear();
                    editMediaGui.visible();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(deleteGui == null){
                    deleteGui = new Delete("Media");
                }else{
                    deleteGui.clear();
                    deleteGui.visible();
                }
            }
        });

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setText("");
            }
        });
    }
    private void makeSearchComboBox(){
        String[] type = {"Id","Name","Author","Year","Genre","Quantity","All Medias"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox1.setModel(model);
    }
    private void crateTable(Object[][] medias){
        model = new DefaultTableModel(medias,head){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Table.getTableHeader().setReorderingAllowed(false);
        Table.setModel(model);
    }
    public void filter(){
        model = new DefaultTableModel(makeTablePartMedias(getSearchedMedias(comboBox1, textField1.getText())),head);
        Table.setModel(model);
    }

    public void visible(){
        frame.setVisible(true);
    }
}
