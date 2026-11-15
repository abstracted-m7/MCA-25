package com.example.quizmania.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.adapters.RevisionAdapter;
import com.example.quizmania.databinding.ActivityStudentDashboardBinding;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.repository.AdaptiveLearningRepository;
import com.example.quizmania.repository.StudentRepository;
import com.example.quizmania.services.ProgressCalculationService;
import com.example.quizmania.student.AttemptQuizActivity;
import com.example.quizmania.student.MistakeNotebookActivity;
import com.example.quizmania.student.RevisionDashboardActivity;
import com.example.quizmania.student.RevisionQuizActivity;
import com.example.quizmania.student.StudentProgressActivity;
import com.example.quizmania.student.SubjectBrowseActivity;
import com.example.quizmania.util.SessionManager;
import com.example.quizmania.view.auth.AuthActivity;
import com.example.quizmania.view.feedback.FeedbackActivity;
import com.example.quizmania.view.quiz.AiQuizActivity;
import com.example.quizmania.viewmodel.DashboardViewModel;
import com.google.gson.Gson;

import java.util.List;

public class StudentDashboardActivity extends AppCompatActivity {

    private ActivityStudentDashboardBinding binding;
    private DashboardViewModel viewModel;
    private QuizAdapter adapter;
    private AdaptiveLearningRepository adaptiveRepo;
    private StudentRepository studentRepo;
    private RevisionQuiz currentPendingRevision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        adaptiveRepo = new AdaptiveLearningRepository();
        studentRepo = new StudentRepository();

        setupUIHeader();
        setupRecyclerView();
        setupQuickActions();

        viewModel.getQuizzes().observe(this, quizzes -> {
            if (quizzes != null) {
                adapter.setQuizList(quizzes, quiz -> {
                    Intent intent = new Intent(StudentDashboardActivity.this, AttemptQuizActivity.class);
                    intent.putExtra(AttemptQuizActivity.EXTRA_QUIZ_ID, quiz.getId());
                    startActivity(intent);
                });
            }
        });
    }

    private void setupUIHeader() {
        SessionManager sm = SessionManager.getInstance(this);
        binding.tvWelcomeStudent.setText("Welcome back, " + sm.getName() + "!");
        binding.tvStudentRollNumber.setText("Roll: " + sm.getRollNumber());

        binding.ivLogout.setOnClickListener(v -> {
            sm.clear();
            viewModel.logout();
            startActivity(new Intent(StudentDashboardActivity.this, AuthActivity.class));
            finish();
        });
    }

    private void setupRecyclerView() {
        binding.rvDashboardQuizzes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizAdapter();
        binding.rvDashboardQuizzes.setAdapter(adapter);
    }

    private void setupQuickActions() {
        binding.cardActionAttemptQuiz.setOnClickListener(v -> {
            startActivity(new Intent(this, SubjectBrowseActivity.class));
        });

        binding.tvViewAllQuizzes.setOnClickListener(v -> {
            startActivity(new Intent(this, SubjectBrowseActivity.class));
        });

        binding.cardActionAiQuiz.setOnClickListener(v -> {
            startActivity(new Intent(this, AiQuizActivity.class));
        });

        binding.fabAiQuiz.setOnClickListener(v -> {
            startActivity(new Intent(this, AiQuizActivity.class));
        });

        binding.cardActionRevision.setOnClickListener(v -> {
            startActivity(new Intent(this, RevisionDashboardActivity.class));
        });

        binding.cardActionMistakes.setOnClickListener(v -> {
            startActivity(new Intent(this, MistakeNotebookActivity.class));
        });

        binding.cardActionProgress.setOnClickListener(v -> {
            startActivity(new Intent(this, StudentProgressActivity.class));
        });

        binding.cardActionFeedback.setOnClickListener(v -> {
            startActivity(new Intent(this, FeedbackActivity.class));
        });

        binding.btnBannerStartRevision.setOnClickListener(v -> {
            if (currentPendingRevision != null) {
                Intent intent = new Intent(this, RevisionQuizActivity.class);
                intent.putExtra("EXTRA_REVISION_JSON", new Gson().toJson(currentPendingRevision));
                startActivity(intent);
            } else {
                startActivity(new Intent(this, RevisionDashboardActivity.class));
            }
        });
    }

    private void loadDashboardStats() {
        String studentId = SessionManager.getInstance(this).getUid();
        if (studentId == null) studentId = "STU_ANON";

        studentRepo.fetchStudentHistory(studentId, (student, attempts, mistakes) -> {
            ProgressCalculationService.StudentAnalytics stats =
                    ProgressCalculationService.computeAnalytics(attempts, mistakes);

            binding.tvStatTotalQuizzes.setText(String.valueOf(stats.totalQuizzes));
            binding.tvStatAccuracy.setText(stats.overallAccuracy + "%");
            binding.tvStatAvgScore.setText(stats.averageScore + "%");
            binding.tvStreakDays.setText(stats.streakDays + (stats.streakDays == 1 ? " Day Streak" : " Days Streak"));

            // Update subject preview chart
            binding.subjectChartPreview.setData(stats.subjectAccuracy);
        });

        // Check for pending 5-attempt adaptive revision quizzes
        adaptiveRepo.fetchPendingRevisionQuizzes(studentId, new AdaptiveLearningRepository.RevisionListCallback() {
            @Override
            public void onSuccess(List<RevisionQuiz> quizzes) {
                if (quizzes != null && !quizzes.isEmpty()) {
                    currentPendingRevision = quizzes.get(0);
                    binding.cardPendingRevisionBanner.setVisibility(View.VISIBLE);
                    String subj = currentPendingRevision.getSubjectName() != null ? currentPendingRevision.getSubjectName() : "Java";
                    binding.tvPendingRevisionDetails.setText("5 attempts completed in " + subj + "! Your personalized revision quiz is ready with "
                            + currentPendingRevision.getQuestions().size() + " targeted questions.");
                } else {
                    binding.cardPendingRevisionBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(String error) {
                binding.cardPendingRevisionBanner.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchQuizzes();
        loadDashboardStats();
    }
}
