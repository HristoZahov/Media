package Gui;

import Classes.Media;
import Classes.User;
import Utilities.UserUtilities;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Utilities.MediaUtilities.getAllMedias;
import static Utilities.TableUtilities.*;

public class AllMedias {
    private JFrame frame;

    private ArrayList<Media> medias;

    private JPanel Main_Frame;
    private JPanel Header;
    private JTable Table;
    private JButton Back;

    public AllMedias() {
        frame = new JFrame("All Medias");
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
        medias = getAllMedias();

        crateTable(makeTablePartMedias(medias));
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
    }
    private void crateTable(Object[][] medias){
        String[] head = {"Id","Name","Author","Year","Genre","Quantity"};
        DefaultTableModel model = new DefaultTableModel(medias,head){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        Table.getTableHeader().setReorderingAllowed(false);
        Table.setModel(model);
    }

    public static void main(String[] args) {
        new AllMedias();
    }
}
