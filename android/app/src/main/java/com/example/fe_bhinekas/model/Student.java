package com.example.fe_bhinekas.model;

import java.util.List;

public class Student {
    public String student_id;
    public String name;
    public Kelas student_class;
    public int batch;
    public Extracurricular[] extracurriculars;
    public boolean special_needs;

    public Student(String student_id, String name, Kelas student_class, int batch, Extracurricular[] extracurriculars, boolean special_needs) {
        this.student_id = student_id;
        this.name = name;
        this.student_class = student_class;
        this.batch = batch;
        this.extracurriculars = extracurriculars;
        this.special_needs = special_needs;
    }

    @Override
    public String toString() {
        return "" + name;
    }
}
