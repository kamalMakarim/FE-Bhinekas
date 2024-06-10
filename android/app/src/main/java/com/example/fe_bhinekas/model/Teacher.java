package com.example.fe_bhinekas.model;

public class Teacher extends User{
    public Kelas teacher_class;
    public String teacher_id;

    public Teacher(String user_id, String username, String password, String display_name, Kelas teacher_class, String teacher_id) {
        super(user_id, username, password, display_name);
        this.teacher_class = teacher_class;
        this.teacher_id = teacher_id;
    }
    public Teacher(String user_id, String display_name, Kelas teacher_class, String teacher_id) {
        super(user_id, display_name);
        this.teacher_class = teacher_class;
        this.teacher_id = teacher_id;
    }

    @Override
    public String toString() {
        return (this.display_name + " - " + this.teacher_class.name);
    }
}
