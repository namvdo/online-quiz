package dao;

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
     * @param clusterId - id of the cluster, a cluster here means a number of quizzes student has taken for a given try
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
    public static boolean insertClusterDetail(int clusterId, Map<Integer, List<Integer>> studentAnswers) throws SQLException {
        String sql = "insert into [OnlineQuiz].[dbo].[cluster_detail](cluster_id, quiz_id, student_choice_answer_id) values (?, ?, ?);";
        pre = conn.prepareStatement(sql);
        conn.setAutoCommit(false);
        for(Map.Entry<Integer, List<Integer>> entry: studentAnswers.entrySet()) {
           List<Integer> studentAns = entry.getValue();
            for(int i: studentAns) {
                pre.setInt(1, clusterId);
                pre.setInt(2, entry.getKey());
                pre.setInt(3, i);
                pre.addBatch();
            }
        }
        int[] updateCounts = pre.executeBatch();
        conn.commit();
        conn.setAutoCommit(true);
        return updateCounts.length > 0;

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

    public static Map<Integer, List<Integer>> getQuizzesAndCorrectAnsFirst(List<QuizBean> quizIds) throws SQLException {
        Map<Integer, List<Integer>> quizToCorrectAns = new HashMap<>();
        for (QuizBean quizId : quizIds) {
            List<Integer> correctAns = AnswerDAO.correctAnswersForQuiz(quizId.getQuizId());
            quizToCorrectAns.put(quizId.getQuizId(), correctAns);
        }
        return quizToCorrectAns;
    }

}
