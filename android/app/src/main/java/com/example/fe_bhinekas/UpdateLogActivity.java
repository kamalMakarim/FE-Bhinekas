package com.example.fe_bhinekas;

import static com.example.fe_bhinekas.LoginActivity.loggedTeacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.Log;
import com.example.fe_bhinekas.model.Teacher;
import com.example.fe_bhinekas.request.BaseApiService;
import com.example.fe_bhinekas.request.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateLogActivity extends AppCompatActivity {
    private EditText editTextMessage;
    private Switch switchSpecialNeeds;
    // Ensure there is a selected student and the student has a class object    private Button buttonSubmitLog;
    private BaseApiService mApiService;
    private Button buttonSubmitLog;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_log);
        this.mContext = this; // Initialize context
        this.mApiService = RetrofitClient.getClient().create(BaseApiService.class);

        this.editTextMessage = findViewById(R.id.editTextMessage);
        this.switchSpecialNeeds = findViewById(R.id.switchSpecialNeeds);
        this.buttonSubmitLog = findViewById(R.id.buttonSubmitLog);


        buttonSubmitLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Update log clicked");
                submitLog();
            }
        });
    }


    public void submitLog() {
        mApiService.updateLog(TeacherMainActivity.selectedLog.id, editTextMessage.getText().toString(), TeacherMainActivity.selectedLog.kelas.id ,switchSpecialNeeds.isChecked()).enqueue(new Callback<BaseResponse<Log>>() {
            @Override
            public void onResponse(Call<BaseResponse<Log>> call, Response<BaseResponse<Log>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<Log> logResponse = response.body();
                    Toast.makeText(mContext, "Log updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    System.out.println(response.raw());
                    Toast.makeText(mContext, "Failed to update log", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Log>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to update log", Toast.LENGTH_SHORT).show();
            }
        });
    }
}