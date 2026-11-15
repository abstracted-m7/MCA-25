package com.example.quizmania.student;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivityRevisionQuizBinding;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.RevisionAttempt;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.services.AdaptiveLearningService;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RevisionQuizActivity extends AppCompatActivity {

    private ActivityRevisionQuizBinding binding;
    private RevisionQuiz revisionQuiz;
    private int currentIndex = 0;
    private final Map<Integer, Integer> userAnswers = new HashMap<>();
    private AdaptiveLearningService adaptiveLearningService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRevisionQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adaptiveLearningService = new AdaptiveLearningService();

        String json = getIntent().getStringExtra("EXTRA_REVISION_JSON");
        if (json != null) {
            revisionQuiz = new Gson().fromJson(json, RevisionQuiz.class);
        }

        if (revisionQuiz == null || revisionQuiz.getQuestions() == null || revisionQuiz.getQuestions().isEmpty()) {
            Toast.makeText(this, "No revision questions found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.tvRevisionTitle.setText((revisionQuiz.getSubjectName() != null ? revisionQuiz.getSubjectName() : "Adaptive") + " Revision");
        binding.tvModeBadge.setText("QUESTION_BANK".equalsIgnoreCase(revisionQuiz.getRevisionType()) ? "Mistake Bank" : "AI Generated");

        binding.btnBack.setOnClickListener(v -> finish());
        binding.btnRevPrevious.setOnClickListener(v -> previousQuestion());
        binding.btnRevNext.setOnClickListener(v -> nextQuestion());
        binding.btnSubmitRevision.setOnClickListener(v -> showSubmitDialog());

        setupOptionClicks();
        displayCurrentQuestion();
    }

    private void displayCurrentQuestion() {
        List<Question> questions = revisionQuiz.getQuestions();
        if (currentIndex < 0 || currentIndex >= questions.size()) return;

        Question q = questions.get(currentIndex);
        binding.tvRevisionIndicator.setText("Revision Question " + (currentIndex + 1) + " of " + questions.size());
        binding.tvRevisionQuestionText.setText(q.getText());

        int progress = (int) (((currentIndex + 1) / (float) questions.size()) * 100);
        binding.revisionProgressBar.setProgress(progress);

        List<String> options = q.getOptions();
        binding.tvRevOptionTextA.setText(options.size() > 0 ? options.get(0) : "");
        binding.tvRevOptionTextB.setText(options.size() > 1 ? options.get(1) : "");
        binding.tvRevOptionTextC.setText(options.size() > 2 ? options.get(2) : "");
        binding.tvRevOptionTextD.setText(options.size() > 3 ? options.get(3) : "");

        resetOptionStyles();

        Integer sel = userAnswers.get(currentIndex);
        if (sel != null) {
            highlightSelectedOption(sel);
        }

        binding.btnRevPrevious.setEnabled(currentIndex > 0);
        binding.btnRevNext.setVisibility(currentIndex < questions.size() - 1 ? View.VISIBLE : View.GONE);
    }

    private void setupOptionClicks() {
        binding.cardRevOptionA.setOnClickListener(v -> selectOption(0));
        binding.cardRevOptionB.setOnClickListener(v -> selectOption(1));
        binding.cardRevOptionC.setOnClickListener(v -> selectOption(2));
        binding.cardRevOptionD.setOnClickListener(v -> selectOption(3));
    }

    private void selectOption(int idx) {
        userAnswers.put(currentIndex, idx);
        resetOptionStyles();
        highlightSelectedOption(idx);
    }

    private void resetOptionStyles() {
        binding.cardRevOptionA.setBackgroundResource(R.drawable.bg_option_default);
        binding.cardRevOptionB.setBackgroundResource(R.drawable.bg_option_default);
        binding.cardRevOptionC.setBackgroundResource(R.drawable.bg_option_default);
        binding.cardRevOptionD.setBackgroundResource(R.drawable.bg_option_default);
    }

    private void highlightSelectedOption(int idx) {
        switch (idx) {
            case 0: binding.cardRevOptionA.setBackgroundResource(R.drawable.bg_option_selected); break;
            case 1: binding.cardRevOptionB.setBackgroundResource(R.drawable.bg_option_selected); break;
            case 2: binding.cardRevOptionC.setBackgroundResource(R.drawable.bg_option_selected); break;
            case 3: binding.cardRevOptionD.setBackgroundResource(R.drawable.bg_option_selected); break;
        }
    }

    private void nextQuestion() {
        if (currentIndex < revisionQuiz.getQuestions().size() - 1) {
            currentIndex++;
            displayCurrentQuestion();
        }
    }

    private void previousQuestion() {
        if (currentIndex > 0) {
            currentIndex--;
            displayCurrentQuestion();
        }
    }

    private void showSubmitDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Submit Revision")
                .setMessage("Submit your revision answers for evaluation and mastery tracking?")
                .setPositiveButton("Submit", (dialog, which) -> submitRevision())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void submitRevision() {
        binding.btnSubmitRevision.setEnabled(false);

        // Assume baseline accuracy before revision was around 40% (since these were questions missed previously)
        int accuracyBefore = 40;

        adaptiveLearningService.submitRevisionQuiz(revisionQuiz, userAnswers, accuracyBefore,
                new AdaptiveLearningService.RevisionCompletionListener() {
                    @Override
                    public void onCompleted(RevisionAttempt attempt) {
                        openRevisionResults(attempt);
                    }

                    @Override
                    public void onError(String message) {
                        Toast.makeText(RevisionQuizActivity.this, "Completed locally: " + message, Toast.LENGTH_SHORT).show();
                        // Compute local counts
                        int total = revisionQuiz.getQuestions() != null ? revisionQuiz.getQuestions().size() : 0;
                        int correct = 0;
                        for (int i = 0; i < total; i++) {
                            Integer sel = userAnswers.get(i);
                            if (sel != null && sel == revisionQuiz.getQuestions().get(i).getCorrectOptionIndex()) {
                                correct++;
                            }
                        }
                        RevisionAttempt fallback = new RevisionAttempt();
                        fallback.setScore(correct);
                        fallback.setTotalQuestions(total);
                        fallback.setCorrectCount(correct);
                        fallback.setWrongCount(total - correct);
                        fallback.setAccuracyAfter(total > 0 ? (correct * 100) / total : 0);
                        fallback.setCompletedAt(System.currentTimeMillis());
                        openRevisionResults(fallback);
                    }
                });
    }

    private void openRevisionResults(RevisionAttempt attempt) {
        QuizAttempt qa = new QuizAttempt();
        qa.setId(attempt.getId() != null ? attempt.getId() : "ATT_REV_" + System.currentTimeMillis());
        qa.setStudentId(attempt.getStudentId());
        qa.setQuizId(revisionQuiz.getId());
        String subject = revisionQuiz.getSubjectName() != null ? revisionQuiz.getSubjectName() : "Adaptive";
        qa.setQuizTitle(subject + " - Adaptive Revision");
        qa.setSubjectId(attempt.getSubjectId());
        qa.setSubjectName(attempt.getSubjectName());
        qa.setScore(attempt.getScore());
        qa.setTotalQuestions(attempt.getTotalQuestions());
        qa.setCorrectCount(attempt.getCorrectCount());
        qa.setWrongCount(attempt.getWrongCount());
        qa.setUnansweredCount(Math.max(0, attempt.getTotalQuestions() - attempt.getCorrectCount() - attempt.getWrongCount()));
        qa.setPercentage(attempt.getAccuracyAfter());
        qa.setTimeTakenSeconds(0);
        qa.setCompletedAt(attempt.getCompletedAt() > 0 ? attempt.getCompletedAt() : System.currentTimeMillis());

        Quiz quizObj = new Quiz();
        quizObj.setId(revisionQuiz.getId());
        quizObj.setTitle(qa.getQuizTitle());
        quizObj.setSubjectId(revisionQuiz.getSubjectId());
        quizObj.setSubjectName(revisionQuiz.getSubjectName());
        quizObj.setQuestions(revisionQuiz.getQuestions());

        Intent intent = new Intent(RevisionQuizActivity.this, QuizResultActivity.class);
        intent.putExtra("EXTRA_ATTEMPT", qa);
        intent.putExtra("EXTRA_QUIZ_JSON", new Gson().toJson(quizObj));
        intent.putExtra("EXTRA_USER_ANSWERS_JSON", new Gson().toJson(userAnswers));
        startActivity(intent);
        finish();
    }
}
