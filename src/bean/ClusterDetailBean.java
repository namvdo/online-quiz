package bean;

import java.io.Serializable;

public class ClusterDetailBean implements Serializable {
    private String quizDes, studentAns, correctAns;
    private float score;

    public ClusterDetailBean(String quizDes, String studentAns, String correctAns, float score) {
        this.quizDes = quizDes;
        this.studentAns = studentAns;
        this.correctAns = correctAns;
        this.score = score;
    }

    public String getQuizDes() {
        return quizDes;
    }

    public void setQuizDes(String quizDes) {
        this.quizDes = quizDes;
    }

    public String getStudentAns() {
        return studentAns;
    }

    public void setStudentAns(String studentAns) {
        this.studentAns = studentAns;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
