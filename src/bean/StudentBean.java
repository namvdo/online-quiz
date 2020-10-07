package bean;

import java.io.Serializable;

public class StudentBean implements Serializable {
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String username, email;

    public StudentBean(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public StudentBean() {
        this.email = "";
        this.username = "";
    }
}
