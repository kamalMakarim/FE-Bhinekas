package com.example.fe_bhinekas.model;

import android.net.http.UrlRequest;

import java.sql.Timestamp;

public class Bill {
    public String id;
    public String student_id;
    public Integer amount;
    public String status;
    public Timestamp created_at;
    public Timestamp due_date;

}
