package com.example.quizmania.teacher;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.adapters.StudentAdapter;
import com.example.quizmania.databinding.ActivityStudentTrackingBinding;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.User;
import com.example.quizmania.model.WrongAnswer;
import com.example.quizmania.repository.StudentRepository;
import com.example.quizmania.services.ProgressCalculationService;

import java.util.ArrayList;
import java.util.List;

public class StudentTrackingActivity extends AppCompatActivity {

    private ActivityStudentTrackingBinding binding;
    private StudentRepository studentRepository;
    private StudentAdapter adapter;
    private final List<User> studentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentTrackingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentRepository = new StudentRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupRecyclerView();
        setupSearch();
        loadAllStudents();
    }

    private void setupRecyclerView() {
        binding.rvStudents.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this::showStudentPerformanceDialog);
        binding.rvStudents.setAdapter(adapter);
    }

    private void setupSearch() {
        binding.etSearchRoll.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s != null ? s.toString().trim() : "";
                binding.ivClearSearch.setVisibility(query.isEmpty() ? View.GONE : View.VISIBLE);
                searchStudents(query);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        binding.ivClearSearch.setOnClickListener(v -> binding.etSearchRoll.setText(""));
    }

    private void loadAllStudents() {
        binding.progressBar.setVisibility(View.VISIBLE);
        studentRepository.fetchAllStudents(students -> {
            binding.progressBar.setVisibility(View.GONE);
            studentList.clear();
            if (students != null) studentList.addAll(students);
            adapter.setStudents(studentList);
            binding.tvEmptyStudents.setVisibility(studentList.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void searchStudents(String query) {
        studentRepository.searchStudentByRollNumber(query, students -> {
            adapter.setStudents(students);
            binding.tvEmptyStudents.setVisibility(students.isEmpty() ? View.VISIBLE : View.GONE);
        });
    }

    private void showStudentPerformanceDialog(User student) {
        binding.progressBar.setVisibility(View.VISIBLE);
        studentRepository.fetchStudentHistory(student.getId(), (u, attempts, mistakes) -> {
            binding.progressBar.setVisibility(View.GONE);

            ProgressCalculationService.StudentAnalytics stats =
                    ProgressCalculationService.computeAnalytics(attempts, mistakes);

            StringBuilder msg = new StringBuilder();
            msg.append("Name: ").append(student.getName()).append("\n");
            msg.append("Roll Number: ").append(student.getRollNumber() != null ? student.getRollNumber() : "N/A").append("\n");
            msg.append("Email: ").append(student.getEmail()).append("\n\n");

            msg.append("Total Quizzes Attempted: ").append(stats.totalQuizzes).append("\n");
            msg.append("Overall Accuracy: ").append(stats.overallAccuracy).append("%\n");
            msg.append("Total Correct: ").append(stats.totalCorrect).append("\n");
            msg.append("Total Incorrect: ").append(stats.totalWrong).append("\n");
            msg.append("Mastered Questions: ").append(stats.masteredQuestionsCount).append("\n\n");

            if (stats.weakTopics != null && !stats.weakTopics.isEmpty()) {
                msg.append("Weak Topics:\n");
                for (String t : stats.weakTopics) {
                    msg.append(" • ").append(t).append("\n");
                }
            } else {
                msg.append("Weak Topics: None recorded\n");
            }

            new AlertDialog.Builder(this)
                    .setTitle("Student Performance Profile")
                    .setMessage(msg.toString())
                    .setPositiveButton("Close", null)
                    .show();
        });
    }
}
