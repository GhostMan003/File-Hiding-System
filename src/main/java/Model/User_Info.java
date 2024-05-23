package Model;

public class User_Info {
    private String userName;
    private String userEmail;

    public User_Info(String name, String email) {
        this.userName = name;
        this.userEmail = email;
    }

    public User_Info(String email) {
        this.userEmail = email;
    }

    public String getName() {
        return userName;
    }

    public void setName(String name) {
        userName = name;
    }

    public String getEmail() {
        return userEmail;
    }

    public void setEmail(String email) {
        userEmail = email;
    }
}
