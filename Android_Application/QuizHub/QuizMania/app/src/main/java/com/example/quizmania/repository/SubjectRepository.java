package com.example.quizmania.repository;

import android.util.Log;

import com.example.quizmania.model.Subject;
import com.example.quizmania.model.Topic;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    private static final String TAG = "SubjectRepository";
    private final FirebaseFirestore db;

    public interface SubjectsCallback {
        void onSuccess(List<Subject> subjects);
        void onFailure(String error);
    }

    public interface TopicsCallback {
        void onSuccess(List<Topic> topics);
        void onFailure(String error);
    }

    public interface ActionCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public SubjectRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void fetchSubjects(SubjectsCallback callback) {
        db.collection("subjects")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Subject> subjects = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Subject s = doc.toObject(Subject.class);
                        if (s.getSubjectId() == null) s.setSubjectId(doc.getId());
                        subjects.add(s);
                    }
                    if (subjects.isEmpty()) {
                        // Seed default initial subjects
                        seedDefaultSubjects(callback);
                    } else {
                        callback.onSuccess(subjects);
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error fetching subjects", e);
                    callback.onFailure("Failed to load subjects: " + e.getMessage());
                });
    }

    public void addSubject(Subject subject, ActionCallback callback) {
        if (subject.getSubjectId() == null || subject.getSubjectId().isEmpty()) {
            subject.setSubjectId(db.collection("subjects").document().getId());
        }
        db.collection("subjects").document(subject.getSubjectId())
                .set(subject)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure("Failed to add subject: " + e.getMessage()));
    }

    public void fetchTopics(String subjectId, TopicsCallback callback) {
        db.collection("topics")
                .whereEqualTo("subjectId", subjectId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Topic> topics = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Topic t = doc.toObject(Topic.class);
                        if (t.getTopicId() == null) t.setTopicId(doc.getId());
                        topics.add(t);
                    }
                    callback.onSuccess(topics);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to load topics: " + e.getMessage()));
    }

    public void addTopic(Topic topic, ActionCallback callback) {
        if (topic.getTopicId() == null || topic.getTopicId().isEmpty()) {
            topic.setTopicId(db.collection("topics").document().getId());
        }
        db.collection("topics").document(topic.getTopicId())
                .set(topic)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure("Failed to add topic: " + e.getMessage()));
    }

    private void seedDefaultSubjects(SubjectsCallback callback) {
        List<Subject> defaultSubjects = new ArrayList<>();

        Subject java = new Subject("SUB_JAVA", "Java Programming", "Core Java, OOP, Collections and JVM internals", "SYSTEM", "ACTIVE");
        Subject python = new Subject("SUB_PYTHON", "Python Programming", "Python basics, Functions, OOP and standard modules", "SYSTEM", "ACTIVE");
        Subject dbms = new Subject("SUB_DBMS", "Database Management System", "Relational database concepts, SQL, Normalization and Transactions", "SYSTEM", "ACTIVE");
        Subject dsa = new Subject("SUB_DSA", "Data Structures", "Arrays, Linked Lists, Stacks, Trees and Sorting algorithms", "SYSTEM", "ACTIVE");

        defaultSubjects.add(java);
        defaultSubjects.add(python);
        defaultSubjects.add(dbms);
        defaultSubjects.add(dsa);

        for (Subject s : defaultSubjects) {
            db.collection("subjects").document(s.getSubjectId()).set(s);
        }

        // Seed some initial topics for each
        seedTopicsForSubject("SUB_JAVA", new String[]{"OOP Concepts", "Exception Handling", "Collections Framework", "Multithreading"});
        seedTopicsForSubject("SUB_PYTHON", new String[]{"Basics & Data Types", "Functions & Lambda", "OOP in Python", "File & Exception Handling"});
        seedTopicsForSubject("SUB_DBMS", new String[]{"SQL Queries", "ER Modeling", "Normalization", "Transactions & ACID"});
        seedTopicsForSubject("SUB_DSA", new String[]{"Arrays & Strings", "Linked Lists", "Trees & Graphs", "Sorting & Searching"});

        callback.onSuccess(defaultSubjects);
    }

    private void seedTopicsForSubject(String subjectId, String[] topicNames) {
        for (String name : topicNames) {
            String topicId = db.collection("topics").document().getId();
            Topic t = new Topic(topicId, subjectId, name);
            db.collection("topics").document(topicId).set(t);
        }
    }
}
