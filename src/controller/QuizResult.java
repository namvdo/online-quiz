package controller;

import dao.QuizAndAnswerDAO;
import Utils.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class QuizResult extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Timestamp startingTime = (Timestamp) session.getAttribute("createdTime");
        // get ending time.
        Timestamp endingTime = new Timestamp(System.currentTimeMillis());
        String studentId = (String) request.getSession().getAttribute("user");
        Map<Integer, List<Integer>> studentAnswers = (HashMap<Integer, List<Integer>>) session.getAttribute("allAnsFromStudent");
        System.out.println("from quizresult: " + Arrays.toString(request.getParameterValues("answer")));
        studentAnswers.forEach((key, value) -> System.out.println("Key: " + key + " | Value: " + value));
        try {
            float score = Utility.calculateStudentScore(studentAnswers);
            System.out.println("From doPost QuizResult score: " + score);
            System.out.println("From doPost QuizResult: " + studentAnswers.toString());
            int latestClusterId = QuizAndAnswerDAO.getTheLastClusterId();
            System.out.println("From doPost QuizResult: " + latestClusterId);
            boolean i1 = QuizAndAnswerDAO.insertQuizCluster(studentId, latestClusterId + 1, startingTime, endingTime);
            boolean i2 = QuizAndAnswerDAO.insertClusterDetail(latestClusterId + 1, studentAnswers);
            boolean i3 = QuizAndAnswerDAO.insertStudentResult(score, studentId, latestClusterId + 1);
            System.out.println("from QuizResult: i1, i2, i3" + i1 + " " + i2 + " " + i3);
            session.setAttribute("score", score);
            request.getRequestDispatcher("/quizResult.jsp").forward(request, response);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}