package com.example.fe_bhinekas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fe_bhinekas.model.Admin;
import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.Parent;
import com.example.fe_bhinekas.model.Student;
import com.example.fe_bhinekas.model.Teacher;
import com.example.fe_bhinekas.model.User;
import com.example.fe_bhinekas.model.response.LoginReponse;
import com.example.fe_bhinekas.model.response.LoginRequest;
import com.example.fe_bhinekas.request.BaseApiService;
import com.example.fe_bhinekas.request.UtilsApi;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static Parent loggedParent;
    public static User loggedUser;
    public static Teacher loggedTeacher;
    public static Admin loggedAdmin;
    private Button loginButton = null;
    private EditText username, password;
    private BaseApiService mApiService;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginButton = findViewById(R.id.login_button);
        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
        mApiService = UtilsApi.getApiService();
        mContext = this;
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        if(getActionBar() != null) {
            getActionBar().hide();
        }

        loginButton.setOnClickListener(v -> handleLogin());

    }

    private void handleLogin() {
        String usernameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        LoginRequest loginRequest = new LoginRequest(usernameStr, passwordStr);
        if(usernameStr.isEmpty() || passwordStr.isEmpty()){
            Toast.makeText(mContext, "Username and password must be filled", Toast.LENGTH_SHORT).show();
            return;
        }
        mApiService.login(loginRequest).enqueue(new Callback<BaseResponse<LoginReponse>>() {
            @Override
            public void onResponse(Call<BaseResponse<LoginReponse>> call, Response<BaseResponse<LoginReponse>> response) {
                BaseResponse<LoginReponse> body = response.body();
                if(body != null){
                    if(body.message.equals("Login success")){
                        if(body.payload.parent_id != null){
                            loggedAdmin = null;
                            loggedTeacher = null;
                            loggedParent = new Parent(body.payload.user_id, body.payload.username, body.payload.password, body.payload.display_name, body.payload.parent_id, Arrays.asList(body.payload.students));
                            loggedUser = new User(body.payload.user_id, body.payload.username, body.payload.password, body.payload.display_name);
                            Toast.makeText(mContext, "Login success", Toast.LENGTH_SHORT).show();
                            moveActivity(mContext, ParentMainActivity.class);
                        } else if (body.payload.teacher_id != null){
                            loggedAdmin = null;
                            loggedParent = null;
                            loggedTeacher = new Teacher(body.payload.user_id, body.payload.username, body.payload.password, body.payload.display_name, body.payload.teacher_class, body.payload.teacher_id);
                            loggedUser = new User(body.payload.user_id, body.payload.username, body.payload.password, body.payload.display_name);
                            Toast.makeText(mContext, "Login success", Toast.LENGTH_SHORT).show();
                            System.out.println(loggedTeacher);
                            moveActivity(mContext, TeacherMainActivity.class);
                        } else if(body.payload.admin_id != null){
                            loggedParent = null;
                            loggedTeacher = null;
                            loggedAdmin = new Admin(body.payload.user_id, body.payload.username, body.payload.password, body.payload.display_name, body.payload.admin_id);
                            loggedUser = new User(body.payload.user_id, body.payload.username, body.payload.password, body.payload.display_name);
                            Toast.makeText(mContext, "Login success", Toast.LENGTH_SHORT).show();
                            moveActivity(mContext, AdminMainActivity.class);
                        }else {
                            Toast.makeText(mContext, "User has no role", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, body.message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Invalid password or username", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<LoginReponse>> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(mContext, "Client error" , Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}