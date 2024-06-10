package com.example.fe_bhinekas.model;

import java.util.List;

public class Parent extends User{
    public String parent_id;
    public List<Student> students;

    public Parent(String user_id, String username, String password, String display_name, String parent_id, List<Student> students) {
        super(user_id, username, password, display_name);
        this.parent_id = parent_id;
        this.students = students;
    }
    public Parent(String user_id, String display_name, String parent_id, List<Student> students) {
        super(user_id, display_name);
        this.parent_id = parent_id;
        this.students = students;
    }
}
