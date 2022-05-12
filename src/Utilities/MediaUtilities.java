package Utilities;

import Classes.Media;
import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;
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
    }//Да се добави баркод
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
            statement.setInt(3,getMediaId());
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
    }//Да се добави бар код

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
    public static ArrayList<Media> getSearchedMedias(JComboBox comboBox, String value){
        return addMediasInArray(getSearchedMediasSQL(comboBox.getSelectedItem().toString(),value));
    }

    private static ResultSet getSearchedMediasSQL(String field, String value){
        conn = openConnection();
        String sql = "SELECT m.Id, m.Name, m.Author, d.Year, d.Genre, d.Description, d.Quantity, d.Picture\n" +
                "FROM media_project.media As m LEFT JOIN media_project.media_details As d \n" +
                "On m.Id = d.Media_Id ";

        String nameFilter = "Where m.Name = ?;";
        String authorFilter = "Where m.Author = ?;";
        String idFilter = "Where m.Id = ?;";
        String yearFilter = "Where d.Year = ?;";
        String genreFilter = "Where d.Genre = ?;";
        String quantityFilter = "Where d.Quantity = ?;";
        ResultSet result = null;

        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(value);
        boolean matchFound = matcher.matches();

        try {
            switch (field){
                case "Id" ->{
                    if(matchFound){
                        PreparedStatement statement = conn.prepareStatement(sql + idFilter);
                        statement.setInt(1,Integer.parseInt(value));
                        result = statement.executeQuery();
                    }
                }
                case "Name" ->{
                    PreparedStatement statement = conn.prepareStatement(sql + nameFilter);
                    statement.setString(1,value);
                    result = statement.executeQuery();
                }
                case "Author" ->{
                    PreparedStatement statement = conn.prepareStatement(sql + authorFilter);
                    statement.setString(1,value);
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
                    statement.setString(1,value);
                    result = statement.executeQuery();
                }
                case "Quantity" ->{
                    if(matchFound){
                        PreparedStatement statement = conn.prepareStatement(sql + quantityFilter);
                        statement.setInt(1,Integer.parseInt(value));
                        result = statement.executeQuery();
                    }
                }
                case "All Medias" ->{
                    result = getAllMediasSQL();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
}
