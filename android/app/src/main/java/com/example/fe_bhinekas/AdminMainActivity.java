package com.example.fe_bhinekas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AdminMainActivity extends AppCompatActivity {
    private Button btnAddUser, btnManageStudents, btnManageTeachers, btnManageParents;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);

        mContext = this;
        btnAddUser = findViewById(R.id.btnAddUser);
        btnManageStudents = findViewById(R.id.btnManageStudents);
        btnManageTeachers = findViewById(R.id.btnManageTeachers);
        btnManageParents = findViewById(R.id.btnManageParents);

        setupListener();
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }

    private void setupListener() {
        btnAddUser.setOnClickListener(v -> moveActivity(mContext, AddUserActivity.class));
        btnManageStudents.setOnClickListener(v -> moveActivity(mContext, ManageStudentsActivity.class));
        btnManageTeachers.setOnClickListener(v -> moveActivity(mContext, ManageTeachersActivity.class));
        btnManageParents.setOnClickListener(v -> moveActivity(mContext, ManageParentsActivity.class));
    }
}