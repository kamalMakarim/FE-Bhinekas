package com.example.fe_bhinekas.model;

public class Extracurricular {
    public String id;
    public String name;
    public int price;

    public Extracurricular(String id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return name;
    }
}
