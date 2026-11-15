package com.example.quizmania.student;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivityAttemptQuizBinding;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.repository.QuizRepository;
import com.example.quizmania.services.AdaptiveLearningService;
import com.example.quizmania.util.SessionManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AttemptQuizActivity extends AppCompatActivity {

    public static final String EXTRA_QUIZ_JSON = "extra_quiz_json";
    public static final String EXTRA_QUIZ_ID = "extra_quiz_id";

    private ActivityAttemptQuizBinding binding;
    private Quiz quiz;
    private int currentQuestionIndex = 0;
    private final Map<Integer, Integer> userAnswers = new HashMap<>(); // Question Index -> Selected Option Index

    private CountDownTimer countDownTimer;
    private long timeRemainingMillis;
    private long totalDurationMillis;
    private boolean isSubmitted = false;

    private AdaptiveLearningService adaptiveLearningService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAttemptQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adaptiveLearningService = new AdaptiveLearningService();

        loadQuizData();

        binding.btnBack.setOnClickListener(v -> showExitConfirmationDialog());
        binding.btnPrevious.setOnClickListener(v -> previousQuestion());
        binding.btnNext.setOnClickListener(v -> nextQuestion());
        binding.btnSubmitQuiz.setOnClickListener(v -> showSubmitConfirmationDialog());

        setupOptionClicks();
    }

    private void loadQuizData() {
        String quizJson = getIntent().getStringExtra(EXTRA_QUIZ_JSON);
        if (quizJson != null && !quizJson.trim().isEmpty()) {
            try {
                quiz = new Gson().fromJson(quizJson, Quiz.class);
                initQuiz();
                return;
            } catch (Exception ignored) {}
        }

        String quizId = getIntent().getStringExtra(EXTRA_QUIZ_ID);
        if (quizId != null && !quizId.trim().isEmpty()) {
            new QuizRepository().fetchQuizDetails(quizId, new QuizRepository.QuizCallback() {
                @Override
                public void onSuccess(Quiz fetched) {
                    quiz = fetched;
                    initQuiz();
                }

                @Override
                public void onFailure(String error) {
                    Toast.makeText(AttemptQuizActivity.this, error, Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } else {
            Toast.makeText(this, "Quiz not found", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initQuiz() {
        if (quiz == null || quiz.getQuestions() == null || quiz.getQuestions().isEmpty()) {
            Toast.makeText(this, "No questions found in this quiz", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.tvQuizTitle.setText(quiz.getTitle());
        totalDurationMillis = quiz.getDurationMinutes() * 60 * 1000L;
        if (totalDurationMillis <= 0) totalDurationMillis = 10 * 60 * 1000L;
        timeRemainingMillis = totalDurationMillis;

        startTimer();
        displayCurrentQuestion();
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(totalDurationMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainingMillis = millisUntilFinished;
                long minutes = (millisUntilFinished / 1000) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                binding.tvTimer.setText(String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds));
            }

            @Override
            public void onFinish() {
                binding.tvTimer.setText("00:00");
                Toast.makeText(AttemptQuizActivity.this, "Time is up! Submitting answers...", Toast.LENGTH_LONG).show();
                submitQuiz();
            }
        }.start();
    }

    private void displayCurrentQuestion() {
        List<Question> questions = quiz.getQuestions();
        if (currentQuestionIndex < 0 || currentQuestionIndex >= questions.size()) return;

        Question q = questions.get(currentQuestionIndex);

        binding.tvQuestionIndicator.setText("Question " + (currentQuestionIndex + 1) + " of " + questions.size());
        binding.tvTopicBadge.setText(q.getTopicName() != null ? q.getTopicName() : (quiz.getSubjectName() != null ? quiz.getSubjectName() : "General"));
        binding.tvQuestionText.setText(q.getText());

        int progress = (int) (((currentQuestionIndex + 1) / (float) questions.size()) * 100);
        binding.quizProgressBar.setProgress(progress);

        List<String> options = q.getOptions();
        binding.tvOptionTextA.setText(options.size() > 0 ? options.get(0) : "");
        binding.tvOptionTextB.setText(options.size() > 1 ? options.get(1) : "");
        binding.tvOptionTextC.setText(options.size() > 2 ? options.get(2) : "");
        binding.tvOptionTextD.setText(options.size() > 3 ? options.get(3) : "");

        resetOptionStyles();

        Integer selected = userAnswers.get(currentQuestionIndex);
        if (selected != null) {
            highlightSelectedOption(selected);
        }

        binding.btnPrevious.setEnabled(currentQuestionIndex > 0);
        binding.btnNext.setVisibility(currentQuestionIndex < questions.size() - 1 ? View.VISIBLE : View.GONE);
    }

    private void setupOptionClicks() {
        binding.cardOptionA.setOnClickListener(v -> selectOption(0));
        binding.cardOptionB.setOnClickListener(v -> selectOption(1));
        binding.cardOptionC.setOnClickListener(v -> selectOption(2));
        binding.cardOptionD.setOnClickListener(v -> selectOption(3));
    }

    private void selectOption(int optionIndex) {
        if (isSubmitted) return;
        userAnswers.put(currentQuestionIndex, optionIndex);
        resetOptionStyles();
        highlightSelectedOption(optionIndex);
    }

    private void resetOptionStyles() {
        binding.cardOptionA.setBackgroundResource(R.drawable.bg_option_default);
        binding.cardOptionB.setBackgroundResource(R.drawable.bg_option_default);
        binding.cardOptionC.setBackgroundResource(R.drawable.bg_option_default);
        binding.cardOptionD.setBackgroundResource(R.drawable.bg_option_default);
    }

    private void highlightSelectedOption(int index) {
        switch (index) {
            case 0:
                binding.cardOptionA.setBackgroundResource(R.drawable.bg_option_selected);
                break;
            case 1:
                binding.cardOptionB.setBackgroundResource(R.drawable.bg_option_selected);
                break;
            case 2:
                binding.cardOptionC.setBackgroundResource(R.drawable.bg_option_selected);
                break;
            case 3:
                binding.cardOptionD.setBackgroundResource(R.drawable.bg_option_selected);
                break;
        }
    }

    private void nextQuestion() {
        if (currentQuestionIndex < quiz.getQuestions().size() - 1) {
            currentQuestionIndex++;
            displayCurrentQuestion();
        }
    }

    private void previousQuestion() {
        if (currentQuestionIndex > 0) {
            currentQuestionIndex--;
            displayCurrentQuestion();
        }
    }

    private void showSubmitConfirmationDialog() {
        int total = quiz.getQuestions().size();
        int answered = userAnswers.size();
        int unanswered = total - answered;

        new AlertDialog.Builder(this)
                .setTitle("Submit Quiz")
                .setMessage("You have answered " + answered + " of " + total + " questions.\n" +
                        (unanswered > 0 ? "You have " + unanswered + " unanswered questions.\n" : "") +
                        "Do you want to submit your quiz now?")
                .setPositiveButton("Submit Now", (dialog, which) -> submitQuiz())
                .setNegativeButton("Keep Working", null)
                .show();
    }

    private void submitQuiz() {
        if (isSubmitted) return;
        isSubmitted = true;

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        binding.btnSubmitQuiz.setEnabled(false);
        binding.btnNext.setEnabled(false);
        binding.btnPrevious.setEnabled(false);

        SessionManager sm = SessionManager.getInstance(this);
        String studentId = sm.getUid() != null ? sm.getUid() : "STU_ANON";
        String rollNumber = sm.getRollNumber() != null ? sm.getRollNumber() : "BWU/MCA/25/040";
        String studentName = sm.getName() != null ? sm.getName() : "Student";

        long timeTakenSeconds = (totalDurationMillis - timeRemainingMillis) / 1000L;

        adaptiveLearningService.submitQuiz(quiz, userAnswers, studentId, rollNumber, studentName, timeTakenSeconds,
                new AdaptiveLearningService.SubmissionListener() {
                    @Override
                    public void onCompleted(QuizAttempt attempt, boolean revisionTriggered, RevisionQuiz generatedRevision) {
                        Intent intent = new Intent(AttemptQuizActivity.this, QuizResultActivity.class);
                        intent.putExtra("EXTRA_ATTEMPT", attempt);
                        intent.putExtra("EXTRA_QUIZ_JSON", new Gson().toJson(quiz));
                        intent.putExtra("EXTRA_USER_ANSWERS_JSON", new Gson().toJson(userAnswers));
                        intent.putExtra("EXTRA_REVISION_TRIGGERED", revisionTriggered);
                        if (generatedRevision != null) {
                            intent.putExtra("EXTRA_REVISION_JSON", new Gson().toJson(generatedRevision));
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(AttemptQuizActivity.this, "Submission note: " + message, Toast.LENGTH_SHORT).show();
                        // Open result view anyway with locally computed score
                        Intent intent = new Intent(AttemptQuizActivity.this, QuizResultActivity.class);
                        intent.putExtra("EXTRA_QUIZ_JSON", new Gson().toJson(quiz));
                        intent.putExtra("EXTRA_USER_ANSWERS_JSON", new Gson().toJson(userAnswers));
                        startActivity(intent);
                        finish();
                    }
                });
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Quit Quiz?")
                .setMessage("Are you sure you want to exit? Your progress in this quiz will be lost.")
                .setPositiveButton("Quit", (dialog, which) -> {
                    if (countDownTimer != null) countDownTimer.cancel();
                    finish();
                })
                .setNegativeButton("Stay", null)
                .show();
    }

    @Override
    public void onBackPressed() {
        showExitConfirmationDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
