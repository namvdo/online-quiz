package Utils;

import bean.AnswerBean;
import dao.AnswerDAO;
import dao.QuizAndAnswerDAO;
import dao.QuizDAO;

import java.security.MessageDigest;
import java.sql.SQLException;
import java.util.*;
import java.util.function.BiConsumer;

public class Utility {
    public static String applySha256(String input){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            /* Applies sha256 to our input */
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte elem: hash) {
                String hex = Integer.toHexString(0xff & elem);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString().substring(0, 50);
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @param questionsAndResponses - question id map to student answer(s)
     * @return - the final score
     */
    public static float calculateStudentScore(Map<Integer, List<Integer>> questionsAndResponses) throws SQLException {
        int count = 0;
        System.out.println("From calculateStudentScore: " + questionsAndResponses.toString());

        for(Map.Entry<Integer, List<Integer>> ques: questionsAndResponses.entrySet()) {
            List<Integer> correctAns = AnswerDAO.correctAnswersForQuiz(ques.getKey());
            correctAns.forEach(ans -> System.out.println("correct answer id: " + ans));
            int stdAnsSize = ques.getValue().size();
            System.out.println("student size: " + stdAnsSize + " correct size: " + correctAns.size());
            if (stdAnsSize > correctAns.size()) {
                continue;
            }
            // this is for getting a percent of the mark, not the whole one, for example, a quiz with 2 possible correct answers, then each correct answer gives you 0.5
            float percent = 1 / (float) correctAns.size();
            float mark = 0;

            for(int i = 0; i < stdAnsSize; i++) {
//                if (correctAns.contains(ques.getValue().get(i))) {
//                    System.out.println("mark: " + mark);
//                    mark += percent;
//                }
                for (Integer correctAn : correctAns) {
                    if (ques.getValue().get(i).equals(correctAn)) {
                        mark += percent;
                    }
                }
            }
            count += mark;
        }


        System.out.println("From calculateStudentScore: " + (count / (float) questionsAndResponses.size() * 10));
        return count / (float) questionsAndResponses.size() * 10;
    }



}
