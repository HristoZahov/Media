package DataBase;

import com.mysql.cj.jdbc.Blob;

import java.sql.*;

import static DataBase.DB_Connect.*;

public class DBUtilities {
    private static Connection conn;

    public static void insertUser(String name, String egn, String phone, String address, String description){
        conn = openConnection();

        addUser(name);
        addUserDetails(getUserId(), egn, phone, address, description);

        closeConnection(conn);
    }

    private static Integer getUserId(){
        String getIdSql = "SELECT Id FROM media_project.user Order By Id desc limit 1;";
        try {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(getIdSql);

            if(result.next()){
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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


}
