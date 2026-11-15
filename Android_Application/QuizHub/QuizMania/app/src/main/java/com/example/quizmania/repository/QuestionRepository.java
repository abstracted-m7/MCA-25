package com.example.quizmania.repository;

import android.util.Log;

import com.example.quizmania.model.Question;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionRepository {
    private static final String TAG = "QuestionRepository";
    private final FirebaseFirestore db;

    public interface QuestionsCallback {
        void onSuccess(List<Question> questions);
        void onFailure(String error);
    }

    public interface ActionCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public QuestionRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void fetchAllQuestions(QuestionsCallback callback) {
        db.collection("questions")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Question> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Question q = doc.toObject(Question.class);
                        if (q.getId() == null) q.setId(doc.getId());
                        list.add(q);
                    }
                    if (list.isEmpty()) {
                        seedSampleQuestions(callback);
                    } else {
                        callback.onSuccess(list);
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to load questions: " + e.getMessage()));
    }

    public void fetchQuestionsBySubject(String subjectId, QuestionsCallback callback) {
        db.collection("questions")
                .whereEqualTo("subjectId", subjectId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Question> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Question q = doc.toObject(Question.class);
                        if (q.getId() == null) q.setId(doc.getId());
                        list.add(q);
                    }
                    if (list.isEmpty()) {
                        seedSampleQuestions(callback);
                    } else {
                        callback.onSuccess(list);
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Failed to load questions for subject: " + e.getMessage()));
    }

    public void addQuestion(Question question, ActionCallback callback) {
        if (question.getId() == null || question.getId().isEmpty()) {
            question.setId(db.collection("questions").document().getId());
        }
        if (question.getCreatedAt() == 0) {
            question.setCreatedAt(System.currentTimeMillis());
        }
        db.collection("questions").document(question.getId())
                .set(question)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure("Failed to add question: " + e.getMessage()));
    }

    private void seedSampleQuestions(QuestionsCallback callback) {
        List<Question> samples = new ArrayList<>();

        // Java OOP Questions
        Question q1 = new Question("Q_JAVA_101",
                "Which OOP concept enables hiding internal object details and protecting data integrity?",
                Arrays.asList("Encapsulation", "Inheritance", "Polymorphism", "Abstraction"),
                0,
                "Encapsulation is the bundling of data with methods operating on that data, restricting direct access to object components.");
        q1.setSubjectId("SUB_JAVA");
        q1.setTopicName("OOP Concepts");
        q1.setDifficulty("Beginner");

        Question q2 = new Question("Q_JAVA_102",
                "Which keyword in Java is used to prevent method overriding?",
                Arrays.asList("static", "final", "abstract", "synchronized"),
                1,
                "The 'final' keyword on a method prevents subclasses from overriding that method.");
        q2.setSubjectId("SUB_JAVA");
        q2.setTopicName("OOP Concepts");
        q2.setDifficulty("Intermediate");

        Question q3 = new Question("Q_JAVA_103",
                "Which interface is the root of the Java Collections Framework hierarchy for lists and sets?",
                Arrays.asList("Map", "Collection", "Iterable", "List"),
                1,
                "The Collection interface is the root interface in the collection hierarchy (above List, Set, and Queue).");
        q3.setSubjectId("SUB_JAVA");
        q3.setTopicName("Collections Framework");
        q3.setDifficulty("Intermediate");

        Question q4 = new Question("Q_JAVA_104",
                "What happens if an unhandled checked exception is thrown in a Java method?",
                Arrays.asList("Ignored at runtime", "Causes compilation failure unless declared in throws", "JVM terminates silently", "Converted to Error"),
                1,
                "Checked exceptions must either be caught in a try-catch block or declared in the method signature using the 'throws' keyword.");
        q4.setSubjectId("SUB_JAVA");
        q4.setTopicName("Exception Handling");
        q4.setDifficulty("Advanced");

        Question q5 = new Question("Q_JAVA_105",
                "Which method must be called to start thread execution in Java?",
                Arrays.asList("run()", "start()", "init()", "execute()"),
                1,
                "The start() method causes the thread to begin execution; the JVM calls the run method of this thread.");
        q5.setSubjectId("SUB_JAVA");
        q5.setTopicName("Multithreading");
        q5.setDifficulty("Intermediate");

        // Python Questions
        Question q6 = new Question("Q_PY_201",
                "What is the output of type([]) in Python 3?",
                Arrays.asList("<class 'tuple'>", "<class 'list'>", "<class 'array'>", "<class 'dict'>"),
                1,
                "In Python, square brackets define a list object whose type is <class 'list'>.");
        q6.setSubjectId("SUB_PYTHON");
        q6.setTopicName("Basics & Data Types");
        q6.setDifficulty("Beginner");

        Question q7 = new Question("Q_PY_202",
                "Which keyword is used to create an anonymous inline function in Python?",
                Arrays.asList("def", "anonymous", "lambda", "inline"),
                2,
                "The 'lambda' keyword creates small anonymous functions that take any number of arguments but only one expression.");
        q7.setSubjectId("SUB_PYTHON");
        q7.setTopicName("Functions & Lambda");
        q7.setDifficulty("Intermediate");

        // DBMS Questions
        Question q8 = new Question("Q_DBMS_301",
                "In ACID properties of database transactions, what does 'I' stand for?",
                Arrays.asList("Integrity", "Isolation", "Index", "Idempotence"),
                1,
                "Isolation ensures that concurrent execution of transactions leaves the database in the same state as if executed serially.");
        q8.setSubjectId("SUB_DBMS");
        q8.setTopicName("Transactions & ACID");
        q8.setDifficulty("Intermediate");

        Question q9 = new Question("Q_DBMS_302",
                "Which normal form eliminates transitive functional dependencies?",
                Arrays.asList("1NF", "2NF", "3NF", "BCNF"),
                2,
                "3NF (Third Normal Form) requires that every non-prime attribute is non-transitively dependent on every candidate key.");
        q9.setSubjectId("SUB_DBMS");
        q9.setTopicName("Normalization");
        q9.setDifficulty("Advanced");

        // DSA Questions
        Question q10 = new Question("Q_DSA_401",
                "What is the worst-case time complexity of standard Binary Search?",
                Arrays.asList("O(1)", "O(n)", "O(log n)", "O(n log n)"),
                2,
                "Binary search repeatedly divides the search interval in half, resulting in O(log n) time complexity.");
        q10.setSubjectId("SUB_DSA");
        q10.setTopicName("Sorting & Searching");
        q10.setDifficulty("Beginner");

        samples.add(q1);
        samples.add(q2);
        samples.add(q3);
        samples.add(q4);
        samples.add(q5);
        samples.add(q6);
        samples.add(q7);
        samples.add(q8);
        samples.add(q9);
        samples.add(q10);

        for (Question q : samples) {
            db.collection("questions").document(q.getId()).set(q);
        }

        callback.onSuccess(samples);
    }
}
