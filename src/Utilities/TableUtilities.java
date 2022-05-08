package Utilities;

import Classes.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableUtilities {
    public static Object[][] makeTablePart(ArrayList<User> users) {
        Object[][] partsForTable = new Object[users.size()][];
        User user;
        Object[] object;

        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            object = new Object[]{user.getId(), user.getName(), user.getEgn(), user.getPhone(), user.getAddress()};
            partsForTable[i] = object;
        }

        return partsForTable;
    }
    public static ArrayList<User> searchFilter(ArrayList<User> users, JComboBox comboBox, String value) {
        ArrayList<User> newUsers = new ArrayList<>();

        if(value.isEmpty()){
            return users;
        }

        String index = String.valueOf(comboBox.getSelectedItem());
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(value);
        boolean matchFound = matcher.matches();

        switch (index) {
            case "Id" -> {
                if(matchFound){
                    int id = Integer.parseInt(value);
                    for (User user : users) {
                        if (user.getId() == id) {
                            newUsers.add(user);
                            break;
                        }
                    }
                }// Да се допише
            }
            case "Name" -> {
                for (User user : users) {
                    if (user.getName().equalsIgnoreCase(value)) {
                        newUsers.add(user);
                    }
                }
            }
        }
        return newUsers;
    }
}
