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
import java.sql.Timestamp;
import java.util.*;

public class TakeQuiz extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            List<QuizBean> quizzes = (List<QuizBean>) session.getAttribute("quizzes");
            // current quiz id here means the index of the fetched quiz on the list.
            int currentQuizIdx = 0;
            if (session.getAttribute("currentQuizIdx") != null) {
                currentQuizIdx = (int) session.getAttribute("currentQuizIdx");
            }

            /* cache the answer of the current question */
            // map the quiz id to student answer
            Map<Integer, List<Integer>> allAnsFromStudent;
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
                    answers.add(Integer.parseInt(answer));
                }
                allAnsFromStudent.put(currentQuizId, answers);
            } else {
                List<Integer> unanswered = new ArrayList<>();
                unanswered.add(-1);
                allAnsFromStudent.put(currentQuizId, unanswered);
            }



            if (quizzes.size() == allAnsFromStudent.size()) {
                session.setAttribute("allAnswered", true);
            }

            /* ------------------------------------------------- */
            if (request.getParameter("next") != null) {
                currentQuizIdx = currentQuizIdx + 1;
            } else if (request.getParameter("prev") != null) {
                currentQuizIdx -= 1;
            }


            // get the the elapsed and redirect user if necessary

            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
            Timestamp createdTime = (Timestamp) session.getAttribute("createdTime");
            int totalTime = (int) session.getAttribute("totalTime");


            System.out.println("index: " + currentQuizIdx);
            // this means the actual index of the quiz on the db
            currentQuizId = quizzes.get(currentQuizIdx).getQuizId();
            List<AnswerBean> answersByQuizID = AnswerDAO.getAnswersByQuizID(currentQuizId);
            session.setAttribute("allAnsFromStudent", allAnsFromStudent);
            session.setAttribute("curAns", answersByQuizID);
            session.setAttribute("currentQuizIdx", currentQuizIdx);
            session.setAttribute("quiz", quizzes.get(currentQuizIdx));
            System.out.println("current answers: " + Arrays.toString(request.getParameterValues("answer")));
            // if the user presses the submit button.
            if (request.getParameter("submit") != null) {
                finishTheQuiz(request, response);
            } else if (currentTime.getTime() >= totalTime * 1000 + createdTime.getTime()) {
                finishTheQuiz(request, response);
            }
            // if the time is up, then cache the last answer(s) for the current quiz, because there is "nothing" submitted
            // then we cannot cache the last answer(s) because of this, we need to add query strings by javascript on countDown.js, and here we get the quiz_id
            // and corresponding answer(s) to add it to the map.
            else if(request.getParameter("end") != null && "true".equals(request.getParameter("end"))){
                int quizId = Integer.parseInt(request.getParameter("quizid"));
                    List<Integer> answers = new ArrayList<>();
                    Map<String, String[]> answerIds = request.getParameterMap();
                    for(Map.Entry<String, String[]> entry: answerIds.entrySet()) {
                        System.out.println("param: " + entry.getKey() + " values: " + Arrays.toString(entry.getValue()));
                        if (entry.getKey().contains("answer")) {
                            answers.add(Integer.parseInt(entry.getValue()[0]));
                        }
                    }
                    allAnsFromStudent.put(quizId, answers);
                    System.out.println("on takequiz: " + allAnsFromStudent.toString());
                    session.setAttribute("allAnsFromStudent", allAnsFromStudent);
                finishTheQuiz(request, response);

            }else {
                request.getRequestDispatcher("/takeQuiz.jsp").forward(request, response);
            }

       } catch (Exception e) {
            System.out.println("Some error here");
            e.printStackTrace();
        }
    }

    /**
     * This method is triggered when either time is up or user clicks the submit button. 
     * @param request - current request to work with
     * @param response - current response
     * @throws ServletException
     * @throws IOException
     */
    private static void finishTheQuiz(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();
            // get the current quiz index, and set all next quizzes after this quiz unanswered by
            // setting values for each of them with -1.
            int currentQuizIdx = (int) session.getAttribute("currentQuizIdx");
            List<QuizBean> quizzes = (List) session.getAttribute("quizzes");
            Map<Integer, List<Integer>> stdWithResponse = (Map) session.getAttribute("allAnsFromStudent");
            for (int unansweredQuizIdx = currentQuizIdx + 1; unansweredQuizIdx < quizzes.size(); unansweredQuizIdx++) {
                    int unansweredQuizId = quizzes.get(unansweredQuizIdx).getQuizId();
                    List<Integer> unanswered = new ArrayList<>();
                    unanswered.add(-1);
                    stdWithResponse.put(unansweredQuizId, unanswered);
            }
            request.getRequestDispatcher("/QuizResult").forward(request, response);
    }

}
