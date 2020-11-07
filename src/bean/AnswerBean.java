package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class AnswerBean implements Serializable {
    int quizId, answerId;
    Timestamp createdTime;
    Timestamp updatedTime;
    boolean isCorrect;
    String answerText;

    public AnswerBean() {
    }

    public AnswerBean(int quizId, int answerId, Timestamp createdTime, Timestamp updatedTime, boolean isCorrect, String answerText) {
        this.quizId = quizId;
        this.answerId = answerId;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.isCorrect = isCorrect;
        this.answerText = answerText;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public Timestamp getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Timestamp createdTime) {
        this.createdTime = createdTime;
    }

    public Timestamp getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Timestamp updatedTime) {
        this.updatedTime = updatedTime;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    @Override
    public String toString() {
        return "AnswerBean{" +
                "quizId=" + quizId +
                ", answerId=" + answerId +
                ", createdTime=" + createdTime +
                ", updatedTime=" + updatedTime +
                ", isCorrect=" + isCorrect +
                ", answerText='" + answerText + '\'' +
                '}';
    }
}
