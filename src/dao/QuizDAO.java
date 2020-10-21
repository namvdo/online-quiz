package dao;

import bean.QuizBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuizDAO {
    static Connection conn;
    static PreparedStatement pre;
    static ResultSet rs;
    static  {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param n - number of quizzes being fetched randomly
     * @return - a list of random quizzes
     * @throws SQLException
     */
    public static List<QuizBean> getQuizzesRandomly(int n) throws SQLException {
        List<QuizBean> quizzes = new ArrayList<>();
        String sql = "select top (?) * from [OnlineQuiz].[dbo].[quiz] order by NEWID()";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, n);
        rs = pre.executeQuery();
        while (rs.next()) {
           int quizId = rs.getInt(1);
           String quizDescription = rs.getString(2);
           String createdBy = rs.getString(3);
           byte weight = rs.getByte(4);
           Timestamp createdAt = rs.getTimestamp(5);
           Timestamp updatedAt = rs.getTimestamp(6);
           QuizBean quiz = new QuizBean(quizId, weight, quizDescription, createdBy, createdAt, updatedAt);
           quizzes.add(quiz);
        }
        return quizzes;
    }

    public static int getTheLatestQuizId() throws SQLException {
        String sql = "select [quiz_id] from [OnlineQuiz].[dbo].[quiz] order by quiz_id desc";
        pre = conn.prepareStatement(sql);
        rs = pre.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }
    public static boolean insertNewQuiz(int quizId, String quizDesc, String createdBy, int weight, Timestamp createdAt) throws SQLException {
        String sql = "insert into [OnlineQuiz].[dbo].[quiz](quiz_id, quiz_description, created_by, weight, created_at) values (?, ?, ?, ?, ?)";
        if (quizDesc.isEmpty()) return false;
        pre = conn.prepareStatement(sql);
        pre.setInt(1, quizId);
        pre.setString(2, quizDesc);
        pre.setString(3, createdBy);
        pre.setInt(4, weight);
        pre.setTimestamp(5, createdAt);
        return pre.executeUpdate() == 1;
    }

    public static int getNumOfQuizzes() throws SQLException {
        String sql = "select count(*) as count from [OnlineQuiz].[dbo].[quiz]";
        pre = conn.prepareStatement(sql);
        rs = pre.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    /**
     * This method is for pagination which is compatible with SQL Server 2008
     * @param from - the beginning row to fetch the data from
     * @param to - the ending row to fetch the record
     * @return - the list of record from 'from' to 'to'
     * @throws SQLException
     */
    public static List<QuizBean> getQuizzesWithOffset(int from, int to) throws SQLException {
        List<QuizBean> quizzes = new ArrayList<>();

        String sql = "WITH data\n" +
                "        AS\n" +
                "                (\n" +
                "                        SELECT ROW_NUMBER() OVER (ORDER BY quiz_id) AS row_id,\n" +
                "                        quiz_id,\n" +
                "                        quiz_description,\n" +
                "                        created_by,\n" +
                "                        created_at\n" +
                "                        FROM quiz\n" +
                "                )\n" +
                "        SELECT *\n" +
                "                FROM data\n" +
                "        WHERE row_id BETWEEN ? AND ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, from);
        pre.setInt(2, to);
        rs = pre.executeQuery();
        while (rs.next()) {
            QuizBean quiz = new QuizBean();
            quiz.setQuizDescription(rs.getString(3));
            quiz.setCreatedAt(rs.getTimestamp(5));
            quiz.setQuizId(rs.getInt(1));
            quizzes.add(quiz);
        }
        quizzes.forEach(System.out::println);
        return quizzes;
    }
}
