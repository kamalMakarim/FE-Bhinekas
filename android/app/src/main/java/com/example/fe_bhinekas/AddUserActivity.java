package com.example.fe_bhinekas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.User;
import com.example.fe_bhinekas.request.BaseApiService;
import com.example.fe_bhinekas.request.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddUserActivity extends AppCompatActivity {
    private Context mContext;
    private BaseApiService mApiService;
    private EditText editTextUsername, editTextPassword, editTextDisplayName;
    private Spinner spinnerRole;
    private Button buttonAddUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        mContext = this;
        mApiService = RetrofitClient.getClient().create(BaseApiService.class);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextDisplayName = findViewById(R.id.editTextDisplayName);
        spinnerRole = findViewById(R.id.spinnerRole);
        buttonAddUser = findViewById(R.id.buttonAddUser);

        buttonAddUser.setOnClickListener(v -> addUser());


    }

    private void addUser() {
        mApiService.addUser(editTextUsername.getText().toString(), editTextPassword.getText().toString(), editTextDisplayName.getText().toString(), spinnerRole.getSelectedItem().toString()).enqueue(new Callback<BaseResponse<User>>() {
            @Override
            public void onResponse(Call<BaseResponse<User>> call, Response<BaseResponse<User>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<User> body = response.body();
                    Toast.makeText(mContext, body.message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<User>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to add user", Toast.LENGTH_SHORT).show();
            }
        });
    }
}