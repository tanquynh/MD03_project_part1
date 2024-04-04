package ra.project_md03.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDatabase {
    private static final String URL = "jdbc:mysql://localhost:3306/md03_project_database";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Anhquynh12@";
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    public static  Connection openConnection() {
        Connection connection =null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL,USER_NAME,PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    };
    public static void closeConnection(Connection connection) {
        if(connection !=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
