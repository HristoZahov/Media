package Utilities;

import Classes.Media;
import Classes.Order;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static Classes.GenerateQRCode.makeQRCode;
import static DataBase.DB_Connect.closeConnection;
import static DataBase.DB_Connect.openConnection;

public class MediaUtilities {
    private static Connection conn;

    //Insert Media
    public static void insertMedia(String name, String author, int year, String genre, String description, int quantity){
        conn = openConnection();

        addMedia(name, author);
        addMediaDetails(getMediaId(), year, genre, description, quantity);
        Media media = getOneMedia(getMediaId());
        String picture = createQRCode(media);

        if(picture != null){
            addPicture(picture,media.getId());
        }else{
            deleteMedia(media.getId());
        }

        closeConnection(conn);
    }

    public static Integer getMediaId(){
        conn = openConnection();

        String getIdSql = "SELECT Id FROM media_project.media Order By Id desc limit 1;";
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(getIdSql);

            if(result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }

        return 0;
    }
    private static void addMedia(String name, String author){
        String sql = "INSERT INTO `media_project`.`media` (`Name`,`Author`) VALUES (?,?);";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,name);
            statement.setString(2,author);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addMediaDetails(int id, int year, String genre, String description, int quantity){
        conn = openConnection();

        String media_detail_user = "INSERT INTO `media_project`.`media_details` (`Media_Id`, `Year`, `Genre`, `Description`, `Quantity`) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setInt(1, id);
            statement.setInt(2, year);
            statement.setString(3, genre);
            statement.setString(4, description);
            statement.setInt(5, quantity);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addPicture(String pathPicture, int id){
        conn = openConnection();

        String media_detail_user = "UPDATE `media_project`.`media_details` SET `Picture` = ? Where Media_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setString(1, pathPicture);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Create QR Code
    public static String createQRCode(Media media){
        String name = "Media " + media.getId() + ".png";
        try {
            makeQRCode(media.toString(), "src\\QR_Codes\\"+ name);
            return name;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Delete QR File
    public static void deleteQRCode(String path){
        conn = openConnection();
        File file = new File(path);
        file.delete();
    }

    //Edit Media
    public static  void updateMedia(Media media){
        conn = openConnection();

        updateMediaSQL(media);
        updateMediaDetailsSQL(media);

        closeConnection(conn);
    }
    private static void updateMediaSQL(Media media){
        String sql = "UPDATE `media_project`.`media` SET Name = ?, Author = ? Where Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, media.getName());
            statement.setString(2, media.getAuthor());
            statement.setInt(3, media.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateMediaDetailsSQL(Media media){
        conn = openConnection();
        String sql = "UPDATE `media_project`.`media_details` SET Year = ?, Genre = ?, Description = ?, Quantity = ?, Picture = ? Where Media_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,media.getYear());
            statement.setString(2, media.getGenre());
            statement.setString(3, media.getDescription());
            statement.setInt(4,media.getQuantity());
            statement.setString(5, media.getPicture_path());
            statement.setInt(6,media.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Edit Quantity
    public static void updateMediaQuantitySQL(Media media, boolean positive){
        conn = openConnection();
        int num;
        if(positive){
            num = 1;
        }else{
            num = -1;
        }
        String sql = "UPDATE `media_project`.`media_details` SET Quantity = ? Where Media_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,media.getQuantity()+num);
            statement.setInt(2,media.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Delete Media
    public static void deleteMedia(int id){
        conn = openConnection();

        deleteQRCode("src\\QR_Codes\\" + getOneMedia(id).getPicture_path());
        deleteMediaDetailsSQL(id);
        deleteMediaSQL(id);

        closeConnection(conn);
    }
    private static void deleteMediaSQL(int id){
        String sql = "DELETE FROM `media_project`.`media` Where Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void deleteMediaDetailsSQL(int id){//Допиши
        String sql = "DELETE FROM `media_project`.`media_details` Where Media_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Search Medias
    public static ArrayList<Media> getSearchedMedias(JComboBox comboBox, String value, boolean existed){
        return addMediasInArray(getSearchedMediasSQL(comboBox.getSelectedItem().toString(),value, existed));
    }

    private static ResultSet getSearchedMediasSQL(String field, String value, boolean existed){
        conn = openConnection();
        String sql = "SELECT m.Id, m.Name, m.Author, d.Year, d.Genre, d.Description, d.Quantity, d.Picture\n" +
                "FROM media_project.media As m LEFT JOIN media_project.media_details As d \n" +
                "On m.Id = d.Media_Id ";

        String nameFilter = "Where m.Name like ? ";
        String authorFilter = "Where m.Author like ? ";
        String yearFilter = "Where d.Year = ? ";
        String genreFilter = "Where d.Genre like ? ";
        ResultSet result = null;

        String existedSQL = "and not Quantity = 0;";
        if(existed){
            nameFilter += existedSQL;
            authorFilter += existedSQL;
            yearFilter += existedSQL;
            genreFilter += existedSQL;
        }

        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(value);
        boolean matchFound = matcher.matches();

        if(!value.isEmpty()){
            try {
                switch (field){
                    case "Name" ->{
                        PreparedStatement statement = conn.prepareStatement(sql + nameFilter);
                        statement.setString(1,"%" + value + "%");
                        result = statement.executeQuery();
                    }
                    case "Author" ->{
                        PreparedStatement statement = conn.prepareStatement(sql + authorFilter);
                        statement.setString(1,"%" + value + "%");
                        result = statement.executeQuery();
                    }
                    case "Year" ->{
                        if(matchFound){
                            PreparedStatement statement = conn.prepareStatement(sql + yearFilter);
                            statement.setInt(1,Integer.parseInt(value));
                            result = statement.executeQuery();
                        }
                    }
                    case "Genre" ->{
                        PreparedStatement statement = conn.prepareStatement(sql + genreFilter);
                        statement.setString(1,"%" + value + "%");
                        result = statement.executeQuery();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            if(existed){
                return getExistedMediasSQL();
            }else{
                return getAllMediasSQL();
            }
        }

        return result;
    }
    private static ArrayList<Media> addMediasInArray(ResultSet result){
        Media media;
        ArrayList<Media> medias = new ArrayList<>();

        while (true) {
            try {
                if(result != null){
                    while (result.next()){
                        media = new Media(result.getInt("Id"), result.getString("Name"),
                                result.getString("Author"), result.getInt("Year"),
                                result.getString("Genre"), result.getString("Description"),
                                result.getInt("Quantity"), result.getString("Picture"));
                        medias.add(media);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                closeConnection(conn);
                return medias;
            }
        }
    }

    //Get All Media
    public static ArrayList<Media> getAllMedias(){
        return addMediasInArray(getAllMediasSQL());
    }
    private static ResultSet getAllMediasSQL(){
        conn = openConnection();
        String sql = "SELECT m.Id, m.Name, m.Author, d.Year, d.Genre, d.Description, d.Quantity, d.Picture\n" +
                "FROM media_project.media As m LEFT JOIN  media_project.media_details As d \n" +
                "On m.Id = d.Media_Id;";
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Get One Media
    public static Media getOneMedia(int id){
        conn = openConnection();
        Media media = null;
        try {
            ResultSet result = getOneMediaSQL(id);

            if(result.next()){
                media = new Media(result.getInt("Id"), result.getString("Name"),
                        result.getString("Author"), result.getInt("Year"),
                        result.getString("Genre"), result.getString("Description"),
                        result.getInt("Quantity"), result.getString("Picture"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }

        return media;
    }
    private static ResultSet getOneMediaSQL(int id){
        String sql = "SELECT m.Id, m.Name, m.Author, d.Year, d.Genre, d.Description, d.Quantity, d.Picture\n" +
                "FROM media_project.media As m LEFT JOIN  media_project.media_details As d \n" +
                "On m.Id = d.Media_Id Where Id = ?;";
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1,id);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Get Order Medias
    public static Object[][] getOrdersArray(boolean isLated){
        conn = openConnection();

        ResultSet result = getOrdersSQL(isLated);

        Object[][] objects = new Object[getSize(result)][];
        Object[] object;

        int count = 0;
        try{
            result = getOrdersSQL(isLated);
            while(result.next()){
                object = new Object[]{result.getString("User"), result.getString("Media"), result.getDate("Start_Date"), result.getDate("End_Date"),false};
                objects[count] = object;
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }

        return objects;
    }
    private static ResultSet getOrdersSQL(boolean isLated){
        Date currentDate = Date.valueOf(LocalDate.now());
        String sql = "SELECT u.Name As User, m.Name As Media, r.Start_Date, r.End_Date\n" +
                "FROM media_project.rent As r \n" +
                "left join media_project.media As m on r.Media_Id = m.Id\n" +
                "left join media_project.user As u on r.User_Id = u.Id\n" +
                "Where r.IsReturned = 0 ";
        ResultSet result = null;
        if (isLated){
            sql += "and r.End_Date < ?;";
        }
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            if(isLated){
                statement.setDate(1, currentDate);
            }
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    private static Integer getSize(ResultSet result){
        int count = 0;
        if(result != null){
            try {
                while (result.next()){
                    count++;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return count;
    }

    //Get Existed Media
    public static ArrayList<Media> getExistedMedias(){
        return addMediasInArray(getExistedMediasSQL());
    }
    private static ResultSet getExistedMediasSQL(){
        conn = openConnection();
        String sql = "SELECT m.Id, m.Name, m.Author, d.Year, d.Genre, d.Description, d.Quantity, d.Picture\n" +
                "FROM media_project.media As m LEFT JOIN  media_project.media_details As d \n" +
                "On m.Id = d.Media_Id Where not Quantity = 0;";
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Order Search
    public static Object[][] getSearchedInArray(String field, String value, boolean isLated, ArrayList<Order> orders){
        conn = openConnection();

        ResultSet result = getSearchedSQL(field,value,isLated);

        if(result == null){
            return null;
        }
        Object[][] objects = new Object[getSize(result)][];
        Object[] object;

        int count = 0;
        boolean isExisted = false;
        try{
            result = getSearchedSQL(field,value,isLated);
            while(result.next()){
                for(Order order:orders){
                    if(order.getUser().equals(result.getString("User")) &&
                            order.getMedia().equals(result.getString("Media")) &&
                            order.getStart_date().equals(result.getDate("Start_Date")) &&
                            order.getEnd_date().equals(result.getDate("End_Date"))){
                        isExisted = true;
                        break;
                    }
                }

                if(isExisted){
                    object = new Object[]{result.getString("User"), result.getString("Media"), result.getDate("Start_Date"), result.getDate("End_Date"),true};
                }else{
                    object = new Object[]{result.getString("User"), result.getString("Media"), result.getDate("Start_Date"), result.getDate("End_Date"),false};
                }
                objects[count] = object;
                count++;
                isExisted = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }

        return objects;
    }
    public static ResultSet getSearchedSQL(String field, String value, boolean isLated){
        conn = openConnection();

        Date currentDate = Date.valueOf(LocalDate.now());
        String sql = "SELECT u.Name As User, m.Name As Media, r.Start_Date, r.End_Date\n" +
                "FROM media_project.rent As r \n" +
                "left join media_project.media As m on r.Media_Id = m.Id\n" +
                "left join media_project.user As u on r.User_Id = u.Id\n" +
                "Where r.IsReturned = 0 ";

        String userFilter = "and u.Name like ? ";
        String mediaFilter = "and m.Name like ? ";
        String startFilter = "and r.Start_Date = ? ";
        String endFilter = "and r.End_Date = ? ";

        ResultSet result = null;

        if(isLated){
            userFilter += "and r.End_Date < ? ";
            mediaFilter += "and r.End_Date < ? ";
            startFilter += "and r.End_Date < ? ";
            endFilter += "and r.End_Date < ? ";
        }

        if(!value.isEmpty()){
            try {
                switch (field){
                    case "User" ->{
                        PreparedStatement statement = conn.prepareStatement(sql + userFilter);
                        statement.setString(1,"%" + value + "%");
                        if (isLated){
                            statement.setDate(2, currentDate);
                        }
                        result = statement.executeQuery();
                    }
                    case "Media" ->{
                        PreparedStatement statement = conn.prepareStatement(sql + mediaFilter);
                        statement.setString(1,"%" + value + "%");
                        if (isLated){
                            statement.setDate(2, currentDate);
                        }
                        result = statement.executeQuery();
                    }
                    case "Start Date" ->{
                        if(value.length() == 10){
                            PreparedStatement statement = conn.prepareStatement(sql + startFilter);
                            statement.setDate(1, Date.valueOf(value));
                            if (isLated){
                                statement.setDate(2, currentDate);
                            }
                            result = statement.executeQuery();
                        }
                    }
                    case "End Date" ->{
                        if(value.length() == 10) {
                            PreparedStatement statement = conn.prepareStatement(sql + endFilter);
                            statement.setDate(1, Date.valueOf(value));
                            if (isLated){
                                statement.setDate(2, currentDate);
                            }
                            result = statement.executeQuery();
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            return getOrdersSQL(isLated);
        }

        return result;
    }

    //Return
    public static void makeReturn(ArrayList<Order> orders){
        for(Order order:orders){
            int idOrder = makeReturnIdSQL(order);
            int mediaId = makeReturnMediaIdSQL(order);
            makeReturnSQL(idOrder);
            updateMediaQuantitySQL(getOneMedia(mediaId),true);
        }
    }
    private static Integer makeReturnIdSQL(Order order){
        conn = openConnection();

        String media_detail_user = "SELECT r.Id FROM media_project.rent As r left join media_project.user As u on r.User_Id = u.Id \n" +
                "left join media_project.media As m on r.Media_Id = m.Id \n" +
                "Where u.Name = ? and m.Name = ? \n" +
                "and r.Start_Date = ? and r.End_Date = ? \n" +
                "and r.IsReturned = 0;";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setString(1, order.getUser());
            statement.setString(2, order.getMedia());
            statement.setDate(3, (Date) order.getStart_date());
            statement.setDate(4, (Date) order.getEnd_date());

            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
        return 0;
    }
    private static Integer makeReturnMediaIdSQL(Order order){
        conn = openConnection();

        String media_detail_user = "SELECT r.Media_Id FROM media_project.rent As r left join media_project.user As u on r.User_Id = u.Id \n" +
                "left join media_project.media As m on r.Media_Id = m.Id \n" +
                "Where u.Name = ? and m.Name = ? \n" +
                "and r.Start_Date = ? and r.End_Date = ? \n" +
                "and r.IsReturned = 0;";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setString(1, order.getUser());
            statement.setString(2, order.getMedia());
            statement.setDate(3, (Date) order.getStart_date());
            statement.setDate(4, (Date) order.getEnd_date());

            ResultSet result = statement.executeQuery();
            if(result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
        return 0;
    }
    private static void makeReturnSQL(int id){
        conn = openConnection();

        String media_detail_user = "UPDATE media_project.rent SET isReturned = 1 Where Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setInt(1,id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }
    }

    public static Object[][] getDedicated(String field, String value, JComboBox box){
        switch (box.getSelectedIndex()){
            case 0 -> {
                return getSearchedDedicatedInArray(field,value,false);
            }
            case 1 -> {
                return getSearchedDedicatedInArray(field,value,true);
            }
        }
        return null;
    }
    public static Object[][] getSearchedDedicatedInArray(String field, String value, boolean isLated){
        conn = openConnection();

        ResultSet result = getSearchedSQL(field,value,isLated);

        if(result == null){
            return null;
        }

        Object[][] objects = new Object[getSize(result)][];
        Object[] object;

        int count = 0;
        try{
            result = getSearchedSQL(field,value,isLated);
            while(result.next()){
                object = new Object[]{result.getString("Media"), result.getDate("Start_Date"), result.getDate("End_Date")};
                objects[count] = object;
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }

        return objects;
    }

    //Delete Check
    public static Boolean checkMediaHaveRentsSQL(int id) {
        conn = openConnection();

        String sql = "SELECT m.Id \n" +
                "FROM media_project.rent As r \n" +
                "left join media_project.media As m on r.Media_Id = m.Id\n" +
                "left join media_project.user As u on r.User_Id = u.Id\n" +
                "Where r.IsReturned = 0 and r.Media_Id = ?;";

        ResultSet result;

        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();

            if(result.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
        return true;
    }
}
