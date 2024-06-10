package com.example.fe_bhinekas;

import static com.example.fe_bhinekas.LoginActivity.loggedParent;
import static com.example.fe_bhinekas.LoginActivity.loggedTeacher;
import static com.example.fe_bhinekas.LoginActivity.loggedUser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.Chat;
import com.example.fe_bhinekas.model.Log;
import com.example.fe_bhinekas.model.Parent;
import com.example.fe_bhinekas.model.Student;
import com.example.fe_bhinekas.request.BaseApiService;
import com.example.fe_bhinekas.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogDetailActivity extends AppCompatActivity {
    private LinearLayout logDetailContainer;
    private TextView logMessage, logDate, logKelas;
    private BaseApiService mApiService;
    private Context mContext;
    private RecyclerView chatLogRecyclerView;
    private ImageButton sendButton;
    private EditText chatInput;
    private ChatAdapter chatAdapter;
    private Student selectedStudent;
    private Log selectedLog;
    private List<Chat> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_detail);
        logDetailContainer = findViewById(R.id.logDetailsContainer);
        logMessage = findViewById(R.id.logMessage);
        logDate = findViewById(R.id.logDate);
        logKelas = findViewById(R.id.logKelas);
        sendButton = findViewById(R.id.sendButton);
        chatLogRecyclerView = findViewById(R.id.chatLogRecyclerView);
        chatInput = findViewById(R.id.chatInput);

        mApiService = UtilsApi.getApiService();
        mContext = this;

        // Load data
        loadLogDetails();
        getChatLogs();

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        if(getActionBar() != null) {
            getActionBar().hide();
        }

        // Initialize RecyclerView
        chatLogRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatAdapter = new ChatAdapter(chatList, this);
        chatLogRecyclerView.setAdapter(chatAdapter);

        new android.os.Handler().postDelayed(this::getChatLogs, 60000);


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = chatInput.getText().toString();
                if(!message.isEmpty()){
                    sendChat(message);
                    chatInput.setText("");
                }
            }
        });
    }

    private void loadLogDetails() {
        if(loggedParent != null){
            selectedLog = ParentMainActivity.selectedLog;
        } else if (loggedTeacher != null) {
            selectedLog = TeacherMainActivity.selectedLog;
        } else {
            finish();
        }
        if (selectedLog != null) {
            logMessage.setText(selectedLog.message);
            logDate.setText(selectedLog.created_at.toString()); // Format date as needed
            logKelas.setText(selectedLog.kelas.name);
        }
    }

    private void getChatLogs() {
        boolean for_special_kids;
        if(loggedParent != null){
            this.selectedStudent = ParentMainActivity.selectedStudent;
        } if(loggedTeacher != null){
            this.selectedStudent = TeacherMainActivity.selectedStudent;
        } else {
            for_special_kids = false;
        }
        mApiService.getChatLogs(selectedStudent.student_id, selectedLog.id, loggedUser.user_id).enqueue(new Callback<BaseResponse<List<Chat>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Chat>>> call, Response<BaseResponse<List<Chat>>> response) {
                if(response.isSuccessful()){
                    BaseResponse<List<Chat>> body = response.body();
                    if(body != null && body.payload != null){
                        chatList.clear();
                        chatList.addAll(body.payload);
                        chatList.sort((o1, o2) -> o1.created_at.compareTo(o2.created_at));
                        chatAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to get chat logs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Chat>>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to get chat logs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendChat(String message) {
        mApiService.postChatLog(loggedUser.user_id, selectedStudent.student_id, message, selectedLog.id).enqueue(new Callback<BaseResponse<Chat>>() {
            @Override
            public void onResponse(Call<BaseResponse<Chat>> call, Response<BaseResponse<Chat>> response) {
                if(response.isSuccessful()){
                    BaseResponse<Chat> body = response.body();
                    if(body != null && body.payload != null){
                        chatList.add(body.payload);
                        chatAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to send chat", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Chat>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to send chat", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
