package com.example.quizmania.view.quiz;

import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.R;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.google.gson.Gson;

import java.util.List;

public class QuizEngineActivity extends AppCompatActivity {

    public static final String EXTRA_QUIZ_JSON =
            "extra_quiz_json";

    private Quiz quiz;

    private int currentQuestionIndex = 0;

    private int score = 0;

    private boolean answerSubmitted = false;

    private android.widget.TextView tvQuizTitle;
    private android.widget.TextView tvQuestionNumber;
    private android.widget.TextView tvQuestion;

    private android.widget.RadioGroup radioGroupOptions;

    private RadioButton radioOption1;
    private RadioButton radioOption2;
    private RadioButton radioOption3;
    private RadioButton radioOption4;

    private Button btnSubmit;
    private Button btnNext;

    private android.view.View layoutFeedback;

    private android.widget.TextView tvResult;
    private android.widget.TextView tvYourAnswer;
    private android.widget.TextView tvCorrectAnswer;
    private android.widget.TextView tvExplanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(
                R.layout.activity_quiz_engine
        );

        initializeViews();

        String quizJson =
                getIntent()
                        .getStringExtra(
                                EXTRA_QUIZ_JSON
                        );

        if (quizJson == null
                || quizJson.trim().isEmpty()) {

            Toast.makeText(
                    this,
                    "Quiz data not found",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }

        try {

            quiz =
                    new Gson()
                            .fromJson(
                                    quizJson,
                                    Quiz.class
                            );

        } catch (Exception e) {

            Toast.makeText(
                    this,
                    "Unable to load quiz: "
                            + e.getMessage(),
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }

        if (quiz == null
                || quiz.getQuestions() == null
                || quiz.getQuestions().isEmpty()) {

            Toast.makeText(
                    this,
                    "Quiz contains no questions",
                    Toast.LENGTH_LONG
            ).show();

            finish();

            return;
        }

        tvQuizTitle.setText(
                quiz.getTitle() != null
                        ? quiz.getTitle()
                        : "AI Quiz"
        );

        showQuestion();
    }

    private void initializeViews() {

        tvQuizTitle =
                findViewById(
                        R.id.tvQuizTitle
                );

        tvQuestionNumber =
                findViewById(
                        R.id.tvQuestionNumber
                );

        tvQuestion =
                findViewById(
                        R.id.tvQuestion
                );

        radioGroupOptions =
                findViewById(
                        R.id.radioGroupOptions
                );

        radioOption1 =
                findViewById(
                        R.id.radioOption1
                );

        radioOption2 =
                findViewById(
                        R.id.radioOption2
                );

        radioOption3 =
                findViewById(
                        R.id.radioOption3
                );

        radioOption4 =
                findViewById(
                        R.id.radioOption4
                );

        btnSubmit =
                findViewById(
                        R.id.btnSubmit
                );

        btnNext =
                findViewById(
                        R.id.btnNext
                );

        layoutFeedback =
                findViewById(
                        R.id.layoutFeedback
                );

        tvResult =
                findViewById(
                        R.id.tvResult
                );

        tvYourAnswer =
                findViewById(
                        R.id.tvYourAnswer
                );

        tvCorrectAnswer =
                findViewById(
                        R.id.tvCorrectAnswer
                );

        tvExplanation =
                findViewById(
                        R.id.tvExplanation
                );

        btnSubmit.setOnClickListener(
                v -> submitAnswer()
        );

        btnNext.setOnClickListener(
                v -> nextQuestion()
        );
    }

    private void showQuestion() {

        List<Question> questions =
                quiz.getQuestions();

        if (currentQuestionIndex >= questions.size()) {

            showFinalResult();

            return;
        }

        Question question =
                questions.get(
                        currentQuestionIndex
                );

        answerSubmitted = false;

        tvQuestionNumber.setText(
                "Question "
                        + (currentQuestionIndex + 1)
                        + " of "
                        + questions.size()
        );

        tvQuestion.setText(
                question.getText()
        );

        List<String> options =
                question.getOptions();

        radioGroupOptions.clearCheck();

        radioOption1.setEnabled(true);
        radioOption2.setEnabled(true);
        radioOption3.setEnabled(true);
        radioOption4.setEnabled(true);

        radioOption1.setText(
                options.size() > 0
                        ? options.get(0)
                        : ""
        );

        radioOption2.setText(
                options.size() > 1
                        ? options.get(1)
                        : ""
        );

        radioOption3.setText(
                options.size() > 2
                        ? options.get(2)
                        : ""
        );

        radioOption4.setText(
                options.size() > 3
                        ? options.get(3)
                        : ""
        );

        btnSubmit.setVisibility(
                android.view.View.VISIBLE
        );

        btnSubmit.setEnabled(true);

        btnNext.setVisibility(
                android.view.View.GONE
        );

        layoutFeedback.setVisibility(
                android.view.View.GONE
        );
    }

    private void submitAnswer() {

        if (answerSubmitted) {
            return;
        }

        Question question =
                quiz.getQuestions()
                        .get(currentQuestionIndex);

        int selectedOptionIndex =
                getSelectedOptionIndex();

        if (selectedOptionIndex == -1) {

            Toast.makeText(
                    this,
                    "Please select an answer",
                    Toast.LENGTH_SHORT
            ).show();

            return;
        }

        answerSubmitted = true;

        int correctIndex =
                question.getCorrectOptionIndex();

        boolean isCorrect =
                selectedOptionIndex == correctIndex;

        if (isCorrect) {
            score++;
        }

        String selectedAnswer =
                question.getOptions()
                        .get(selectedOptionIndex);

        String correctAnswer =
                question.getOptions()
                        .get(correctIndex);

        // Disable options after submission

        radioOption1.setEnabled(false);
        radioOption2.setEnabled(false);
        radioOption3.setEnabled(false);
        radioOption4.setEnabled(false);

        btnSubmit.setEnabled(false);

        // Result

        if (isCorrect) {

            tvResult.setText(
                    "✓ Correct!"
            );

        } else {

            tvResult.setText(
                    "✗ Incorrect"
            );
        }

        tvYourAnswer.setText(
                "Your answer: "
                        + selectedAnswer
        );

        tvCorrectAnswer.setText(
                "Correct answer: "
                        + correctAnswer
        );

        String explanation =
                question.getExplanation();

        if (explanation == null
                || explanation.trim().isEmpty()) {

            explanation =
                    "No explanation was provided by the AI.";
        }

        tvExplanation.setText(
                "Explanation:\n"
                        + explanation
        );

        layoutFeedback.setVisibility(
                android.view.View.VISIBLE
        );

        btnNext.setVisibility(
                android.view.View.VISIBLE
        );

        if (currentQuestionIndex
                == quiz.getQuestions().size() - 1) {

            btnNext.setText(
                    "FINISH QUIZ"
            );

        } else {

            btnNext.setText(
                    "NEXT QUESTION"
            );
        }
    }

    private int getSelectedOptionIndex() {

        int checkedId =
                radioGroupOptions
                        .getCheckedRadioButtonId();

        if (checkedId == R.id.radioOption1) {
            return 0;
        }

        if (checkedId == R.id.radioOption2) {
            return 1;
        }

        if (checkedId == R.id.radioOption3) {
            return 2;
        }

        if (checkedId == R.id.radioOption4) {
            return 3;
        }

        return -1;
    }

    private void nextQuestion() {

        currentQuestionIndex++;

        if (currentQuestionIndex
                < quiz.getQuestions().size()) {

            showQuestion();

        } else {

            showFinalResult();
        }
    }

    private void showFinalResult() {

        int total =
                quiz.getQuestions().size();

        tvQuizTitle.setText(
                "Quiz Completed"
        );

        tvQuestionNumber.setText(
                "Final Result"
        );

        tvQuestion.setText(
                "Your Score: "
                        + score
                        + " / "
                        + total
        );

        radioGroupOptions.setVisibility(
                android.view.View.GONE
        );

        btnSubmit.setVisibility(
                android.view.View.GONE
        );

        layoutFeedback.setVisibility(
                android.view.View.VISIBLE
        );

        tvResult.setText(
                score == total
                        ? "Excellent! All answers are correct."
                        : "Quiz completed!"
        );

        tvYourAnswer.setText(
                "Score: "
                        + score
                        + " / "
                        + total
        );

        tvCorrectAnswer.setText(
                "Percentage: "
                        + calculatePercentage()
                        + "%"
        );

        tvExplanation.setText(
                "You can generate another AI quiz to continue practicing."
        );

        btnNext.setText(
                "CLOSE"
        );

        btnNext.setVisibility(
                android.view.View.VISIBLE
        );

        btnNext.setOnClickListener(
                v -> finish()
        );
    }

    private int calculatePercentage() {

        int total =
                quiz.getQuestions().size();

        if (total == 0) {
            return 0;
        }

        return (score * 100) / total;
    }
}