package com.example.quizmania.view.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.databinding.ActivityTeacherDashboardBinding;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.repository.QuizRepository;
import com.example.quizmania.repository.StudentRepository;
import com.example.quizmania.repository.SubjectRepository;
import com.example.quizmania.teacher.AddQuestionActivity;
import com.example.quizmania.teacher.CreateQuizActivity;
import com.example.quizmania.teacher.ManageSubjectActivity;
import com.example.quizmania.teacher.StudentTrackingActivity;
import com.example.quizmania.teacher.TeacherAnalyticsActivity;
import com.example.quizmania.util.SessionManager;
import com.example.quizmania.view.auth.AuthActivity;
import com.example.quizmania.view.feedback.FeedbackActivity;
import com.example.quizmania.viewmodel.DashboardViewModel;

public class AdminDashboardActivity extends AppCompatActivity {

    private ActivityTeacherDashboardBinding binding;
    private DashboardViewModel viewModel;
    private QuizAdapter adapter;
    private StudentRepository studentRepo;
    private SubjectRepository subjectRepo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        studentRepo = new StudentRepository();
        subjectRepo = new SubjectRepository();

        setupUIHeader();
        setupRecyclerView();
        setupQuickActions();

        viewModel.getQuizzes().observe(this, quizzes -> {
            if (quizzes != null) {
                adapter.setQuizList(quizzes, quiz -> {
                    // Preview quiz
                });
                binding.tvMetricQuizzes.setText(String.valueOf(quizzes.size()));
            }
        });
    }

    private void setupUIHeader() {
        SessionManager sm = SessionManager.getInstance(this);
        binding.tvTeacherWelcome.setText("Welcome, " + (sm.getName() != null ? sm.getName() : "Teacher") + "!");

        binding.ivTeacherLogout.setOnClickListener(v -> {
            sm.clear();
            viewModel.logout();
            startActivity(new Intent(AdminDashboardActivity.this, AuthActivity.class));
            finish();
        });
    }

    private void setupRecyclerView() {
        binding.rvTeacherQuizzes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizAdapter();
        SessionManager sm = SessionManager.getInstance(this);
        adapter.setCurrentTeacherId(sm.getUid());
        adapter.setOnQuizDeleteListener(this::confirmDeleteQuiz);
        binding.rvTeacherQuizzes.setAdapter(adapter);
    }

    private void confirmDeleteQuiz(Quiz quiz) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Active Quiz")
                .setMessage("Are you sure you want to delete '" + quiz.getTitle() + "'?\n\nThis active quiz will be permanently removed from the student portal.")
                .setPositiveButton("Delete", (dialog, which) -> {
                    new QuizRepository().deleteQuiz(quiz.getId(), new QuizRepository.ActionCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(AdminDashboardActivity.this, "Quiz deleted successfully!", Toast.LENGTH_SHORT).show();
                            viewModel.fetchQuizzes();
                            loadTeacherStats();
                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(AdminDashboardActivity.this, "Failed to delete: " + error, Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void setupQuickActions() {
        binding.cardTeacherSubjects.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageSubjectActivity.class));
        });

        binding.cardTeacherQuestionBank.setOnClickListener(v -> {
            startActivity(new Intent(this, AddQuestionActivity.class));
        });

        binding.cardTeacherCreateQuiz.setOnClickListener(v -> {
            startActivity(new Intent(this, CreateQuizActivity.class));
        });

        binding.cardTeacherStudentTracking.setOnClickListener(v -> {
            startActivity(new Intent(this, StudentTrackingActivity.class));
        });

        binding.cardTeacherAnalytics.setOnClickListener(v -> {
            startActivity(new Intent(this, TeacherAnalyticsActivity.class));
        });

        binding.cardTeacherFeedback.setOnClickListener(v -> {
            startActivity(new Intent(this, FeedbackActivity.class));
        });
    }

    private void loadTeacherStats() {
        // Students count
        studentRepo.fetchAllStudents(students -> {
            binding.tvMetricStudents.setText(String.valueOf(students.size()));
        });

        // Subjects count
        subjectRepo.fetchSubjects(new SubjectRepository.SubjectsCallback() {
            @Override
            public void onSuccess(java.util.List<com.example.quizmania.model.Subject> subjects) {
                binding.tvMetricSubjects.setText(String.valueOf(subjects.size()));
            }

            @Override
            public void onFailure(String error) {}
        });

        // Class total attempts
        studentRepo.fetchClassAnalytics(analytics -> {
            Integer totalAttempts = (Integer) analytics.get("totalAttempts");
            binding.tvMetricAttempts.setText(String.valueOf(totalAttempts != null ? totalAttempts : 0));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchQuizzes();
        loadTeacherStats();
    }
}
