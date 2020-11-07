package dao;

import bean.AnswerBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class AnswerDAO {
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

    public static List<AnswerBean> getAnswersByQuizID(int quizId) throws SQLException {
        List<AnswerBean> ansList = new ArrayList<>();
        String sql = "SELECT [quiz_id]\n" +
                "      ,[answer_id]\n" +
                "      ,[answer_text]\n" +
                "      ,[is_correct]\n" +
                "      ,[created_at]\n" +
                "      ,[updated_at]\n" +
                "  FROM [OnlineQuiz].[dbo].[quiz_answer] where quiz_id = ?";

        pre = conn.prepareStatement(sql);
        pre.setInt(1, quizId);
        rs = pre.executeQuery();
        while (rs.next()) {
            int answerId = rs.getInt(2);
            String answerText = rs.getString(3);
            boolean isCorrect = rs.getBoolean(4);
            Timestamp createdAt = rs.getTimestamp(5);
            Timestamp updatedAt = rs.getTimestamp(5);
            AnswerBean answer = new AnswerBean(quizId, answerId, createdAt, updatedAt, isCorrect, answerText);
            ansList.add(answer);
        }
        return ansList;
    }
    public static List<Integer> correctAnswersForQuiz(int quizId) throws SQLException {
        List<Integer> list = new ArrayList<>();
        String sql = "select * from [OnlineQuiz].[dbo].[quiz_answer] where quiz_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, quizId);
        rs = pre.executeQuery();
        while (rs.next()) {
            if (rs.getBoolean(4)) {
                list.add(rs.getInt(2));
            }
        }
        System.out.println("correct answer from AnswerDAO: " + Arrays.toString(list.toArray()));
        return list;
    }
    public static int getTheLatestAnswerId() throws SQLException {
        String sql = "select [answer_id] from [OnlineQuiz].[dbo].[quiz_answer] order by answer_id desc";
        pre = conn.prepareStatement(sql);
        rs = pre.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }


    public static String getAnswer(int answerId) throws SQLException {
        String sql = "select answer_text from [OnlineQuiz].[dbo].[quiz_answer] where answer_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, answerId);
        rs = pre.executeQuery();
        if (rs.next()) {
            return rs.getString(1);
        } else {
            return "";
        }

    }
}
