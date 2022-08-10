package com.example.multidisplin;

public class ModelUser {
    int id;
    String username;
    String email;
    String password;
    boolean status;

    public ModelUser(String username, String email, String password, boolean status) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", pasword='" + password + '\'' +
                ", status=" + status +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPasword() {
        return password;
    }

    public void setPasword(String pasword) {
        this.password = pasword;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
