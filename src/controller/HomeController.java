package controller;

import dao.QuizDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class HomeController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            try {
                int noOfQuizzes = QuizDAO.getNumOfQuizzes();
                session.setAttribute("noOfQuiz", noOfQuizzes);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
