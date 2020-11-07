package controller;

import dao.QuizAndAnswerDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteQuiz extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int quizId = Integer.parseInt(request.getParameter("quizId"));
            if (QuizAndAnswerDAO.deleteQuizAndAnswers(quizId)) {
               request.setAttribute("successfullyDeleted", true) ;
               request.getRequestDispatcher("/ManageQuiz").forward(request, response);
            }
        } catch (SQLException | NumberFormatException throwables) {
            throwables.printStackTrace();
            request.setAttribute("successfullyDeleted", false) ;
            request.getRequestDispatcher("/ManageQuiz").forward(request, response);
        }
    }
}
