package DataBase;

import Classes.User;

import java.sql.*;
import java.util.ArrayList;

import static DataBase.DB_Connect.*;

public class DBUtilities {
    private static Connection conn;

    public static void insertUser(String name, String egn, String phone, String address, String description){
        conn = openConnection();

        addUser(name);
        addUserDetails(getUserId(), egn, phone, address, description);

        closeConnection(conn);
    }

    public static Integer getUserId(){
        conn = openConnection();

        String getIdSql = "SELECT Id FROM media_project.user Order By Id desc limit 1;";
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
    private static void addUser(String name){
        String sql = "INSERT INTO `media_project`.`user` (`Name`) VALUES (?);";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void addUserDetails(int id, String egn, String phone, String address, String description){
        conn = openConnection();

        String media_detail_user = "INSERT INTO `media_project`.`user_details` (`User_Id`, `EGN`, `Phone`, `Address`, `Description`) VALUES (?, ?, ?, ?, ?);";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setInt(1, id);
            statement.setString(2, egn);
            statement.setString(3, phone);
            statement.setString(4, address);
            statement.setString(5, description);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static  void updateUser(int id, String name, String egn, String phone, String address, String description){
        conn = openConnection();

        updateUserSQL(id,name);
        updateDetailsSQL(id, egn, phone, address, description);

        closeConnection(conn);
    }
    private static void updateUserSQL(int id, String name){
        String sql = "UPDATE `media_project`.`user` SET Name = ? Where Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,name);
            statement.setInt(2,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void updateDetailsSQL(int id, String egn, String phone, String address, String description){//Допиши
        String sql = "UPDATE `media_project`.`user_details` SET EGN = ?, Phone = ?, Address = ?, Description = ? Where User_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,egn);
            statement.setString(2,phone);
            statement.setString(3,address);
            statement.setString(4,description);
            statement.setInt(5,id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getUsers(){
        conn = openConnection();

        User user;
        ArrayList<User> users = new ArrayList<>();

        while (true) {
            try {
                ResultSet result = getAllUsersSQL();
                while (result.next()){
                    user = new User(result.getInt("Id"), result.getString("Name"),
                            result.getString("EGN"), result.getString("Phone"),
                            result.getString("Address"), result.getString("Description"));
                    users.add(user);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                closeConnection(conn);
                return users;
            }
        }
    }
    private static ResultSet getAllUsersSQL(){
        String sql = "SELECT u.Id, u.Name, d.EGN, d.Phone, d.Address, d.Description \n" +
                "FROM media_project.user As u LEFT JOIN  media_project.user_details As d \n" +
                "On u.Id = d.User_Id;";
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static User getUser(int id){
        conn = openConnection();
        User user = null;
        try {
            ResultSet result = getUserSQL(id);

            if(result.next()){
                user = new User(result.getInt("Id"), result.getString("Name"),
                        result.getString("EGN"), result.getString("Phone"),
                        result.getString("Address"), result.getString("Description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeConnection(conn);
        }

        return user;
    }
    private static ResultSet getUserSQL(int id){
        String sql = "SELECT u.Id, u.Name, d.EGN, d.Phone, d.Address, d.Description \n" +
                "FROM media_project.user As u LEFT JOIN  media_project.user_details As d \n" +
                "On u.Id = d.User_Id Where Id = ?;";
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
}
