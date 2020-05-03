package com.example.android.myapplication;

import android.net.Uri;

public class User {



    String name;
    String email;
    String  password;
    String avatar;

    public User() {
    }


    public User(String avatar,String email, String name,String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String  getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }



}

