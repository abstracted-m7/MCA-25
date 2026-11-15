package com.example.quizmania.repository;

import android.util.Log;

import com.example.quizmania.model.LearningProgress;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.RevisionAttempt;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.model.WrongAnswer;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AdaptiveLearningRepository {
    private static final String TAG = "AdaptiveLearningRepo";
    private final FirebaseFirestore db;

    public interface LearningProgressCallback {
        void onSuccess(LearningProgress progress);
        void onFailure(String error);
    }

    public interface AllProgressCallback {
        void onSuccess(List<LearningProgress> list);
        void onFailure(String error);
    }

    public interface RevisionQuizCallback {
        void onSuccess(RevisionQuiz revisionQuiz);
        void onFailure(String error);
    }

    public interface RevisionListCallback {
        void onSuccess(List<RevisionQuiz> quizzes);
        void onFailure(String error);
    }

    public interface MistakesCallback {
        void onSuccess(List<WrongAnswer> mistakes);
        void onFailure(String error);
    }

    public interface ActionCallback {
        void onSuccess();
        void onFailure(String error);
    }

    public interface SubmitQuizCallback {
        void onSuccess(QuizAttempt attempt, boolean revisionTriggered, RevisionQuiz generatedRevision);
        void onFailure(String error);
    }

    public AdaptiveLearningRepository() {
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * Records a completed quiz attempt, logs all wrong answers, updates the 5-attempt counter,
     * and if 5 attempts are completed for this subject, automatically triggers a Revision Quiz!
     */
    public void recordQuizSubmission(QuizAttempt attempt,
                                     List<WrongAnswer> wrongAnswers,
                                     SubmitQuizCallback callback) {
        if (attempt.getId() == null || attempt.getId().isEmpty()) {
            attempt.setId(db.collection("quizAttempts").document().getId());
        }

        WriteBatch batch = db.batch();

        // 1. Save QuizAttempt
        DocumentReference attemptRef = db.collection("quizAttempts").document(attempt.getId());
        batch.set(attemptRef, attempt);

        // 2. Save individual WrongAnswer events
        for (WrongAnswer wa : wrongAnswers) {
            if (wa.getId() == null || wa.getId().isEmpty()) {
                wa.setId(db.collection("wrongAnswers").document().getId());
            }
            wa.setAttemptId(attempt.getId());
            wa.setStudentId(attempt.getStudentId());
            wa.setRollNumber(attempt.getRollNumber());
            wa.setQuizId(attempt.getQuizId());
            wa.setSubjectId(attempt.getSubjectId());
            wa.setSubjectName(attempt.getSubjectName());
            wa.setCreatedAt(attempt.getCompletedAt());

            DocumentReference waRef = db.collection("wrongAnswers").document(wa.getId());
            batch.set(waRef, wa);
        }

        // Commit attempt and wrong answers
        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    // 3. Process subject 5-attempt progress
                    updateSubjectLearningProgress(attempt, callback);
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to record quiz submission", e);
                    callback.onFailure("Failed to save quiz attempt: " + e.getMessage());
                });
    }

    private void updateSubjectLearningProgress(QuizAttempt attempt, SubmitQuizCallback callback) {
        String studentId = attempt.getStudentId();
        String subjectId = attempt.getSubjectId();
        String progressDocId = studentId + "_" + subjectId;

        DocumentReference progressRef = db.collection("learningProgress").document(progressDocId);

        db.runTransaction(transaction -> {
            DocumentSnapshot snapshot = transaction.get(progressRef);
            int currentAttempts = 0;
            if (snapshot.exists()) {
                Long attempts = snapshot.getLong("completedAttempts");
                currentAttempts = attempts != null ? attempts.intValue() : 0;
            }

            int newAttempts = currentAttempts + 1;
            boolean shouldTriggerRevision = (newAttempts % 5 == 0);

            Map<String, Object> data = new HashMap<>();
            data.put("studentId", studentId);
            data.put("subjectId", subjectId);
            data.put("subjectName", attempt.getSubjectName());
            data.put("completedAttempts", newAttempts);
            data.put("pendingRevision", shouldTriggerRevision);
            data.put("updatedAt", System.currentTimeMillis());

            transaction.set(progressRef, data);
            return shouldTriggerRevision;
        }).addOnSuccessListener(shouldTriggerRevision -> {
            if (Boolean.TRUE.equals(shouldTriggerRevision)) {
                // Generate the Revision Batch
                generateRevisionBatch(attempt.getStudentId(), attempt.getRollNumber(),
                        attempt.getSubjectId(), attempt.getSubjectName(), new RevisionQuizCallback() {
                            @Override
                            public void onSuccess(RevisionQuiz revisionQuiz) {
                                callback.onSuccess(attempt, true, revisionQuiz);
                            }

                            @Override
                            public void onFailure(String error) {
                                // Still acknowledge attempt success even if revision generation has a hitch
                                callback.onSuccess(attempt, false, null);
                            }
                        });
            } else {
                callback.onSuccess(attempt, false, null);
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Failed updating learning progress", e);
            callback.onSuccess(attempt, false, null);
        });
    }

    /**
     * Generates a personalized revision quiz based on the student's last 5 completed attempts for the subject.
     * Deduplicates questions using questionId and ranks weak topics.
     */
    public void generateRevisionBatch(String studentId, String rollNumber,
                                      String subjectId, String subjectName,
                                      RevisionQuizCallback callback) {
        // Query attempts in memory to avoid composite index requirements
        db.collection("quizAttempts")
                .whereEqualTo("studentId", studentId)
                .get()
                .addOnSuccessListener(attemptsSnapshot -> {
                    List<QuizAttempt> allAttempts = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : attemptsSnapshot) {
                        QuizAttempt a = doc.toObject(QuizAttempt.class);
                        if (a.getId() == null) a.setId(doc.getId());
                        allAttempts.add(a);
                    }

                    // Sort descending by completion time in memory
                    Collections.sort(allAttempts, (a, b) -> Long.compare(b.getCompletedAt(), a.getCompletedAt()));

                    List<String> sourceAttemptIds = new ArrayList<>();
                    for (QuizAttempt a : allAttempts) {
                        if (subjectId == null || subjectId.isEmpty() || subjectId.equalsIgnoreCase(a.getSubjectId())) {
                            sourceAttemptIds.add(a.getId());
                            if (sourceAttemptIds.size() >= 5) break;
                        }
                    }

                    // Fallback to any recent attempts if fewer than 5 in specific subject
                    if (sourceAttemptIds.isEmpty()) {
                        for (QuizAttempt a : allAttempts) {
                            sourceAttemptIds.add(a.getId());
                            if (sourceAttemptIds.size() >= 5) break;
                        }
                    }

                    // Retrieve wrong answers from database
                    db.collection("wrongAnswers")
                            .whereEqualTo("studentId", studentId)
                            .get()
                            .addOnSuccessListener(wrongAnswersSnapshot -> {
                                Map<String, Question> uniqueQuestions = new LinkedHashMap<>();
                                Map<String, Integer> topicMistakeCount = new HashMap<>();

                                for (QueryDocumentSnapshot doc : wrongAnswersSnapshot) {
                                    WrongAnswer wa = doc.toObject(WrongAnswer.class);
                                    boolean matchesAttempt = wa.getAttemptId() != null && sourceAttemptIds.contains(wa.getAttemptId());
                                    boolean matchesSubject = subjectId != null && subjectId.equalsIgnoreCase(wa.getSubjectId());

                                    if (matchesAttempt || matchesSubject || sourceAttemptIds.isEmpty()) {
                                        String qId = wa.getQuestionId() != null ? wa.getQuestionId() : wa.getId();
                                        if (qId == null && wa.getQuestionText() != null) {
                                            qId = String.valueOf(wa.getQuestionText().hashCode());
                                        }

                                        if (qId != null && !uniqueQuestions.containsKey(qId)) {
                                            Question q = new Question();
                                            q.setId(qId);
                                            q.setText(wa.getQuestionText());
                                            q.setOptions(wa.getOptions());
                                            q.setCorrectOptionIndex(wa.getCorrectOptionIndex());
                                            q.setCorrectAnswer(wa.getCorrectAnswer());
                                            q.setExplanation(wa.getExplanation());
                                            q.setSubjectId(wa.getSubjectId() != null ? wa.getSubjectId() : subjectId);
                                            q.setTopicName(wa.getTopicName() != null ? wa.getTopicName() : "Revision Topic");
                                            uniqueQuestions.put(qId, q);
                                        }

                                        // Count topic mistakes
                                        String topic = wa.getTopicName() != null ? wa.getTopicName() : "General";
                                        topicMistakeCount.put(topic, topicMistakeCount.getOrDefault(topic, 0) + 1);
                                    }
                                }

                                if (uniqueQuestions.isEmpty()) {
                                    // If student had 100% score on attempts, pick questions from question bank for practice
                                    pickBackupQuestionsForRevision(studentId, rollNumber, subjectId, subjectName, sourceAttemptIds, callback);
                                    return;
                                }

                                List<Question> revisionQuestions = new ArrayList<>(uniqueQuestions.values());
                                List<String> weakTopics = new ArrayList<>(topicMistakeCount.keySet());

                                createAndSaveRevisionQuiz(studentId, rollNumber, subjectId, subjectName,
                                        sourceAttemptIds, revisionQuestions, weakTopics, "QUESTION_BANK", callback);
                            })
                            .addOnFailureListener(e -> callback.onFailure("Failed retrieving wrong answers: " + e.getMessage()));
                })
                .addOnFailureListener(e -> callback.onFailure("Failed querying attempts: " + e.getMessage()));
    }

    private void pickBackupQuestionsForRevision(String studentId, String rollNumber,
                                               String subjectId, String subjectName,
                                               List<String> sourceAttemptIds,
                                               RevisionQuizCallback callback) {
        new QuestionRepository().fetchQuestionsBySubject(subjectId, new QuestionRepository.QuestionsCallback() {
            @Override
            public void onSuccess(List<Question> questions) {
                List<Question> subset = questions.size() > 5 ? questions.subList(0, 5) : questions;
                createAndSaveRevisionQuiz(studentId, rollNumber, subjectId, subjectName,
                        sourceAttemptIds, subset, Collections.singletonList("Mastery Challenge"), "QUESTION_BANK", callback);
            }

            @Override
            public void onFailure(String error) {
                callback.onFailure("Failed loading revision questions: " + error);
            }
        });
    }

    private void createAndSaveRevisionQuiz(String studentId, String rollNumber,
                                          String subjectId, String subjectName,
                                          List<String> sourceAttemptIds,
                                          List<Question> questions,
                                          List<String> weakTopics,
                                          String revisionType,
                                          RevisionQuizCallback callback) {
        String revisionId = "REV_" + System.currentTimeMillis();

        RevisionQuiz revQuiz = new RevisionQuiz();
        revQuiz.setId(revisionId);
        revQuiz.setStudentId(studentId);
        revQuiz.setRollNumber(rollNumber);
        revQuiz.setSubjectId(subjectId);
        revQuiz.setSubjectName(subjectName);
        revQuiz.setSourceAttemptIds(sourceAttemptIds);
        revQuiz.setQuestions(questions);
        revQuiz.setWeakTopics(weakTopics);
        revQuiz.setRevisionType(revisionType);
        revQuiz.setStatus("READY");
        revQuiz.setCreatedAt(System.currentTimeMillis());

        db.collection("revisionQuizzes").document(revisionId)
                .set(revQuiz)
                .addOnSuccessListener(aVoid -> {
                    // Update progress document with batchId
                    String progressDocId = studentId + "_" + subjectId;
                    db.collection("learningProgress").document(progressDocId)
                            .update("currentBatchId", revisionId, "pendingRevision", true);
                    callback.onSuccess(revQuiz);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed saving revision quiz: " + e.getMessage()));
    }

    public void fetchLearningProgress(String studentId, String subjectId, LearningProgressCallback callback) {
        String progressDocId = studentId + "_" + subjectId;
        db.collection("learningProgress").document(progressDocId)
                .get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        LearningProgress lp = doc.toObject(LearningProgress.class);
                        callback.onSuccess(lp);
                    } else {
                        // Default progress
                        LearningProgress lp = new LearningProgress(studentId, subjectId, "Subject");
                        callback.onSuccess(lp);
                    }
                })
                .addOnFailureListener(e -> callback.onFailure("Failed loading progress: " + e.getMessage()));
    }

    public void fetchAllStudentProgress(String studentId, AllProgressCallback callback) {
        db.collection("learningProgress")
                .whereEqualTo("studentId", studentId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<LearningProgress> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        list.add(doc.toObject(LearningProgress.class));
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed loading progress list: " + e.getMessage()));
    }

    public void fetchPendingRevisionQuizzes(String studentId, RevisionListCallback callback) {
        db.collection("revisionQuizzes")
                .whereEqualTo("studentId", studentId)
                .whereEqualTo("status", "READY")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<RevisionQuiz> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        RevisionQuiz rq = doc.toObject(RevisionQuiz.class);
                        if (rq.getId() == null) rq.setId(doc.getId());
                        list.add(rq);
                    }
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed loading revision quizzes: " + e.getMessage()));
    }

    public void fetchStudentMistakes(String studentId, MistakesCallback callback) {
        db.collection("wrongAnswers")
                .whereEqualTo("studentId", studentId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<WrongAnswer> list = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        WrongAnswer wa = doc.toObject(WrongAnswer.class);
                        if (wa.getId() == null) wa.setId(doc.getId());
                        list.add(wa);
                    }
                    // Sort descending by creation date in memory to avoid composite index requirement
                    Collections.sort(list, (a, b) -> Long.compare(b.getCreatedAt(), a.getCreatedAt()));
                    callback.onSuccess(list);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed loading mistakes: " + e.getMessage()));
    }

    /**
     * Submits a revision quiz attempt, updates question mastery, and marks revision quiz as completed.
     */
    public void recordRevisionAttempt(RevisionAttempt attempt,
                                      Map<String, Boolean> questionResults,
                                      ActionCallback callback) {
        if (attempt.getId() == null || attempt.getId().isEmpty()) {
            attempt.setId(db.collection("revisionAttempts").document().getId());
        }

        WriteBatch batch = db.batch();
        DocumentReference attemptRef = db.collection("revisionAttempts").document(attempt.getId());
        batch.set(attemptRef, attempt);

        // Mark RevisionQuiz as COMPLETED
        DocumentReference quizRef = db.collection("revisionQuizzes").document(attempt.getRevisionId());
        batch.update(quizRef, "status", "COMPLETED", "completedAt", System.currentTimeMillis());

        // Update learningProgress pendingRevision = false
        String progressDocId = attempt.getStudentId() + "_" + attempt.getSubjectId();
        DocumentReference progressRef = db.collection("learningProgress").document(progressDocId);
        batch.update(progressRef, "pendingRevision", false);

        batch.commit()
                .addOnSuccessListener(aVoid -> {
                    // Update mastery for each question
                    updateQuestionMastery(attempt.getStudentId(), questionResults, callback);
                })
                .addOnFailureListener(e -> callback.onFailure("Failed saving revision attempt: " + e.getMessage()));
    }

    private void updateQuestionMastery(String studentId, Map<String, Boolean> questionResults, ActionCallback callback) {
        if (questionResults == null || questionResults.isEmpty()) {
            callback.onSuccess();
            return;
        }

        for (Map.Entry<String, Boolean> entry : questionResults.entrySet()) {
            String questionId = entry.getKey();
            boolean answeredCorrect = entry.getValue();

            db.collection("wrongAnswers")
                    .whereEqualTo("studentId", studentId)
                    .whereEqualTo("questionId", questionId)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            WrongAnswer wa = doc.toObject(WrongAnswer.class);
                            int currentCorrect = wa.getMasteryCorrectCount();
                            if (answeredCorrect) {
                                currentCorrect++;
                            }

                            String newStatus = "Weak";
                            if (currentCorrect >= 2) {
                                newStatus = "Mastered";
                            } else if (currentCorrect == 1) {
                                newStatus = "Practicing";
                            }

                            doc.getReference().update("masteryCorrectCount", currentCorrect, "masteryStatus", newStatus);
                        }
                    });
        }
        callback.onSuccess();
    }
}
