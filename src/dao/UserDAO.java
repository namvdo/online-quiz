package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static Connection connection;
    private static PreparedStatement pre;
    private static ResultSet rs;

    static {
        try {
            connection = DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("EROROROROR");
            e.printStackTrace();
        }
    }

    /**
     *
     * @param username - username be registered
     * @param password - password to be registered
     * @param email - email to be registered
     * @param isTeacher - whether this user registers as a teacher or a student
     * @return true if register successfully, false it doesn't
     * @throws SQLException
     */
    public static boolean registerUser(String username, String password, String email, boolean isTeacher) throws SQLException {
        int rsNo = 0;
        String sql = "insert into account(username, password, email, is_teacher) values (?, ?, ?, ?)";
        if (isRegisteredUser(username)) {
            return false;
        }

        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password.substring(0, 50));
        pre.setString(3, email);
        pre.setBoolean(4, isTeacher);
        try {
            rsNo = pre.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return rsNo == 1;
    }

    public static boolean isRegisteredUser(String username) throws SQLException {
        String sql = "select top 1 * from [onlinequiz].[dbo].[account] where account=?";
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        rs = pre.executeQuery();
        return rs.next();
    }

    public static boolean isLoggedInSuccessfully(String username, String password, boolean isTeacher) throws SQLException {
        String sql = "select top 1 * from [OnlineQuiz].[dbo].[account] where username = ? and password = ?";
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password);
        rs = pre.executeQuery();
        return rs.next();
    }
    public static boolean isUserATeacher(String username) throws SQLException {
        String sql = "select top 1 * from [OnlineQuiz].[dbo].[account] where username = ? and is_teacher = 1";
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        rs = pre.executeQuery();
        return rs.next();
    }
}
