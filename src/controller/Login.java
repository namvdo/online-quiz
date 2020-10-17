package controller;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

import Utils.Utility;
import dao.*;

public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            boolean isTeacher = UserDAO.isUserATeacher(username);

            boolean isLoggedInSuccessfully = UserDAO.isLoggedInSuccessfully(username, Utility.applySha256(password).substring(0, 50), isTeacher);
            System.out.println("is teacher: " + isTeacher);
            if (isLoggedInSuccessfully) {
                Cookie cookie1 = new Cookie("username", username);
                Cookie cookie2 = new Cookie("password", password);
                cookie1.setMaxAge(60 * 60);
                cookie2.setMaxAge(60 * 60);
                response.addCookie(cookie1);
                response.addCookie(cookie2);

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
