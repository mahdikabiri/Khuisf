package com.example.khuisf;

public class User {
    String username,password,name;
    int access;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public User(String username, String password, String name, int access) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.access = access;
    }
}
