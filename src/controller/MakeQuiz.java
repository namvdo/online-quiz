package controller;

import dao.AnswerDAO;
import dao.QuizDAO;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MakeQuiz extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String quizDesc = request.getParameter("quizDes");
            String opt1 = request.getParameter("opt1");
            String opt2 = request.getParameter("opt2");
            String opt3 = request.getParameter("opt3");
            String opt4 = request.getParameter("opt4");
            List<Optional<String>> answers = new ArrayList<>();
            Optional<String> o1 = Optional.ofNullable(opt1);
            Optional<String> o2 = Optional.ofNullable(opt2);
            Optional<String> o3 = Optional.ofNullable(opt3);
            Optional<String> o4 = Optional.ofNullable(opt4);
            answers.add(o1);
            answers.add(o2);
            answers.add(o3);
            answers.add(o4);


            // checked answer options
            String[] options = request.getParameterValues("answer");

            // add cookie to the browser to remember the last action from the user
            request.setAttribute("quizDes", quizDesc);
            request.setAttribute("answer1", answers.get(0).get());
            request.setAttribute("answer2", answers.get(1).get());
            request.setAttribute("answer3", answers.get(2).get());
            request.setAttribute("answer4", answers.get(3).get());
            // iterate through all checkboxes
            for(int i = 0; i < options.length; i++) {
                request.setAttribute("option" + i, i);
            }
            // check if the quiz description is null, then forward the request
            if (quizDesc.isEmpty()) {
                request.setAttribute("emptyQuizDes", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;
            }

            boolean isAllEmpty = true;
            for(Optional<String> option: answers) {
                isAllEmpty = (option.get().isEmpty() && isAllEmpty);
            }
            if (isAllEmpty) {
                request.setAttribute("allEmpty", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;
            }
            // check how many answer options are not null, if there is only 1 then forward to makeQuiz.jsp and alert fail to insert
            int optionsNonEmpty = 0;
            for(Optional<String> option: answers) {
                if (!option.get().isEmpty()) {
                    optionsNonEmpty++;
                }
            }
            if (optionsNonEmpty < 2) {
                request.setAttribute("noQuiz", optionsNonEmpty);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;
            }
            String teacherId = (String) request.getSession().getAttribute("user");
            List<Integer> correctAns = new ArrayList<>();
            List<Integer> incorrectAns = new ArrayList<>();
            if (options.length == 4) {
                request.setAttribute("oversized", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;

            }
            for(int i = 0; i < options.length; i++) {
                correctAns.add(Integer.parseInt(options[i]));
            }
            for(int i = 0; i < 4; i++) {
                if (!correctAns.contains(i)) {
                    incorrectAns.add(i);
                }
            }
            int latestQuizId = QuizDAO.getTheLatestQuizId();
            int latestAnswerId = AnswerDAO.getTheLatestAnswerId();
            Timestamp now = new Timestamp(System.currentTimeMillis());


            boolean isAnswerInsertedSuccessfully = true;
            boolean isQuizInsertedSuccessfully = QuizDAO.insertNewQuiz(latestQuizId + 1, quizDesc, teacherId, 1, now);
            // insert the correct answer options to the db

            for (Integer correctAn : correctAns) {
                isAnswerInsertedSuccessfully = isAnswerInsertedSuccessfully && AnswerDAO.insertNewAnswer(latestQuizId + 1, latestAnswerId + correctAn + 1, answers.get(correctAn).get(), true, now);
            }

            // insert incorrect answers to the database
            for (Integer incorrectAn : incorrectAns) {
                isAnswerInsertedSuccessfully = isAnswerInsertedSuccessfully && AnswerDAO.insertNewAnswer(latestQuizId + 1, latestAnswerId + incorrectAn + 1, answers.get(incorrectAn).get(), false, now);
            }

            if (isAnswerInsertedSuccessfully && isQuizInsertedSuccessfully) {
                request.setAttribute("inserted", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            request.setAttribute("inserted", false);
            request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
        }

    }
}
