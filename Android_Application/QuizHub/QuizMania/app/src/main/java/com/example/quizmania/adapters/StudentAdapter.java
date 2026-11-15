package com.example.quizmania.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmania.R;
import com.example.quizmania.model.User;

import java.util.ArrayList;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public interface OnStudentClickListener {
        void onStudentClick(User student);
    }

    private final List<User> students = new ArrayList<>();
    private final OnStudentClickListener listener;

    public StudentAdapter(OnStudentClickListener listener) {
        this.listener = listener;
    }

    public void setStudents(List<User> newStudents) {
        students.clear();
        if (newStudents != null) {
            students.addAll(newStudents);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        User student = students.get(position);
        holder.tvName.setText(student.getName() != null ? student.getName() : "Student");
        holder.tvRoll.setText("Roll: " + (student.getRollNumber() != null ? student.getRollNumber() : "N/A"));
        holder.tvEmail.setText(student.getEmail() != null ? student.getEmail() : "");

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onStudentClick(student);
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvRoll;
        TextView tvEmail;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvStudentName);
            tvRoll = itemView.findViewById(R.id.tvStudentRoll);
            tvEmail = itemView.findViewById(R.id.tvStudentEmail);
        }
    }
}
