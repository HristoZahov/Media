package Gui;

import Classes.Media;
import Classes.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static Utilities.MediaUtilities.getExistedMedias;
import static Utilities.MediaUtilities.getSearchedMedias;
import static Utilities.TableUtilities.*;
import static Utilities.UserUtilities.*;

public class Rent {
    private ArrayList<String> MediaNames;
    private ArrayList<String> UserNames;

    private ArrayList<User> users;
    private DefaultTableModel modelUser;
    private String[] headUser = {"Name","EGN","Phone","Select"};

    private ArrayList<Media> medias;
    private DefaultTableModel modelMedia;
    private String[] head = {"Name","Author","Year","Genre","Quantity","Select"};

    private JFrame frame;
    private JPanel Main_Frame;
    private JTable table1;
    private JTable table2;
    private JTextField MediaName;
    private JTextField UserName;
    private JButton searchUserButton;
    private JButton searchMediaButton;
    private JButton Rent;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JTextField Date;
    private JButton backButton;
    private JPanel Header;

    public Rent() {
        frame = new JFrame("Rent");
        frame.setContentPane(Main_Frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Border border = new LineBorder(Color.BLACK, 3, false);
        Header.setBorder(border);

        //Size
        frame.setBounds(250,200,1000,400); //370,300
        //frame.setResizable(false);

        MediaNames = new ArrayList<>();
        UserNames = new ArrayList<>();

        users = getAllUsers();
        crateUserTable(makeTablePartUsersRent(users,UserNames));
        makeSearchUserComboBox();

        medias = getExistedMedias();
        crateMediaTable(makeTablePartMediasRent(medias, MediaNames));
        makeSearchMediaComboBox();

        addLiseners();
    }

    public static void main(String[] args) {
        new Rent();
    }

    private void addLiseners(){
        Rent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrRemoveMedia();
                addOrRemoveUser();

                if(!UserNames.isEmpty() && !UserNames.isEmpty() && !Date.getText().isEmpty()){
                    makeRent(MediaNames,UserNames,Date.getText());
                    UserNames.clear();
                    MediaNames.clear();

                    users = getAllUsers();
                    crateUserTable(makeTablePartUsersRent(users,UserNames));

                    medias = getExistedMedias();
                    crateMediaTable(makeTablePartMediasRent(medias, MediaNames));

                    mediaFilter();
                    userFilter();
                    Date.setText("");

                    if(Run.menu.getMediaGui() != null){
                        Run.menu.getMediaGui().filter();
                    }
                }
            }
        });
        searchMediaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaFilter();
            }
        });
        searchUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOrRemoveUser();
                userFilter();
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Run.menu.getRent_return().visible();
                frame.dispose();
            }
        });
    }
    private void addOrRemoveMedia(){
        boolean isExist = false;

        for (int i = 0; i < modelMedia.getRowCount(); i++) {
            if((Boolean) modelMedia.getValueAt(i, 5)){
                for(String name: MediaNames){
                    if (modelMedia.getValueAt(i,0).toString().equals(name)) {
                        isExist = true;
                    }
                }

                if (!isExist) {
                    MediaNames.add(modelMedia.getValueAt(i,0).toString());
                }
            }else{
                for(String name: MediaNames){
                    if (modelMedia.getValueAt(i,0).toString().equals(name)) {
                        isExist = true;
                    }
                }

                if (isExist) {
                    MediaNames.remove(modelMedia.getValueAt(i,0).toString());
                }
            }
            isExist = false;
        }
    }
    private void addOrRemoveUser(){
        boolean isExist = false;

        for (int i = 0; i < modelUser.getRowCount(); i++) {
            if((Boolean) modelUser.getValueAt(i, 3)){
                for(String name: UserNames){
                    if (modelUser.getValueAt(i,0).toString().equals(name)) {
                        isExist = true;
                    }
                }

                if (!isExist) {
                    UserNames.add(modelUser.getValueAt(i,0).toString());
                }
            }else{
                for(String name: UserNames){
                    if (modelUser.getValueAt(i,0).toString().equals(name)) {
                        isExist = true;
                    }
                }

                if (isExist) {
                    UserNames.remove(modelUser.getValueAt(i,0).toString());
                }
            }
            isExist = false;
        }
    }

    private void crateUserTable(Object[][] users){
        modelUser = new DefaultTableModel(users, headUser){
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0,1,2:
                        return String.class;
                    default:
                        return Boolean.class;
                }
            }
        };

        table2.getTableHeader().setReorderingAllowed(false);
        table2.setModel(modelUser);
    }
    private void makeSearchUserComboBox(){
        String[] type = {"Name","EGN","Phone","Address"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox2.setModel(model);
    }
    public void userFilter(){
        addOrRemoveUser();
        crateUserTable(makeTablePartUsersRent(getSearchedUsers(comboBox2,UserName.getText()),UserNames));
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
    private void makeSearchMediaComboBox(){
        String[] type = {"Name","Author","Year","Genre"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(type);
        comboBox1.setModel(model);
    }
    public void mediaFilter(){
        addOrRemoveMedia();
        crateMediaTable(makeTablePartMediasRent(getSearchedMedias(comboBox1, MediaName.getText(),true), MediaNames));
    }

    public void visible(){
        frame.setVisible(true);
    }
}
