package com.example.fe_bhinekas.model.response;

import com.example.fe_bhinekas.model.Kelas;
import com.example.fe_bhinekas.model.Student;

public class LoginReponse {
    public String user_id;
    public String username = "";
    public String password = "";
    public String display_name;
    public Kelas teacher_class;
    public String teacher_id;
    public String parent_id;
    public String admin_id;
    public Student[] students;

    public LoginReponse(String user_id, String username, String password, String display_name, Kelas teacher_class, String teacher_id, String parent_id, String admin_id ,Student[] students) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.display_name = display_name;
        this.teacher_class = teacher_class;
        this.teacher_id = teacher_id;
        this.parent_id = parent_id;
        this.admin_id = admin_id;
        this.students = students;
    }

}
