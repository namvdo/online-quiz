package controller;

import bean.AnswerBean;
import bean.QuizBean;
import dao.AnswerDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

public class TakeQuiz extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<QuizBean> quizzes = (List<QuizBean>) session.getAttribute("quizzes");
        try {
            // current quiz id here means the index of the fetched quiz on the list.
            int currentQuizIdx = 0;
            if (session.getAttribute("currentQuizIdx") != null) {
                currentQuizIdx = (int) session.getAttribute("currentQuizIdx");
            }

            /* cache the answer of the current question */
            // map the quiz id to student answer
            Map<Integer, List<Integer>> allAnsFromStudent;
            // quiz id before clicking next, or previous, below changes its index.
            int currentQuizId = quizzes.get(currentQuizIdx).getQuizId();
            if (session.getAttribute("allAnsFromStudent") == null) {
                allAnsFromStudent = new HashMap<>();
            } else {
                allAnsFromStudent = (HashMap<Integer, List<Integer>>) session.getAttribute("allAnsFromStudent");
            }
            if (request.getParameterValues("answer") != null) {
                String[] answerIdFromStudent = request.getParameterValues("answer");
                // only student answered quiz will be added to the list;
                List<Integer> answers = new ArrayList<>();
                for(String answer: answerIdFromStudent) {
                    System.out.println("answer from TakeQuiz: " + answer);
                    answers.add(Integer.parseInt(answer));
                }
                System.out.println("from TakeQuiz: answer size" + answers.size());
                allAnsFromStudent.put(currentQuizId, answers);
            } else {
                List<Integer> unanswered = new ArrayList<>();
                unanswered.add(-1);
                allAnsFromStudent.put(currentQuizId, unanswered);
            }

            if (request.getParameter("submit") != null) {
                request.getRequestDispatcher("/QuizResult").forward(request, response);
            }

            if (quizzes.size() == allAnsFromStudent.size()) {
                session.setAttribute("allAnswered", true);
            }

            // get the the elapsed and redirect user if necessary

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            Timestamp createdTime = (Timestamp) session.getAttribute("createdTime");
            int totalTime = (int) session.getAttribute("totalTime");
            if (currentTime.getTime() - createdTime.getTime() > totalTime * 1000) {
                request.getRequestDispatcher("/QuizResult").forward(request, response);
            }          
            
            
            if (request.getParameter("next") != null) {
                currentQuizIdx = currentQuizIdx + 1;
            } else if (request.getParameter("prev") != null) {
                currentQuizIdx -= 1;
            }
            
            System.out.println("index: " + currentQuizIdx);
            // this means the actual index of the quiz on the db
            currentQuizId = quizzes.get(currentQuizIdx).getQuizId();
            List<AnswerBean> answersByQuizID = AnswerDAO.getAnswersByQuizID(currentQuizId);
            session.setAttribute("allAnsFromStudent", allAnsFromStudent);
            session.setAttribute("curAns", answersByQuizID);
            session.setAttribute("currentQuizIdx", currentQuizIdx);
            session.setAttribute("quiz", quizzes.get(currentQuizIdx));
        } catch (Exception e) {
            System.out.println("Some error here");
            e.printStackTrace();
        }
        request.getRequestDispatcher("/takeQuiz.jsp").forward(request, response);
    }

    /**
     *
     * @param request - the current request
     * @return true if all questions have been answered
     */
    private static boolean areAllQuestionsAnswered(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<QuizBean> quizzes = (ArrayList<QuizBean>) session.getAttribute("quizzes");
        Map<Integer, Integer> allAnsFromStudent = (HashMap) session.getAttribute("allAnsFromStudent");
        return quizzes.size() == allAnsFromStudent.size();
    }
}