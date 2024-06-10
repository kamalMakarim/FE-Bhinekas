package com.example.fe_bhinekas.model;

import java.sql.Timestamp;

public class Log {
    public String id;
    public String message;
    public Timestamp created_at;
    public Kelas kelas;
    public User writer;
    public boolean for_special_kids;

    public Log(String id, String message, Timestamp created_at, Kelas kelas, boolean for_special_kids, User writer) {
        this.id = id;
        this.message = message;
        this.created_at = created_at;
        this.kelas = kelas;
        this.for_special_kids = for_special_kids;
        this.writer = writer;
    }
}
