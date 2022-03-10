package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    private static final String URL="jdbc:postgresql://localhost:5432/lab";
    private static final String USER_NAME ="postgres";
    private static final String PASSWORD="root";

    public static Connection connect(){
        try {
            Connection connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
