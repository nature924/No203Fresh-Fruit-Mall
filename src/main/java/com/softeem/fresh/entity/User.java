package com.softeem.fresh.entity;

public class User {
    private String id;

    private String username;

    private String password;

    private Integer dr;

    private String email;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String id, String username, String password, Integer dr, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.dr = dr;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getDr() {
        return dr;
    }

    public void setDr(Integer dr) {
        this.dr = dr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }
}