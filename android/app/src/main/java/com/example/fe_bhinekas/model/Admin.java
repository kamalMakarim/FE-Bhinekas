package com.example.fe_bhinekas.model;

public class Admin extends User{
    public String admin_id;
    public Admin(String user_id, String username, String password, String display_name, String admin_id) {
        super(user_id, username, password, display_name);
        this.admin_id = admin_id;
    }
}
