package tewi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://194.163.35.1:3306/u797587982_husnap";
        String username = "u797587982_husnap";
        String password = "tewi^uOWl&c[z62&O";

        System.out.println("Connecting to the database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected successfully!");
            
            // Use the connection to query the database...

        } catch (SQLException e) {
            System.out.println("Unable to connect to the database.");
            e.printStackTrace();
        }
    }
}