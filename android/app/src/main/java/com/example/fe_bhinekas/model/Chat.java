package com.example.fe_bhinekas.model;

import java.sql.Timestamp;

public class Chat {
    public String id;
    public String message;
    public Timestamp created_at;
    public User sender;
    public String student_id;
    public Log log;
    public boolean read;

    public Chat(String id, String message, Timestamp created_at, User sender, String student_id, Log log, boolean read) {
        this.id = id;
        this.message = message;
        this.created_at = created_at;
        this.sender= sender;
        this.student_id = student_id;
        this.log = log;
        this.read = read;
    }
}
