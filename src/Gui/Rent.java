package Gui;

import Classes.Media;
import Classes.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

import static Utilities.MediaUtilities.getAllMedias;
import static Utilities.TableUtilities.*;
import static Utilities.UserUtilities.getAllUsers;

public class Rent {
    private ArrayList<User> users;
    private DefaultTableModel modelUser;
    private String[] headUser = {"Name","EGN","Phone"};

    private ArrayList<Media> medias;
    private DefaultTableModel modelMedia;
    private String[] head = {"Name","Author","Year","Genre","Quantity","Select"};

    private JPanel Main_Frame;
    private JTable table1;
    private JTable table2;
    private JTextField MediaName;
    private JTextField UserName;
    private JButton searchUserButton;
    private JButton searchMediaButton;
    private JButton Rent;

    public Rent() {
        JFrame frame = new JFrame("Rent");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        users = getAllUsers();
        crateUserTable(makeTablePartUsersRent(users));

        medias = getAllMedias();
        crateMediaTable(makeTablePartMediasRent(medias));
    }

    public static void main(String[] args) {
        new Rent();
    }

    private void crateUserTable(Object[][] users){
        modelUser = new DefaultTableModel(users, headUser){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table2.getTableHeader().setReorderingAllowed(false);
        table2.setModel(modelUser);
    }
    private void crateMediaTable(Object[][] medias){
        modelMedia = new DefaultTableModel(medias,head){
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return medias[0][columnIndex].getClass();
            }
        };

        table1.getTableHeader().setReorderingAllowed(false);
        table1.setModel(modelMedia);
    }
}
