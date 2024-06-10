package com.example.fe_bhinekas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.Extracurricular;
import com.example.fe_bhinekas.model.Kelas;
import com.example.fe_bhinekas.model.Student;
import com.example.fe_bhinekas.request.BaseApiService;
import com.example.fe_bhinekas.request.UtilsApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManageStudentsActivity extends AppCompatActivity {
    private EditText autoCompleteParent;
    private Spinner spinnerStudentClass;
    private Spinner spinnerExtracurriclar;
    private EditText editTextBatch;
    private CheckBox checkSpecialNeeds;
    private Button buttonSearch;
    private RecyclerView recyclerView;
    private StudentAdapter studentAdapter;
    private Context mContext;
    private BaseApiService mApiService;
    private List<Extracurricular> allExtracurriculars = new ArrayList<>();
    private List<Student> foundStudents = new ArrayList<>();
    private List<Kelas> allClasses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_students);

        mContext = this;
        mApiService = UtilsApi.getApiService();

        // Initialize UI components
        autoCompleteParent = findViewById(R.id.editTextParent); // Corrected ID might be different
        spinnerStudentClass = findViewById(R.id.spinnerStudentClass);
        spinnerExtracurriclar = findViewById(R.id.spinnerExtracurricular);
        editTextBatch = findViewById(R.id.editTextBatch);
        checkSpecialNeeds = findViewById(R.id.checkboxSpecialNeeds);
        buttonSearch = findViewById(R.id.buttonSearch);
        recyclerView = findViewById(R.id.recyclerViewSearchResults);

        setupAdapters();
        getAllExtracurriculars();
        getAllClasses();

        // Setup the RecyclerView
        studentAdapter = new StudentAdapter(this, foundStudents);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonSearch.setOnClickListener(v -> handleSearch());
    }

    private void setupAdapters() {
        ArrayAdapter<Kelas> classAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allClasses);
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStudentClass.setAdapter(classAdapter);

        ArrayAdapter<Extracurricular> extracurricularAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, allExtracurriculars);
        extracurricularAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerExtracurriclar.setAdapter(extracurricularAdapter);
    }

    private void getAllExtracurriculars() {
        mApiService.getAllExtracurriculars().enqueue(new Callback<BaseResponse<List<Extracurricular>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Extracurricular>>> call, Response<BaseResponse<List<Extracurricular>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Extracurricular> extracurriculars = response.body().payload;
                    if (extracurriculars != null && !extracurriculars.isEmpty()) {
                        allExtracurriculars.clear();
                        allExtracurriculars.add(new Extracurricular(" ", "All Extracurriculars", 0));
                        allExtracurriculars.addAll(extracurriculars);
                        ArrayAdapter<Extracurricular> extracurricularAdapter = (ArrayAdapter<Extracurricular>) spinnerExtracurriclar.getAdapter();
                        extracurricularAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "No extracurriculars found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to fetch extracurriculars: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Extracurricular>>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to get extracurriculars: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllClasses() {
        mApiService.getAllKelas().enqueue(new Callback<BaseResponse<List<Kelas>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Kelas>>> call, Response<BaseResponse<List<Kelas>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Kelas> kelas = response.body().payload;
                    if (kelas != null && !kelas.isEmpty()) {
                        allClasses.clear();
                        allClasses.add(new Kelas(" ", "All Classes", 0));
                        allClasses.addAll(kelas);
                        ArrayAdapter<Kelas> classAdapter = (ArrayAdapter<Kelas>) spinnerStudentClass.getAdapter();
                        classAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(mContext, "No classes found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to fetch classes: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Kelas>>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to get classes: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void handleSearch() {
        String parent = autoCompleteParent.getText().toString().isEmpty() ? " " : autoCompleteParent.getText().toString(); // Send empty space if no input
        Kelas kelas = (Kelas) spinnerStudentClass.getSelectedItem();  // This will be null if nothing is selected
        Extracurricular extracurricular = (Extracurricular) spinnerExtracurriclar.getSelectedItem();  // This will be null if nothing is selected
        String extracurricularId = extracurricular == null ? " " : extracurricular.id; // Send empty space if no extracurricular selected
        Integer batch = editTextBatch.getText().toString().isEmpty() ? -1 : Integer.parseInt(editTextBatch.getText().toString()); // Send -1 if no batch number provided
        boolean specialNeeds = checkSpecialNeeds.isChecked();
        String classId = (kelas == null) ? " " : kelas.id; // Send empty space if no class selected

        // As you're now using default values, you might not need to check for null or empty to decide whether to perform the search
        mApiService.getStudents(classId, parent, batch, specialNeeds, extracurricularId).enqueue(new Callback<BaseResponse<List<Student>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<Student>>> call, Response<BaseResponse<List<Student>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Student> students = response.body().payload;
                    if (students != null && !students.isEmpty()) {
                        updateStudentsList(students);
                        Toast.makeText(mContext, "Students found: " + foundStudents.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, "No students found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, "Failed to fetch students: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<List<Student>>> call, Throwable t) {
                Toast.makeText(mContext, "Failed to get students: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateStudentsList(List<Student> newStudents) {
        foundStudents.clear();
        foundStudents.addAll(newStudents);
        studentAdapter.notifyDataSetChanged();
    }
}