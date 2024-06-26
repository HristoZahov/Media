package Utilities;

import Classes.User;

import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static DataBase.DB_Connect.*;
import static Utilities.MediaUtilities.getOneMedia;
import static Utilities.MediaUtilities.updateMediaQuantitySQL;

public class UserUtilities {
    private static Connection conn;

    //Insert User
    public static void insertUser(String name, String egn, String phone, String address, String description) {
        conn = openConnection();

        addUser(name);
        addUserDetails(getUserId(), egn, phone, address, description);

        closeConnection(conn);
    }

    public static Integer getUserId() {
        conn = openConnection();

        String getIdSql = "SELECT Id FROM media_project.user Order By Id desc limit 1;";
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(getIdSql);

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return 0;
    }

    private static void addUser(String name) {
        String sql = "INSERT INTO `media_project`.`user` (`Name`) VALUES (?);";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addUserDetails(int id, String egn, String phone, String address, String description) {
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

    //Edit User
    public static void updateUser(User user) {
        conn = openConnection();

        updateUserSQL(user.getId(), user.getName());
        updateUserDetailsSQL(user.getId(), user.getEgn(), user.getPhone(), user.getAddress(), user.getDescription());

        closeConnection(conn);
    }

    private static void updateUserSQL(int id, String name) {
        String sql = "UPDATE `media_project`.`user` SET Name = ? Where Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateUserDetailsSQL(int id, String egn, String phone, String address, String description) {//Допиши
        String sql = "UPDATE `media_project`.`user_details` SET EGN = ?, Phone = ?, Address = ?, Description = ? Where User_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, egn);
            statement.setString(2, phone);
            statement.setString(3, address);
            statement.setString(4, description);
            statement.setInt(5, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Get All Users
    public static ArrayList<User> getAllUsers() {
        return addUsersInArray(getAllUsersSQL());
    }

    private static ResultSet getAllUsersSQL() {
        conn = openConnection();
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

    //Get One User
    public static User getOneUser(int id) {
        conn = openConnection();
        User user = null;
        try {
            ResultSet result = getUserSQL(id);

            if (result.next()) {
                user = new User(result.getInt("Id"), result.getString("Name"),
                        result.getString("EGN"), result.getString("Phone"),
                        result.getString("Address"), result.getString("Description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return user;
    }

    private static ResultSet getUserSQL(int id) {
        String sql = "SELECT u.Id, u.Name, d.EGN, d.Phone, d.Address, d.Description \n" +
                "FROM media_project.user As u LEFT JOIN  media_project.user_details As d \n" +
                "On u.Id = d.User_Id Where Id = ?;";
        ResultSet result = null;
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            result = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Delete User
    public static void deleteUser(int id) {
        conn = openConnection();

        deleteUserDetailsSQL(id);
        deleteUserSQL(id);

        closeConnection(conn);
    }

    private static void deleteUserSQL(int id) {
        String sql = "DELETE FROM `media_project`.`user` Where Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteUserDetailsSQL(int id) {//Допиши
        String sql = "DELETE FROM `media_project`.`user_details` Where User_Id = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Search User
    public static ArrayList<User> getSearchedUsers(JComboBox comboBox, String value) {
        return addUsersInArray(getSearchedUsersSQL(comboBox.getSelectedItem().toString(), value));
    }

    private static ResultSet getSearchedUsersSQL(String field, String value) {
        conn = openConnection();
        String sql = "SELECT u.Id, u.Name, d.EGN, d.Phone, d.Address, d.Description \n" +
                "FROM media_project.user As u LEFT JOIN  media_project.user_details As d \n" +
                "On u.Id = d.User_Id ";

        String nameFilter = "Where u.Name like ?;";
        String egnFilter = "Where d.EGN like ?;";
        String phoneFilter = "Where d.Phone like ?;";
        String addressFilter = "Where d.Address like ?;";
        ResultSet result = null;

        if (!value.isEmpty()) {
            try {
                switch (field) {
                    case "Name" -> {
                        PreparedStatement statement = conn.prepareStatement(sql + nameFilter);
                        statement.setString(1, "%" + value + "%");
                        result = statement.executeQuery();
                    }
                    case "EGN" -> {
                        if (value.length() == 10) {
                            PreparedStatement statement = conn.prepareStatement(sql + egnFilter);
                            statement.setString(1, "%" + value + "%");
                            result = statement.executeQuery();
                        }
                    }
                    case "Phone" -> {
                        PreparedStatement statement = conn.prepareStatement(sql + phoneFilter);
                        statement.setString(1, "%" + value + "%");
                        result = statement.executeQuery();
                    }
                    case "Address" -> {
                        PreparedStatement statement = conn.prepareStatement(sql + addressFilter);
                        statement.setString(1, "%" + value + "%");
                        result = statement.executeQuery();
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            return getAllUsersSQL();
        }

        return result;
    }

    private static ArrayList<User> addUsersInArray(ResultSet result) {
        User user;
        ArrayList<User> users = new ArrayList<>();

        while (true) {
            try {
                if (result != null) {
                    while (result.next()) {
                        user = new User(result.getInt("Id"), result.getString("Name"),
                                result.getString("EGN"), result.getString("Phone"),
                                result.getString("Address"), result.getString("Description"));
                        users.add(user);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                closeConnection(conn);
                return users;
            }
        }
    }

    //Rent
    private static Integer getUserByNameId(String name) {
        conn = openConnection();

        String getIdSql = "SELECT Id FROM media_project.user Where Name = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(getIdSql);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return 0;
    }

    private static Integer getMediaByNameId(String name) {
        conn = openConnection();

        String getIdSql = "SELECT Id FROM media_project.media Where Name = ?;";
        try {
            PreparedStatement statement = conn.prepareStatement(getIdSql);
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }

        return 0;
    }

    public static void makeRent(ArrayList<String> mediaNames, ArrayList<String> userNames, String date) {
        for (String username : userNames) {
            int userId = getUserByNameId(username);
            for (String mediaName : mediaNames) {
                int mediaId = getMediaByNameId(mediaName);
                makeRentSQL(userId, mediaId, date);
                updateMediaQuantitySQL(getOneMedia(mediaId), false);
            }
        }
    }

    private static void makeRentSQL(int userId, int mediaId, String date) {
        conn = openConnection();

        Date currentDate = Date.valueOf(LocalDate.now());
        String media_detail_user = "INSERT INTO `media_project`.`rent` (`User_Id`, `Media_Id`, `Start_Date`, `End_Date`, `IsReturned`) VALUES (?, ?, ?, ?, '0');";
        try {
            PreparedStatement statement = conn.prepareStatement(media_detail_user);

            statement.setInt(1, userId);
            statement.setInt(2, mediaId);
            statement.setDate(3, currentDate);
            statement.setDate(4, Date.valueOf(date));

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(conn);
        }
    }

    //Delete Check

    public static Boolean checkUserHaveRentsSQL(int id) {
        conn = openConnection();

        String sql = "SELECT u.Id \n" +
                "FROM media_project.rent As r \n" +
                "left join media_project.media As m on r.Media_Id = m.Id\n" +
                "left join media_project.user As u on r.User_Id = u.Id\n" +
                "Where r.IsReturned = 0 and r.User_Id = ?;";

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
