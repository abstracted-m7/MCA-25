package com.example.quizmania.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.databinding.ActivityQuizResultBinding;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.view.dashboard.StudentDashboardActivity;
import com.google.gson.Gson;

public class QuizResultActivity extends AppCompatActivity {

    private ActivityQuizResultBinding binding;
    private QuizAttempt attempt;
    private Quiz quiz;
    private RevisionQuiz generatedRevision;
    private boolean isRevisionTriggered = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQuizResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        attempt = (QuizAttempt) getIntent().getSerializableExtra("EXTRA_ATTEMPT");
        isRevisionTriggered = getIntent().getBooleanExtra("EXTRA_REVISION_TRIGGERED", false);

        String quizJson = getIntent().getStringExtra("EXTRA_QUIZ_JSON");
        if (quizJson != null) {
            quiz = new Gson().fromJson(quizJson, Quiz.class);
        }

        String revJson = getIntent().getStringExtra("EXTRA_REVISION_JSON");
        if (revJson != null) {
            generatedRevision = new Gson().fromJson(revJson, RevisionQuiz.class);
        }

        String userAnswersJson = getIntent().getStringExtra("EXTRA_USER_ANSWERS_JSON");

        displayResults();

        binding.btnReturnDashboard.setOnClickListener(v -> {
            Intent intent = new Intent(this, StudentDashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });

        binding.btnReviewAnswers.setOnClickListener(v -> {
            Intent intent = new Intent(this, ReviewAnswersActivity.class);
            if (quizJson != null) {
                intent.putExtra("EXTRA_QUIZ_JSON", quizJson);
            }
            if (userAnswersJson != null) {
                intent.putExtra("EXTRA_USER_ANSWERS_JSON", userAnswersJson);
            }
            startActivity(intent);
        });

        binding.btnGoToRevision.setOnClickListener(v -> {
            Intent intent = new Intent(this, RevisionDashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void displayResults() {
        if (attempt != null) {
            binding.tvQuizTitleResult.setText(attempt.getQuizTitle());
            binding.tvPercentage.setText(attempt.getPercentage() + "%");
            binding.tvScoreSummary.setText("Score: " + attempt.getScore() + " / " + attempt.getTotalQuestions() + " marks");
            binding.tvCountCorrect.setText(String.valueOf(attempt.getCorrectCount()));
            binding.tvCountWrong.setText(String.valueOf(attempt.getWrongCount()));
            binding.tvCountUnanswered.setText("Unanswered: " + attempt.getUnansweredCount());

            long mins = attempt.getTimeTakenSeconds() / 60;
            long secs = attempt.getTimeTakenSeconds() % 60;
            binding.tvTimeTaken.setText("Time Taken: " + mins + "m " + secs + "s");

            if (attempt.getPercentage() >= 80) {
                binding.tvPerformanceStatus.setText("Outstanding Performance! 🏆");
            } else if (attempt.getPercentage() >= 60) {
                binding.tvPerformanceStatus.setText("Good Effort! Keep Practicing 👍");
            } else {
                binding.tvPerformanceStatus.setText("Needs Improvement – Review Mistakes 📚");
            }
        }

        if (isRevisionTriggered) {
            binding.cardRevisionTriggered.setVisibility(View.VISIBLE);
            String subject = (attempt != null && attempt.getSubjectName() != null) ? attempt.getSubjectName() : "this subject";
            binding.tvRevisionMessage.setText("Milestone reached! After 5 completed attempts in " + subject +
                    ", QuizHub created a personalized adaptive revision quiz targeting your incorrect answers.");
        }
    }
}
