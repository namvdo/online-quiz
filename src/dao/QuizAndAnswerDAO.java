package dao;

import bean.AnswerBean;
import bean.QuizBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class QuizAndAnswerDAO {
    static Connection conn;
    static PreparedStatement pre;
    static Statement statement;
    static ResultSet rs;
    // this is kidda terrible static block, the connection here should not be singleton, you should modify this.
    static  {
        try {
            conn = DBConnection.getInstance().getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param studentId - id of the student being saved
     * @param startingTime - initial time when starting taking the quizzes
     * @param endingTime - the finishing time when either a student hits the submit button or time is up.
     * @return true if the given information is successfully saved.
     */
    public static boolean insertQuizCluster(String studentId, Timestamp startingTime, Timestamp endingTime) throws SQLException {
        String sql = "insert into [OnlineQuiz].[dbo].[quiz_cluster](student_id, created_time, ended_time) values (?, convert(datetime,?,5), convert(datetime,?,5))";
        try {
            pre = conn.prepareStatement(sql);
            pre.setString(1, studentId);
            pre.setTimestamp(2, startingTime);
            pre.setTimestamp(3, endingTime);
            int result = pre.executeUpdate();
            System.out.println("from QuizAndAnswerDAO: " + result);
            return result == 1;
        } catch (SQLException exception) {
            return false;
        }
    }
    
    public static boolean insertStudentResult(float score, String studentId, int clusterId) {
        String sql = "insert into [OnlineQuiz].[dbo].[student_result](score, student_id, cluster_id) values (?, ?, ?)";
        try {
            pre = conn.prepareStatement(sql);
            pre.setFloat(1, score);
            pre.setString(2, studentId);
            pre.setInt(3, clusterId);
            return pre.executeUpdate() == 1;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return false;
    }

    public static int getTheLastClusterId() {
        String sql = "select top 1 cluster_id from [OnlineQuiz].[dbo].[quiz_cluster] order by cluster_id desc";
        try {
            pre = conn.prepareStatement(sql);
            rs = pre.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public static boolean insertQuizAndAnswers(QuizBean quiz, List<AnswerBean> answers) throws SQLException {
        System.out.println("quiz id: " + quiz.getQuizId());
        String insertQuizSql = "insert into [OnlineQuiz].[dbo].[quiz]" +
                "(quiz_id, quiz_description, created_by, weight, created_at) " +
                "values (?, ?, ?, ?, ?)";
        String insertAnswerSql = "insert into [OnlineQuiz].[dbo].[quiz_answer]" +
                "(quiz_id, answer_id, answer_text, is_correct, created_at) " +
                "values (?, ?, ?, ?, ?)";
        PreparedStatement insertAnswer = conn.prepareStatement(insertAnswerSql);
        try {
            conn.setAutoCommit(false);
            if (quiz == null || answers.size() != 4) {
                return false;
            }
            pre = conn.prepareStatement(insertQuizSql);
            pre.setInt(1, quiz.getQuizId());
            pre.setString(2, quiz.getQuizDescription());
            pre.setString(3, quiz.getCreatedBy());
            pre.setInt(4, 1);
            pre.setTimestamp(5, quiz.getCreatedAt());
            int quizRowCount = pre.executeUpdate();
            int answerRowCount = 0;
            for(int i = 0; i < answers.size(); i++) {

                insertAnswer.setInt(1, quiz.getQuizId());
                insertAnswer.setInt(2, answers.get(i).getAnswerId());
                insertAnswer.setString(3, answers.get(i).getAnswerText());
                insertAnswer.setBoolean(4, answers.get(i).isCorrect());
                insertAnswer.setTimestamp(5, answers.get(i).getCreatedTime());
                answerRowCount += insertAnswer.executeUpdate();
            }
            conn.commit();
            return quizRowCount == 1 && answerRowCount == 4;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (pre != null) {
                pre.close();
            } if (insertAnswer != null) {
                insertAnswer.close();
            }
        }
    }
    public static boolean deleteQuizAndAnswers(int quizId) throws SQLException {
        try {
            conn.setAutoCommit(false);
            String sql = "delete from [onlinequiz].[dbo].[quiz_answer] where quiz_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quizId);
            int deletedAnswerCount = pre.executeUpdate();
            sql = "delete from [onlinequiz].[dbo].[quiz] where quiz_id = ?";
            pre = conn.prepareStatement(sql);
            pre.setInt(1, quizId);
            int deletedQuizCount = pre.executeUpdate();
            conn.commit();
            return deletedAnswerCount != 0 && deletedQuizCount == 1;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

}
