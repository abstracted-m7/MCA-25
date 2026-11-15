package com.example.quizmania.student;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.databinding.ActivityStudentProgressBinding;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.WrongAnswer;
import com.example.quizmania.repository.StudentRepository;
import com.example.quizmania.services.ProgressCalculationService;
import com.example.quizmania.util.SessionManager;

import java.util.List;

public class StudentProgressActivity extends AppCompatActivity {

    private ActivityStudentProgressBinding binding;
    private StudentRepository studentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentProgressBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentRepository = new StudentRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        loadStudentAnalytics();
    }

    private void loadStudentAnalytics() {
        String studentId = SessionManager.getInstance(this).getUid();
        if (studentId == null) studentId = "STU_ANON";

        studentRepository.fetchStudentHistory(studentId, (student, attempts, mistakes) -> {
            displayAnalytics(attempts, mistakes);
        });
    }

    private void displayAnalytics(List<QuizAttempt> attempts, List<WrongAnswer> mistakes) {
        ProgressCalculationService.StudentAnalytics stats =
                ProgressCalculationService.computeAnalytics(attempts, mistakes);

        binding.tvProgOverallAccuracy.setText(stats.overallAccuracy + "%");
        binding.tvProgTotalQuizzes.setText(String.valueOf(stats.totalQuizzes));
        binding.tvProgTotalQuestions.setText(String.valueOf(stats.totalQuestions));
        binding.tvProgCorrectCount.setText("Correct: " + stats.totalCorrect);
        binding.tvProgWrongCount.setText("Incorrect: " + stats.totalWrong);
        binding.tvProgStreak.setText("🔥 " + stats.streakDays + (stats.streakDays == 1 ? " Day" : " Days"));

        // Populate Bar Chart
        binding.subjectBarChart.setData(stats.subjectAccuracy);

        // Weak topics
        if (stats.weakTopics != null && !stats.weakTopics.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String t : stats.weakTopics) {
                sb.append("• ").append(t).append("\n");
            }
            binding.tvWeakTopicsOverview.setText(sb.toString().trim());
        } else {
            binding.tvWeakTopicsOverview.setText("No weak topics identified yet. Keep testing yourself!");
        }

        // Mastery
        binding.tvProgWeakCount.setText(stats.weakQuestionsCount + " Weak");
        binding.tvProgPracticingCount.setText(stats.practicingQuestionsCount + " Practicing");
        binding.tvProgMasteredCount.setText(stats.masteredQuestionsCount + " Mastered");
    }
}
