package controller;

import bean.QuizBean;
import dao.QuizDAO;
import dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class QuizInitializer extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int quizNums = Integer.parseInt(request.getParameter("numOfQuizzes"));
            HttpSession session = request.getSession();
            List<QuizBean> quizzes = QuizDAO.getQuizzesRandomly(quizNums);
            int allQuizzes = (int) session.getAttribute("allQuizzes");
            if (quizNums > allQuizzes) {
                request.setAttribute("invalidInput", true);
                request.getRequestDispatcher("./index.jsp").forward(request, response);
                return;
            }
            String username = (String) request.getSession().getAttribute("user");
            if (UserDAO.isUserATeacher(username)) {
                request.setAttribute("teacher", true);
                request.getRequestDispatcher("./index.jsp").forward(request, response);
                return;
            }
            session.setAttribute("quizzes", quizzes);
            Timestamp createdTime = new Timestamp(System.currentTimeMillis());
            session.setAttribute("createdTime", createdTime);
            // for testing purpose, I set the time for answering quizzes is for 1 minute.
            session.setAttribute("totalTime", 10);
            request.getRequestDispatcher("/TakeQuiz").forward(request, response);
        } catch (Exception throwable) {
            throwable.printStackTrace();
            System.out.println("Some errors here on QuizInitializer, maybe cannot parse the string or some kind.");
            request.setAttribute("invalidInput", true);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
