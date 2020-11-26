package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private final Connection connection;
    public static DBConnection getInstance() throws ClassNotFoundException, SQLException {
        return new DBConnection();
    }
    private DBConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String url = "jdbc:sqlserver://localhost:1433;databaseName=OnlineQuiz;";
        String password = "";
        String username = "sa";
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public Connection getConnection() {
        return connection;
    }
    
    public static void closeConnection(Connection con, PreparedStatement pre, ResultSet rs) {
         if (rs != null && !rs.isClosed()) {
            rs.close();
        } if (pre != null && !pre.isClosed()) {
            pre.close();
        } if (con != null && !con.isClosed()) {
            con.close();
        }
     }
}
