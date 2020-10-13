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
        studentAnswers.forEach((key, value) -> System.out.println("Key: " + key + " | Value: " + value));
        try {
            float score = Utility.calculateStudentScore(studentAnswers);
            boolean i1 = QuizAndAnswerDAO.insertQuizCluster(studentId, startingTime, endingTime);
            System.out.println("i1: " + i1);
            int latestClusterId = QuizAndAnswerDAO.getTheLastClusterId();
            boolean i2 = QuizAndAnswerDAO.insertClusterDetail(latestClusterId, studentAnswers);
            boolean i3 = QuizAndAnswerDAO.insertStudentResult(score, studentId, latestClusterId);
            System.out.println("studentId; " + studentId  + " lastestClsuter: " + latestClusterId);
            session.setAttribute("score", score);
            session.removeAttribute("allAnsFromStudent");
            session.removeAttribute("curAns");
            session.removeAttribute("currentQuizIdx");
            session.removeAttribute("quiz");
            session.removeAttribute("quizzes");
            session.removeAttribute("createdTime");
            session.removeAttribute("totalTime");
            request.getRequestDispatcher("/quizResult.jsp").forward(request, response);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
