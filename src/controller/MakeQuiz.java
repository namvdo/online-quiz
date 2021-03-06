package controller;

import bean.AnswerBean;
import bean.QuizBean;
import dao.AnswerDAO;
import dao.QuizAndAnswerDAO;
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




            // this is for cache the last submit from the user, for displaying purpose.
            request.setAttribute("quizDes", quizDesc);
            request.setAttribute("answer1", answers.get(0).get());
            request.setAttribute("answer2", answers.get(1).get());
            request.setAttribute("answer3", answers.get(2).get());
            request.setAttribute("answer4", answers.get(3).get());

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
            // check how many answer options are not null, if there are not 4 options provided then forward to the makeQuiz.jsp
            // and inform insufficient options provided.
            int optionsNonEmpty = 0;
            for(Optional<String> option: answers) {
                if (!option.get().isEmpty()) {
                    optionsNonEmpty++;
                }
            }
            if (optionsNonEmpty != 4) {
                request.setAttribute("noQuiz", optionsNonEmpty);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;
            }
            // checked answer options
            String[] options = request.getParameterValues("answer");

            if (options == null) {
                request.setAttribute("noCheckedAnswer", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;
            }

            String teacherId = (String) request.getSession().getAttribute("user");
            List<Integer> correctAns = new ArrayList<>();
            if (options.length == 4) {
                request.setAttribute("oversized", true);
                request.getRequestDispatcher("/makeQuiz.jsp").forward(request, response);
                return;

            }

            int latestQuizId = QuizDAO.getTheLatestQuizId();
            int latestAnswerId = AnswerDAO.getTheLatestAnswerId();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            List<AnswerBean> answerOptions = new ArrayList<>();
            QuizBean quizBean = new QuizBean();
            quizBean.setQuizId(latestQuizId + 1);
            quizBean.setQuizDescription(quizDesc);
            quizBean.setCreatedBy(teacherId);
            quizBean.setWeight((byte) 1);
            quizBean.setCreatedAt(now);

            // iterate through all correct checked answers
            for (String s : options) {
                request.setAttribute("option" + s, true);
                correctAns.add(Integer.parseInt(s));
                int answerIdx = Integer.parseInt(s);
                AnswerBean answerOption = new AnswerBean();
                answerOption.setQuizId(quizBean.getQuizId());
                answerOption.setAnswerText(answers.get(answerIdx).get());
                answerOption.setAnswerId(latestAnswerId + 1 + answerIdx);
                answerOption.setCreatedTime(now);
                answerOption.setCorrect(true);
                answerOptions.add(answerOption);
            }
            // this is for unchecked answer options
            for(int i = 0; i < 4; i++) {
                if (!correctAns.contains(i)) {
                    AnswerBean answerOption = new AnswerBean();
                    answerOption.setQuizId(quizBean.getQuizId());
                    answerOption.setAnswerText(answers.get(i).get());
                    answerOption.setAnswerId(latestAnswerId + 1 + i);
                    answerOption.setCreatedTime(now);
                    answerOption.setCorrect(false);
                    answerOptions.add(answerOption);
                }
            }


            boolean isInsertedSuccessfully = QuizAndAnswerDAO.insertQuizAndAnswers(quizBean, answerOptions);
            if (isInsertedSuccessfully) {
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
