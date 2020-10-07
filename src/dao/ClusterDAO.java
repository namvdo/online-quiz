package dao;

import bean.ClusterDetailBean;
import bean.StudentHistoryBean;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClusterDAO {
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

    public static List<StudentHistoryBean> getQuizTakingHistory() throws SQLException {
        List<StudentHistoryBean> list = new ArrayList<>();
        String sql = "SELECT score, student_result.student_id, student_result.cluster_id, quiz_cluster.created_time \n" +
                "  FROM [OnlineQuiz].[dbo].[student_result] inner join quiz_cluster on student_result.cluster_id = quiz_cluster.cluster_id";
        pre = conn.prepareStatement(sql);
        rs = pre.executeQuery();
        while (rs.next()) {
           float score = rs.getInt(1);
           String studentId = rs.getString(2);
           int clusterId = rs.getInt(3);
           Timestamp takenTime = rs.getTimestamp(4);
           StudentHistoryBean std = new StudentHistoryBean(studentId, takenTime, clusterId, score);
            list.add(std);
        }
        return list;
    }


    public static List<ClusterDetailBean> getResultInDetail(int clusterId) throws SQLException {
        List<ClusterDetailBean> list = new ArrayList<>();
        String sql = "SELECT TOP (1000) [cluster_id], quiz.quiz_description,\n" +
                "        cluster_detail.quiz_id\n" +
                "      ,[student_choice_answer_id], answer_id\n" +
                "  FROM [OnlineQuiz].[dbo].[cluster_detail] inner join quiz_answer on " +
                "cluster_detail.quiz_id = quiz_answer.quiz_id inner join quiz on quiz.quiz_id = cluster_detail.quiz_id " +
                "where is_correct = 1 and cluster_detail.cluster_id = ?";
        pre = conn.prepareStatement(sql);
        pre.setInt(1, clusterId);
        rs = pre.executeQuery();

        while(rs.next()) {
            float score;
            String quizDes = rs.getString(2);
            String correctAns = AnswerDAO.getAnswer(rs.getInt(5));
            String studentAns = AnswerDAO.getAnswer(rs.getInt(4));
            if (correctAns.equals(studentAns)) {
                score = 1;
            } else {
                score = 0;
            }
            ClusterDetailBean bean = new ClusterDetailBean(quizDes, studentAns, correctAns, score);
            System.out.println("get resultdetail: " + bean.toString());
            list.add(bean);
        }
        return list;
    }
}
