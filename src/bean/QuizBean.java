package bean;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author macbook
 */
public class QuizBean implements Serializable {
    private int quizId;
    private byte weight;
    private String quizDescription, createdBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // new property to determine whether this quiz has been answered by student, if it's
    // then we cannot delete this quiz

    private boolean answered;

    public QuizBean() {

    }

    public QuizBean(int quizId, byte weight, String quizDescription, String createdBy, Timestamp createdAt, Timestamp updatedAt) {
        this.quizId = quizId;
        this.weight = weight;
        this.quizDescription = quizDescription;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.answered = false;
    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public byte getWeight() {
        return weight;
    }

    public void setWeight(byte weight) {
        this.weight = weight;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "QuizBean{" +
                "quizId=" + quizId +
                ", weight=" + weight +
                ", quizDescription='" + quizDescription + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
