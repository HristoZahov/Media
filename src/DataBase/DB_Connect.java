package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

import static DataBase.Variables.*;

public class DB_Connect {
    public static Connection openConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, DB_USER, DB_PASS);
        } catch (Exception e) {
            System.out.println("Wrong DB name!");
        }
        return connection;
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (Exception e) {
            System.out.println("Connection doesn't exist!");
        }
    }
}
