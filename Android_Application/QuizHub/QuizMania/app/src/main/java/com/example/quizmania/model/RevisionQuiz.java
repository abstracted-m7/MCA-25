package com.example.quizmania.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RevisionQuiz implements Serializable {
    private String id;
    private String studentId;
    private String rollNumber;
    private String subjectId;
    private String subjectName;
    private List<String> sourceAttemptIds;
    private List<String> questionIds;
    private List<Question> questions;
    private List<String> weakTopics;
    private String revisionType; // "QUESTION_BANK" or "AI_GENERATED"
    private String status; // "PENDING", "PROCESSING", "READY", "FAILED", "COMPLETED"
    private long createdAt;
    private long completedAt;

    public RevisionQuiz() {
        // Required for Firestore
        this.sourceAttemptIds = new ArrayList<>();
        this.questionIds = new ArrayList<>();
        this.questions = new ArrayList<>();
        this.weakTopics = new ArrayList<>();
        this.status = "READY";
        this.revisionType = "QUESTION_BANK";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public List<String> getSourceAttemptIds() { return sourceAttemptIds != null ? sourceAttemptIds : new ArrayList<>(); }
    public void setSourceAttemptIds(List<String> sourceAttemptIds) { this.sourceAttemptIds = sourceAttemptIds; }

    public List<String> getQuestionIds() { return questionIds != null ? questionIds : new ArrayList<>(); }
    public void setQuestionIds(List<String> questionIds) { this.questionIds = questionIds; }

    public List<Question> getQuestions() { return questions != null ? questions : new ArrayList<>(); }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
        if (questions != null) {
            List<String> ids = new ArrayList<>();
            for (Question q : questions) {
                if (q.getId() != null) ids.add(q.getId());
            }
            this.questionIds = ids;
        }
    }

    public List<String> getWeakTopics() { return weakTopics != null ? weakTopics : new ArrayList<>(); }
    public void setWeakTopics(List<String> weakTopics) { this.weakTopics = weakTopics; }

    public String getRevisionType() { return revisionType != null ? revisionType : "QUESTION_BANK"; }
    public void setRevisionType(String revisionType) { this.revisionType = revisionType; }

    public String getStatus() { return status != null ? status : "READY"; }
    public void setStatus(String status) { this.status = status; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public long getCompletedAt() { return completedAt; }
    public void setCompletedAt(long completedAt) { this.completedAt = completedAt; }
}
