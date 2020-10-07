package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

import Utils.Utility;
import dao.*;

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            boolean isTeacher = UserDAO.isUserATeacher(username);
            boolean isLoggedInSuccessfully = UserDAO.isLoggedInSuccessfully(username, Utility.applySha256(password).substring(0, 50), isTeacher);
            System.out.println("is teacher: " + isTeacher);
            if (isLoggedInSuccessfully) {
                int noOfQuizzes = QuizDAO.getNumOfQuizzes();
                HttpSession session = request.getSession();
                System.out.println(username);
                session.setAttribute("isTeacher", isTeacher);
                session.setAttribute("allQuizzes", noOfQuizzes);
                session.setAttribute("user", username);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            } else {
                request.setAttribute("loginFailed", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
