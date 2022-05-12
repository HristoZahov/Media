package Utilities;

import Classes.Media;
import Classes.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TableUtilities {
    public static Object[][] makeTablePartUsers(ArrayList<User> users) {
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
    public static Object[][] makeTablePartMedias(ArrayList<Media> medias) {
        Object[][] partsForTable = new Object[medias.size()][];
        Media media;
        Object[] object;

        for (int i = 0; i < medias.size(); i++) {
            media = medias.get(i);
            object = new Object[]{media.getId(),media.getName(),media.getAuthor(),media.getYear(),media.getGenre(),media.getQuantity()};
            partsForTable[i] = object;
        }

        return partsForTable;
    }
}
