package com.heroku.java.Model;

public class Admin {
    private int adminID;
    private String username;
    private String password;
    private String fullname;
    private String email;

    public Admin() {
    }

    public Admin(int adminID, String username, String password, String fullname, String email) {
        this.adminID = adminID;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getAdminID() {
        return adminID;
    }

    public void setAdminID(int adminID) {
        this.adminID = adminID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }   
}
