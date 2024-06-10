package com.example.fe_bhinekas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fe_bhinekas.model.Student;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private LayoutInflater inflater;

    // Constructor
    public StudentAdapter(Context context, List<Student> studentList) {
        this.inflater = LayoutInflater.from(context);
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.student_list_item, parent, false);
        return new StudentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        // Get the current student
        Student currentStudent = studentList.get(position);
        holder.studentName.setText(currentStudent.name);
        holder.studentId.setText(currentStudent.student_class.name);
        // Add more fields as needed
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    // ViewHolder class to hold item views
    public static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        TextView studentId;
        // Define more views here if needed

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.text_student_name);
            studentId = itemView.findViewById(R.id.student_class);
            // Initialize more views here
        }
    }
}
