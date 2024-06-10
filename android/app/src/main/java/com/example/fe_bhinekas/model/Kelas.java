package com.example.fe_bhinekas.model;

public class Kelas {
    public String id;
    public String name;
    public int year;

    public Kelas(String id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;
    }

    @Override
    public String toString() {
        return name;
    }
}
