package controller;

import dao.AnswerDAO;
import dao.QuizDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MakeQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String quizDesc = request.getParameter("quizDes");
        String opt1 = request.getParameter("opt1");
        String opt2 = request.getParameter("opt2");
        String opt3 = request.getParameter("opt3");
        String opt4 = request.getParameter("opt4");
        List<String> answers = new ArrayList<>();
        answers.add(opt1);
        answers.add(opt2);
        answers.add(opt3);
        answers.add(opt4);
        String teacherId = (String) request.getSession().getAttribute("user");
        byte correctAns = Byte.parseByte(request.getParameter("answer"));

        try {
            int latestQuizId = QuizDAO.getTheLatestQuizId();
            int latestAnswerId = AnswerDAO.getTheLatestAnswerId();
            Timestamp now = new Timestamp(System.currentTimeMillis());

            
            boolean isAnswerInsertedSuccessfully = false;
            boolean isQuizInsertedSuccessfully = QuizDAO.insertNewQuiz(latestQuizId + 1, quizDesc, teacherId, 1, now);
            for(int i = 0; i < 4; i++) {
                if (correctAns == i) {
                    isAnswerInsertedSuccessfully = AnswerDAO.insertNewAnswer(latestQuizId + 1, latestAnswerId + 1 + i, answers.get(i), true, now);
                } else {
                    isAnswerInsertedSuccessfully = AnswerDAO.insertNewAnswer(latestQuizId + 1, latestAnswerId + 1 + i, answers.get(i), false, now);
                }
                if (!isAnswerInsertedSuccessfully) {
                    break;
                }
            }
            if (isAnswerInsertedSuccessfully && isQuizInsertedSuccessfully) {
                request.setAttribute("inserted", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
