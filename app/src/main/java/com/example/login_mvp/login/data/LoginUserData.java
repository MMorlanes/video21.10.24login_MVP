package com.example.login_mvp.login.data;

import com.example.login_mvp.beans.User;


public class LoginUserData {
    private String message;
    private User user;

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
