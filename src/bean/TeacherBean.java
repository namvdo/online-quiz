package bean;

public class TeacherBean {
    private String username,  email;
    public TeacherBean(String username, String email) {
        this.username = username;
        this.email = email;
    }
    public TeacherBean() {
        this.email = "";
        this.username = "";
    }

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
}