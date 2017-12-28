package com.example.yinon.mycloset;

/**
 * Created by yinon on 12/12/17.
 */

public class User {
    private String username, password;
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
    public String getUsername() { return username; }
    public String getPassword() {
        return password;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean equals(User p) {
        if (this == p) return true;
        if (p == null) return false;
        return true;
    }
}