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
    // since there are only 2 types of users, then we can check if this is a teacher and insert data to the corresponding table.
    public static boolean registerUser(String username, String password, String email, boolean isTeacher) throws SQLException {
        int rsNo = 0;
        String sql;
        if (isTeacher) {
            sql = "insert into teacher (teacher_username, password, email) values (?, ?, ?)";
        } else {
            sql = "insert into student(student_username, password, email) values (?, ?, ?)";
        }
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password.substring(0, 50));
        pre.setString(3, email);
        try {
            rsNo = pre.executeUpdate(); // try to insert a new row
        } catch (Exception e) {
            System.out.println("Error");
            return false;
        }
        System.out.println("rs No: " + rsNo);
        return rsNo == 1;
    }

    public static boolean isRegisteredUser(String username, boolean isTeacher) throws SQLException {
        String sql;
        if (isTeacher) {
            sql = "select top 1 * from [OnlineQuiz].[dbo].[teacher] where teacher_username= ?";
        } else {
            sql = "select top 1 * from [OnlineQuiz].[dbo].[student] where student_username= ?";
        }
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        rs = pre.executeQuery();
        return rs.next();
    }

    public static boolean isLoggedInSuccessfully(String username, String password, boolean isTeacher) throws SQLException {
        String sql;
        if (isTeacher) {
            sql = "select top 1 * from [OnlineQuiz].[dbo].[teacher] where teacher_username = ? and password = ?";
        } else {
            sql = "select top 1 * from [OnlineQuiz].[dbo].[student] where student_username = ? and password = ?";
        }
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        pre.setString(2, password);
        rs = pre.executeQuery();
        return rs.next();
    }
    public static boolean isUserATeacher(String username) throws SQLException {
        String sql = "select top 1 * from [OnlineQuiz].[dbo].[teacher] where teacher_username = ?";
        pre = connection.prepareStatement(sql);
        pre.setString(1, username);
        rs = pre.executeQuery();
        return rs.next();
    }
}
