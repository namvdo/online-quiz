package controller;

import dao.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import Utils.*;
public class Register extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            boolean isTeacher = request.getParameter("option").equals("teacher");
            boolean isRegisteredSuccessfully = UserDAO.registerUser(username, Utility.applySha256(password), email, isTeacher);
            System.out.println("register: " + isRegisteredSuccessfully);
            request.setAttribute("successfullyRegister", isRegisteredSuccessfully);
            request.getRequestDispatcher("/afterRegistration.jsp").forward(request, response);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            request.setAttribute("successfullyRegister", false);
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
