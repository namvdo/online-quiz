package controller;

import bean.QuizBean;
import dao.QuizDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ManageQuiz extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int noOfQuizzes = QuizDAO.getNumOfQuizzes();
            int currentPage = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
            int noOfPages = (int) Math.ceil(noOfQuizzes/(float) 5);
            System.out.println("no of quizzes: " + noOfQuizzes);
            System.out.println("no of pages: " + noOfPages);
            List<QuizBean> quizzes = QuizDAO.getQuizzesWithOffset(currentPage * 5 - 4, currentPage * 5);
            request.setAttribute("startIndex", currentPage * 5 - 4);
            System.out.println("quizzes size: " + quizzes.size());
            request.setAttribute("pages", noOfPages);
            request.setAttribute("quizzesWithOffset", quizzes);
            request.getRequestDispatcher("/manageQuiz.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
