package com.example.fe_bhinekas.model;

public class User {
    public String user_id;
    private String username = "";
    public String display_name;

    public User(String user_id, String username, String password, String display_name) {
        this.user_id = user_id;
        this.username = username;
        this.display_name = display_name;
    }

    public User(String user_id, String display_name) {
        this.user_id = user_id;
        this.display_name = display_name;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", username='" + username + '\'' +
                ", display_name='" + display_name + '\'' +
                '}';
    }
}
