package com.example.quizmania.repository;

import android.util.Log;

import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.User;
import com.example.quizmania.model.WrongAnswer;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentRepository {
    private static final String TAG = "StudentRepository";
    private final FirebaseFirestore db;

    @FunctionalInterface
    public interface StudentsCallback {
        void onResult(List<User> students);
    }

    @FunctionalInterface
    public interface StudentDetailsCallback {
        void onResult(User student, List<QuizAttempt> attempts, List<WrongAnswer> mistakes);
    }

    @FunctionalInterface
    public interface ClassAnalyticsCallback {
        void onResult(Map<String, Object> analytics);
    }

    public StudentRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void fetchAllStudents(StudentsCallback callback) {
        db.collection("users")
                .whereEqualTo("role", "STUDENT")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<User> students = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        User u = doc.toObject(User.class);
                        if (u.getId() == null) u.setId(doc.getId());
                        students.add(u);
                    }
                    callback.onResult(students);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to load students", e);
                    callback.onResult(new ArrayList<>());
                });
    }

    public void searchStudentByRollNumber(String rollNumberQuery, StudentsCallback callback) {
        fetchAllStudents(students -> {
            if (rollNumberQuery == null || rollNumberQuery.trim().isEmpty()) {
                callback.onResult(students);
                return;
            }
            String q = rollNumberQuery.trim().toLowerCase();
            List<User> filtered = new ArrayList<>();
            for (User s : students) {
                if ((s.getRollNumber() != null && s.getRollNumber().toLowerCase().contains(q)) ||
                    (s.getName() != null && s.getName().toLowerCase().contains(q))) {
                    filtered.add(s);
                }
            }
            callback.onResult(filtered);
        });
    }

    public void fetchStudentHistory(String studentId, StudentDetailsCallback callback) {
        db.collection("users").document(studentId).get()
                .addOnSuccessListener(userDoc -> {
                    User student = userDoc.exists() ? userDoc.toObject(User.class) : new User();
                    if (student != null && student.getId() == null) student.setId(studentId);

                    db.collection("quizAttempts")
                            .whereEqualTo("studentId", studentId)
                            .get()
                            .addOnSuccessListener(attemptsSnap -> {
                                List<QuizAttempt> attempts = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : attemptsSnap) {
                                    QuizAttempt qa = doc.toObject(QuizAttempt.class);
                                    if (qa.getId() == null) qa.setId(doc.getId());
                                    attempts.add(qa);
                                }
                                Collections.sort(attempts, (a, b) -> Long.compare(b.getCompletedAt(), a.getCompletedAt()));

                                db.collection("wrongAnswers")
                                        .whereEqualTo("studentId", studentId)
                                        .get()
                                        .addOnSuccessListener(mistakesSnap -> {
                                            List<WrongAnswer> mistakes = new ArrayList<>();
                                            for (QueryDocumentSnapshot doc : mistakesSnap) {
                                                WrongAnswer wa = doc.toObject(WrongAnswer.class);
                                                if (wa.getId() == null) wa.setId(doc.getId());
                                                mistakes.add(wa);
                                            }
                                            Collections.sort(mistakes, (a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
                                            callback.onResult(student, attempts, mistakes);
                                        })
                                        .addOnFailureListener(e -> {
                                            Log.e(TAG, "Failed to load mistakes for student", e);
                                            callback.onResult(student, attempts, new ArrayList<>());
                                        });
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Failed to load attempts for student", e);
                                callback.onResult(student, new ArrayList<>(), new ArrayList<>());
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to load user document", e);
                    callback.onResult(new User(), new ArrayList<>(), new ArrayList<>());
                });
    }

    public void fetchClassAnalytics(ClassAnalyticsCallback callback) {
        db.collection("quizAttempts").get()
                .addOnSuccessListener(attemptsSnap -> {
                    List<QuizAttempt> attempts = new ArrayList<>();
                    int totalQuestions = 0;
                    int totalCorrect = 0;

                    Map<String, int[]> subjectScoreMap = new HashMap<>(); // subject -> [totalScore, count]
                    Map<String, int[]> studentScoreMap = new HashMap<>(); // studentId -> [totalScore, count]

                    for (QueryDocumentSnapshot doc : attemptsSnap) {
                        QuizAttempt a = doc.toObject(QuizAttempt.class);
                        attempts.add(a);
                        totalQuestions += a.getTotalQuestions();
                        totalCorrect += a.getCorrectCount();

                        String subj = a.getSubjectName() != null ? a.getSubjectName() : "General";
                        int[] sStats = subjectScoreMap.getOrDefault(subj, new int[]{0, 0});
                        sStats[0] += a.getPercentage();
                        sStats[1] += 1;
                        subjectScoreMap.put(subj, sStats);

                        String sId = a.getStudentId() != null ? a.getStudentId() : "Unknown";
                        int[] uStats = studentScoreMap.getOrDefault(sId, new int[]{0, 0});
                        uStats[0] += a.getPercentage();
                        uStats[1] += 1;
                        studentScoreMap.put(sId, uStats);
                    }

                    Map<String, Object> result = new HashMap<>();
                    result.put("totalAttempts", attempts.size());
                    int overallAccuracy = totalQuestions > 0 ? (totalCorrect * 100) / totalQuestions : 0;
                    result.put("overallAccuracy", overallAccuracy);

                    // Subject averages
                    Map<String, Integer> subjectAverages = new HashMap<>();
                    for (Map.Entry<String, int[]> entry : subjectScoreMap.entrySet()) {
                        int avg = entry.getValue()[1] > 0 ? entry.getValue()[0] / entry.getValue()[1] : 0;
                        subjectAverages.put(entry.getKey(), avg);
                    }
                    result.put("subjectAverages", subjectAverages);

                    // Fetch mistake frequency across class
                    db.collection("wrongAnswers").get()
                            .addOnSuccessListener(mistakesSnap -> {
                                Map<String, Integer> frequentMistakes = new HashMap<>();
                                Map<String, Integer> weakTopics = new HashMap<>();

                                for (QueryDocumentSnapshot doc : mistakesSnap) {
                                    WrongAnswer wa = doc.toObject(WrongAnswer.class);
                                    String qText = wa.getQuestionText() != null ? wa.getQuestionText() : "Question";
                                    frequentMistakes.put(qText, frequentMistakes.getOrDefault(qText, 0) + 1);

                                    String topic = wa.getTopicName() != null ? wa.getTopicName() : "General";
                                    weakTopics.put(topic, weakTopics.getOrDefault(topic, 0) + 1);
                                }

                                result.put("frequentMistakes", frequentMistakes);
                                result.put("weakTopics", weakTopics);
                                callback.onResult(result);
                            })
                            .addOnFailureListener(e -> {
                                result.put("frequentMistakes", new HashMap<>());
                                result.put("weakTopics", new HashMap<>());
                                callback.onResult(result);
                            });
                })
                .addOnFailureListener(e -> {
                    Map<String, Object> empty = new HashMap<>();
                    empty.put("totalAttempts", 0);
                    empty.put("overallAccuracy", 0);
                    empty.put("subjectAverages", new HashMap<String, Integer>());
                    empty.put("frequentMistakes", new HashMap<String, Integer>());
                    empty.put("weakTopics", new HashMap<String, Integer>());
                    callback.onResult(empty);
                });
    }
}
