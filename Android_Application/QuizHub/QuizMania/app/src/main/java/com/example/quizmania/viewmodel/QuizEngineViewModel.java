package com.example.quizmania.viewmodel;

import android.os.CountDownTimer;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;

public class QuizEngineViewModel extends ViewModel {

    private static final long QUESTION_TIME_MS = 30000; // 30 seconds

    private final MutableLiveData<Quiz> quizLiveData =
            new MutableLiveData<>();

    private final MutableLiveData<Integer> questionIndex =
            new MutableLiveData<>(0);

    private final MutableLiveData<Integer> selectedOption =
            new MutableLiveData<>(-1);

    private final MutableLiveData<Long> timeRemaining =
            new MutableLiveData<>(QUESTION_TIME_MS);

    private final MutableLiveData<Integer> scoreLiveData =
            new MutableLiveData<>(0);

    private final MutableLiveData<Boolean> quizFinished =
            new MutableLiveData<>(false);

    private CountDownTimer timer;


    // ---------------------------------------------------------
    // GETTERS
    // ---------------------------------------------------------

    public LiveData<Quiz> getQuiz() {
        return quizLiveData;
    }

    public LiveData<Integer> getQuestionIndex() {
        return questionIndex;
    }

    public LiveData<Integer> getSelectedOption() {
        return selectedOption;
    }

    public LiveData<Long> getTimeRemaining() {
        return timeRemaining;
    }

    public LiveData<Integer> getScore() {
        return scoreLiveData;
    }

    public LiveData<Boolean> getQuizFinished() {
        return quizFinished;
    }


    // ---------------------------------------------------------
    // START QUIZ
    // ---------------------------------------------------------

    public void startQuiz(Quiz quiz) {

        if (quiz == null) {
            return;
        }

        if (quiz.getQuestions() == null ||
                quiz.getQuestions().isEmpty()) {
            return;
        }

        // Cancel old timer
        if (timer != null) {
            timer.cancel();
        }

        quizLiveData.setValue(quiz);
        questionIndex.setValue(0);
        selectedOption.setValue(-1);
        scoreLiveData.setValue(0);
        quizFinished.setValue(false);
        timeRemaining.setValue(QUESTION_TIME_MS);

        startTimer();
    }


    // ---------------------------------------------------------
    // TIMER
    // ---------------------------------------------------------

    private void startTimer() {

        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(
                QUESTION_TIME_MS,
                1000
        ) {

            @Override
            public void onTick(long millisUntilFinished) {

                timeRemaining.postValue(
                        millisUntilFinished
                );
            }

            @Override
            public void onFinish() {

                timeRemaining.postValue(0L);

                // Time finished.
                // Treat current question as unanswered.
                moveToNext();
            }

        }.start();
    }


    // ---------------------------------------------------------
    // SELECT OPTION
    // ---------------------------------------------------------

    public void selectOption(int optionIdx) {

        Quiz quiz = quizLiveData.getValue();
        Integer index = questionIndex.getValue();

        if (quiz == null || index == null) {
            return;
        }

        if (quiz.getQuestions() == null ||
                index < 0 ||
                index >= quiz.getQuestions().size()) {
            return;
        }

        Question question =
                quiz.getQuestions().get(index);

        if (question.getOptions() == null) {
            return;
        }

        if (optionIdx < 0 ||
                optionIdx >= question.getOptions().size()) {
            return;
        }

        selectedOption.setValue(optionIdx);
    }


    // ---------------------------------------------------------
    // SUBMIT ANSWER
    // ---------------------------------------------------------

    public void submitAnswer() {

        Quiz quiz = quizLiveData.getValue();
        Integer index = questionIndex.getValue();
        Integer selected = selectedOption.getValue();

        if (quiz == null ||
                index == null ||
                selected == null) {
            return;
        }

        if (quiz.getQuestions() == null ||
                index < 0 ||
                index >= quiz.getQuestions().size()) {
            return;
        }

        Question question =
                quiz.getQuestions().get(index);

        // Check answer
        if (selected >= 0 &&
                selected == question.getCorrectOptionIndex()) {

            int currentScore =
                    scoreLiveData.getValue() != null
                            ? scoreLiveData.getValue()
                            : 0;

            scoreLiveData.setValue(
                    currentScore + 1
            );
        }

        // Move to next question
        moveToNext();
    }


    // ---------------------------------------------------------
    // MOVE TO NEXT QUESTION
    // ---------------------------------------------------------

    private void moveToNext() {

        Quiz quiz = quizLiveData.getValue();
        Integer index = questionIndex.getValue();

        if (quiz == null || index == null) {
            return;
        }

        if (quiz.getQuestions() == null ||
                quiz.getQuestions().isEmpty()) {
            finishQuiz();
            return;
        }

        int nextIndex = index + 1;

        // More questions available
        if (nextIndex < quiz.getQuestions().size()) {

            questionIndex.setValue(nextIndex);

            selectedOption.setValue(-1);

            timeRemaining.setValue(
                    QUESTION_TIME_MS
            );

            startTimer();

        } else {

            // No more questions
            finishQuiz();
        }
    }


    // ---------------------------------------------------------
    // FINISH QUIZ
    // ---------------------------------------------------------

    private void finishQuiz() {

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        timeRemaining.setValue(0L);

        quizFinished.setValue(true);
    }


    // ---------------------------------------------------------
    // RESTART QUIZ
    // ---------------------------------------------------------

    public void restartQuiz() {

        Quiz quiz = quizLiveData.getValue();

        if (quiz == null) {
            return;
        }

        startQuiz(quiz);
    }


    // ---------------------------------------------------------
    // GET CURRENT QUESTION
    // ---------------------------------------------------------

    public Question getCurrentQuestion() {

        Quiz quiz = quizLiveData.getValue();
        Integer index = questionIndex.getValue();

        if (quiz == null ||
                index == null ||
                quiz.getQuestions() == null) {
            return null;
        }

        if (index < 0 ||
                index >= quiz.getQuestions().size()) {
            return null;
        }

        return quiz.getQuestions().get(index);
    }


    // ---------------------------------------------------------
    // CLEAN UP
    // ---------------------------------------------------------

    @Override
    protected void onCleared() {

        super.onCleared();

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}