package com.example.quizmania.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.repository.AiQuizRepository;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class AiQuizViewModel extends ViewModel {

    private final AiQuizRepository repository;

    private final CompositeDisposable disposables =
            new CompositeDisposable();

    private final MutableLiveData<String> generationStatus =
            new MutableLiveData<>();

    private final MutableLiveData<Boolean> generationSuccess =
            new MutableLiveData<>();

    private final MutableLiveData<Quiz> generatedQuiz =
            new MutableLiveData<>();

    public AiQuizViewModel() {

        repository =
                new AiQuizRepository();
    }

    public void generateAiQuiz(
            String topic,
            int numQuestions,
            String model) {

        generationStatus.setValue(
                "Generating quiz..."
        );

        generationSuccess.setValue(
                null
        );

        disposables.add(

                repository.generateQuiz(
                                topic,
                                numQuestions,
                                model
                        )

                        .subscribeOn(
                                Schedulers.io()
                        )

                        .observeOn(
                                AndroidSchedulers.mainThread()
                        )

                        .subscribe(

                                // =========================
                                // SUCCESS / HTTP RESPONSE
                                // =========================

                                response -> {

                                    try {

                                        // -------------------------
                                        // Check HTTP status
                                        // -------------------------

                                        if (!response.isSuccessful()) {

                                            String errorMessage =
                                                    "HTTP "
                                                            + response.code();

                                            if (response.errorBody()
                                                    != null) {

                                                String errorBody =
                                                        response
                                                                .errorBody()
                                                                .string();

                                                if (!errorBody.isEmpty()) {

                                                    errorMessage +=
                                                            "\n"
                                                                    + errorBody;
                                                }
                                            }

                                            throw new Exception(
                                                    errorMessage
                                            );
                                        }

                                        // -------------------------
                                        // Get response body
                                        // -------------------------

                                        JsonObject result =
                                                response.body();

                                        if (result == null) {

                                            throw new Exception(
                                                    "Empty response body"
                                            );
                                        }

                                        // -------------------------
                                        // Extract quiz JSON
                                        // -------------------------

                                        JsonObject quizJson =
                                                AiQuizRepository
                                                        .extractQuizJson(
                                                                result
                                                        );

                                        // -------------------------
                                        // JSON → Quiz object
                                        // -------------------------

                                        Quiz quiz =
                                                new Gson()
                                                        .fromJson(
                                                                quizJson,
                                                                Quiz.class
                                                        );

                                        if (quiz == null) {

                                            throw new Exception(
                                                    "Quiz is empty"
                                            );
                                        }

                                        // -------------------------
                                        // Get questions
                                        // -------------------------

                                        List<Question> questions =
                                                quiz.getQuestions();

                                        if (questions == null
                                                || questions.isEmpty()) {

                                            throw new Exception(
                                                    "AI returned no questions"
                                            );
                                        }

                                        // -------------------------
                                        // Validate questions
                                        // -------------------------

                                        for (int i = 0;
                                             i < questions.size();
                                             i++) {

                                            Question question =
                                                    questions.get(i);

                                            // Generate ID if missing
                                            if (question.getId() == null
                                                    || question.getId()
                                                    .trim()
                                                    .isEmpty()) {

                                                question.setId(
                                                        "q" + (i + 1)
                                                );
                                            }

                                            // Check question text
                                            if (question.getText() == null
                                                    || question.getText()
                                                    .trim()
                                                    .isEmpty()) {

                                                throw new Exception(
                                                        "Question "
                                                                + (i + 1)
                                                                + " has no text"
                                                );
                                            }

                                            // Check options
                                            if (question.getOptions()
                                                    == null
                                                    || question
                                                    .getOptions()
                                                    .size() != 4) {

                                                throw new Exception(
                                                        "Question "
                                                                + (i + 1)
                                                                + " must have exactly 4 options"
                                                );
                                            }

                                            // Check correct answer index
                                            int correctIndex =
                                                    question
                                                            .getCorrectOptionIndex();

                                            if (correctIndex < 0
                                                    || correctIndex > 3) {

                                                throw new Exception(
                                                        "Invalid correct answer in question "
                                                                + (i + 1)
                                                );
                                            }
                                        }

                                        // -------------------------
                                        // Quiz successfully created
                                        // -------------------------

                                        generatedQuiz.setValue(
                                                quiz
                                        );

                                        generationStatus.setValue(
                                                "Quiz generated successfully"
                                        );

                                        generationSuccess.setValue(
                                                true
                                        );

                                    } catch (Exception e) {

                                        generationStatus.setValue(
                                                "Failed: "
                                                        + e.getMessage()
                                        );

                                        generationSuccess.setValue(
                                                false
                                        );
                                    }
                                },

                                // =========================
                                // NETWORK ERROR
                                // =========================

                                error -> {

                                    generationStatus.setValue(
                                            "Generation failed: "
                                                    + error.getMessage()
                                    );

                                    generationSuccess.setValue(
                                            false
                                    );
                                }
                        )
        );
    }

    public LiveData<String> getGenerationStatus() {

        return generationStatus;
    }

    public LiveData<Boolean> getGenerationSuccess() {

        return generationSuccess;
    }

    public LiveData<Quiz> getGeneratedQuiz() {

        return generatedQuiz;
    }

    @Override
    protected void onCleared() {

        super.onCleared();

        disposables.clear();
    }
}