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

import static Utilities.MediaUtilities.*;
import static Utilities.TableUtilities.*;

public class Existed_Medias {
    private JFrame frame;

    private ArrayList<Media> medias;
    private DefaultTableModel model;
    private String[] head = {"Id","Name","Author","Year","Genre","Quantity"};

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;
    private JLabel Title;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton Search;

    public Existed_Medias() {
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
        medias = getExistedMedias();

        crateTable(makeTablePartMedias(medias));
        makeSearchComboBox();
        addLiseners();
    }

    private void addLiseners(){
        Back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.getReport().visible();
                frame.dispose();
            }
        });

        Search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filter();
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
        String[] type = {"Name","Author","Year","Genre"};
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
        model = new DefaultTableModel(makeTablePartMedias(getSearchedMedias(comboBox1, textField1.getText(),true)),head);
        Table.setModel(model);
    }
    public void update(){
        medias = getExistedMedias();
        crateTable(makeTablePartMedias(medias));
    }

    public void visible(){
        frame.setVisible(true);
    }

    public static void main(String[] args) {
       new Existed_Medias();
    }
}
