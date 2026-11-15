package com.example.quizmania.model;

import java.io.Serializable;

public class Subject implements Serializable {
    private String subjectId;
    private String subjectName;
    private String description;
    private String createdBy;
    private String status; // "ACTIVE" or "ARCHIVED"
    private int questionCount;
    private int quizCount;

    public Subject() {
        // Required for Firestore
    }

    public Subject(String subjectId, String subjectName, String description, String createdBy, String status) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.description = description;
        this.createdBy = createdBy;
        this.status = status;
    }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getQuestionCount() { return questionCount; }
    public void setQuestionCount(int questionCount) { this.questionCount = questionCount; }

    public int getQuizCount() { return quizCount; }
    public void setQuizCount(int quizCount) { this.quizCount = quizCount; }

    @Override
    public String toString() {
        return subjectName != null ? subjectName : "";
    }
}
