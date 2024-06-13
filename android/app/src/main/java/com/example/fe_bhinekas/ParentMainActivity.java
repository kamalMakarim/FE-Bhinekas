package com.example.fe_bhinekas;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.Log;
import com.example.fe_bhinekas.model.Student;
import com.example.fe_bhinekas.request.BaseApiService;
import com.example.fe_bhinekas.request.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentMainActivity extends AppCompatActivity {
    private BaseApiService mApiService;
    private Context mContext;
    public static Log selectedLog;
    private Spinner myStudentsSpinner;
    private ListView logListView;
    private Button billButton;
    private List<Log> listLogs;
    public static Student selectedStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_main);

        mContext = this;

        // Initialize API service
        mApiService = RetrofitClient.getClient().create(BaseApiService.class);

        // Initialize UI elements
        myStudentsSpinner = findViewById(R.id.MyStudents);
        logListView = findViewById(R.id.Logs);
        billButton = findViewById(R.id.bill);

        // Fill the spinner
        List<Student> students = LoginActivity.loggedParent.students;
        ArrayAdapter<Student> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, students);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        myStudentsSpinner.setAdapter(adapter);

        // Setup listeners
        setupListeners();
    }

    private void setupListeners() {
        billButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveActivity(mContext, ViewBillActivity.class);
            }
        });

        myStudentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStudent = (Student) parent.getItemAtPosition(position);
                getLogs(selectedStudent.student_class.id, selectedStudent.special_needs);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void getLogs(String class_id, boolean for_special_kids) {
        mApiService.getLogs(class_id, for_special_kids).enqueue(new Callback<BaseResponse<List<Log>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Log>>> call, Response<BaseResponse<List<Log>>> response) {
                if (response.isSuccessful()) {
                    BaseResponse<List<Log>> res = response.body();
                    if (res != null && "Logs retrieved".equals(res.message)) {
                        listLogs = res.payload;
                        listLogs.sort((o1, o2) -> o2.created_at.compareTo(o1.created_at));
                        LogArrayAdapter adapter = new LogArrayAdapter(mContext, listLogs);
                        logListView.setAdapter(adapter);

                    } else {
                        Toast.makeText(mContext, "Failed to retrieve logs", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to retrieve logs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Log>>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to retrieve logs", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class LogArrayAdapter extends ArrayAdapter<Log>{
        private TextView message;
        private TextView date;
        private View log_vew_container;
        public LogArrayAdapter(Context context, List<Log> logs){
            super(context, 0, logs);
        }



        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            Log log = getItem(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.log_view, parent, false);
            }
            log_vew_container = convertView.findViewById(R.id.log_view_container);
            message = convertView.findViewById(R.id.message);
            date = convertView.findViewById(R.id.date);
            //make the message only up to 15 characters
            String shownMessage = log.message;
            if(log.message.length() > 15){
                shownMessage = log.message.substring(0, 15) + "...";
            }
            message.setText(shownMessage);
            date.setText(log.created_at.toString());
            log_vew_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedLog = log;
                    System.out.println(selectedLog);
                    moveActivity(mContext, LogDetailActivity.class);
                }
            });
            return convertView;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (selectedStudent != null && selectedStudent.student_class != null) {
            getLogs(selectedStudent.student_class.id, selectedStudent.special_needs);
        } else {
            selectedStudent = LoginActivity.loggedParent.students.get(0);
        }
    }

    private void moveActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        startActivity(intent);
    }
}
