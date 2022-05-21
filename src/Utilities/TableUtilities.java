package Utilities;

import Classes.Media;
import Classes.User;

import java.util.ArrayList;

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
    public static Object[][] makeTablePartUsersRent(ArrayList<User> users, ArrayList<String> names) {
        Object[][] partsForTable = new Object[users.size()][];
        User user;
        Object[] object;

        boolean isExist = false;
        for (int i = 0; i < users.size(); i++) {
            user = users.get(i);
            for(String name: names){
                if(user.getName().equals(name)){
                    isExist = true;
                }
            }

            if(isExist){
                object = new Object[]{user.getName(), user.getEgn(), user.getPhone(), true};
            }else{
                object = new Object[]{user.getName(), user.getEgn(), user.getPhone(), false};
            }
            partsForTable[i] = object;

            isExist = false;
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
    public static Object[][] makeTablePartMediasRent(ArrayList<Media> medias, ArrayList<String> names) {
        Object[][] partsForTable = new Object[medias.size()][];
        Media media;
        Object[] object;

        boolean isExist = false;
        for (int i = 0; i < medias.size(); i++) {
            media = medias.get(i);
            for(String name: names){
                if(media.getName().equals(name)){
                    isExist = true;
                }
            }
            if(isExist){
                object = new Object[]{media.getName(),media.getAuthor(),media.getYear(),media.getGenre(),media.getQuantity(), true};
            }else{
                object = new Object[]{media.getName(),media.getAuthor(),media.getYear(),media.getGenre(),media.getQuantity(), false};
            }
            partsForTable[i] = object;
            isExist = false;
        }

        return partsForTable;
    }
}
