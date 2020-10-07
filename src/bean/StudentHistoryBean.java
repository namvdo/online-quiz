package bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class StudentHistoryBean implements Serializable {
    String studentId;
    Timestamp takenTime;
    int clusterId;
    float score;

    public StudentHistoryBean() {
    }

    public StudentHistoryBean(String studentId, Timestamp takenTime, int clusterId, float score) {
        this.studentId = studentId;
        this.takenTime = takenTime;
        this.clusterId = clusterId;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Timestamp getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Timestamp takenTime) {
        this.takenTime = takenTime;
    }

    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
