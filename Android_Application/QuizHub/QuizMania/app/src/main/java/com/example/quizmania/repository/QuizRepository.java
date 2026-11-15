package com.example.quizmania.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class QuizRepository {
    private static final String TAG = "QuizRepository";
    private final FirebaseFirestore db;

    public interface QuizzesCallback {
        void onSuccess(List<Quiz> quizzes);
        void onFailure(String error);
    }

    public interface QuizCallback {
        void onSuccess(Quiz quiz);
        void onFailure(String error);
    }

    public interface ActionCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public QuizRepository() {
        db = FirebaseFirestore.getInstance();
    }

    public void fetchQuizzes(MutableLiveData<List<Quiz>> quizzesLiveData) {
        db.collection("quizzes")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Quiz> quizzes = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Quiz q = doc.toObject(Quiz.class);
                        if (q.getId() == null) q.setId(doc.getId());
                        quizzes.add(q);
                    }
                    if (quizzes.isEmpty()) {
                        seedSampleQuizzes(quizzesLiveData);
                    } else {
                        quizzesLiveData.setValue(quizzes);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to fetch quizzes", e);
                });
    }

    public void fetchQuizzesBySubject(String subjectId, QuizzesCallback callback) {
        db.collection("quizzes")
                .whereEqualTo("subjectId", subjectId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Quiz> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Quiz q = doc.toObject(Quiz.class);
                        if (q.getId() == null) q.setId(doc.getId());
                        list.add(q);
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to load quizzes: " + e.getMessage()));
    }

    public void fetchQuizDetails(String quizId, QuizCallback callback) {
        db.collection("quizzes").document(quizId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        Quiz quiz = doc.toObject(Quiz.class);
                        if (quiz != null) {
                            if (quiz.getId() == null) quiz.setId(doc.getId());
                            // Fetch attached questions if question subcollection exists
                            fetchQuizSubcollectionQuestions(quiz, callback);
                        } else {
                            callback.onFailure("Could not parse quiz");
                        }
                    } else {
                        callback.onFailure("Quiz not found");
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Error loading quiz: " + e.getMessage()));
    }

    private void fetchQuizSubcollectionQuestions(Quiz quiz, QuizCallback callback) {
        if (quiz.getQuestions() != null && !quiz.getQuestions().isEmpty()) {
            callback.onSuccess(quiz);
            return;
        }
        db.collection("quizzes").document(quiz.getId()).collection("questions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Question> questions = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Question q = doc.toObject(Question.class);
                        if (q.getId() == null) q.setId(doc.getId());
                        questions.add(q);
                    }
                    if (!questions.isEmpty()) {
                        quiz.setQuestions(questions);
                    }
                    callback.onSuccess(quiz);
                })
                .addOnFailureListener(e -> callback.onSuccess(quiz)); // Fall back to quiz itself
    }

    public void saveQuiz(Quiz quiz, List<Question> questions, MutableLiveData<Boolean> successLiveData) {
        if (quiz.getId() == null || quiz.getId().isEmpty()) {
            quiz.setId(db.collection("quizzes").document().getId());
        }
        quiz.setQuestions(questions);

        db.collection("quizzes").document(quiz.getId())
                .set(quiz)
                .addOnSuccessListener(aVoid -> {
                    if (questions != null) {
                        for (Question q : questions) {
                            if (q.getId() == null) q.setId(db.collection("questions").document().getId());
                            db.collection("quizzes").document(quiz.getId())
                                    .collection("questions").document(q.getId())
                                    .set(q);
                        }
                    }
                    successLiveData.setValue(true);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to save quiz", e);
                    successLiveData.setValue(false);
                });
    }

    public void saveQuizWithCallback(Quiz quiz, List<Question> questions, ActionCallback callback) {
        if (quiz.getId() == null || quiz.getId().isEmpty()) {
            quiz.setId(db.collection("quizzes").document().getId());
        }
        quiz.setQuestions(questions);

        db.collection("quizzes").document(quiz.getId())
                .set(quiz)
                .addOnSuccessListener(aVoid -> {
                    if (questions != null) {
                        for (Question q : questions) {
                            if (q.getId() == null) q.setId(db.collection("questions").document().getId());
                            db.collection("quizzes").document(quiz.getId())
                                    .collection("questions").document(q.getId())
                                    .set(q);
                        }
                    }
                    callback.onSuccess();
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to create quiz: " + e.getMessage()));
    }

    public void deleteQuiz(String quizId, ActionCallback callback) {
        db.collection("quizzes").document(quizId)
                .delete()
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure("Failed to delete quiz: " + e.getMessage()));
    }

    private void seedSampleQuizzes(MutableLiveData<List<Quiz>> quizzesLiveData) {
        new QuestionRepository().fetchQuestionsBySubject("SUB_JAVA", new QuestionRepository.QuestionsCallback() {
            @Override
            public void onSuccess(List<Question> questions) {
                List<Quiz> seeded = new ArrayList<>();

                Quiz javaQuiz = new Quiz();
                javaQuiz.setId("QUIZ_JAVA_01");
                javaQuiz.setTitle("Java OOP & Core Principles");
                javaQuiz.setDescription("Test your mastery on OOP, Inheritance, Encapsulation and Polymorphism.");
                javaQuiz.setSubjectId("SUB_JAVA");
                javaQuiz.setSubjectName("Java Programming");
                javaQuiz.setDurationMinutes(10);
                javaQuiz.setDifficulty("Intermediate");
                javaQuiz.setTotalMarks(questions.size());
                javaQuiz.setStatus("PUBLISHED");
                javaQuiz.setQuestions(questions);

                db.collection("quizzes").document(javaQuiz.getId()).set(javaQuiz);
                for (Question q : questions) {
                    db.collection("quizzes").document(javaQuiz.getId()).collection("questions").document(q.getId()).set(q);
                }

                seeded.add(javaQuiz);
                quizzesLiveData.setValue(seeded);
            }

            @Override
            public void onFailure(String error) {
                quizzesLiveData.setValue(new ArrayList<>());
            }
        });
    }
}
