package com.example.quizmania.model;

import java.io.Serializable;

public class RevisionAttempt implements Serializable {
    private String id;
    private String revisionId;
    private String studentId;
    private String subjectId;
    private String subjectName;
    private int score;
    private int totalQuestions;
    private int correctCount;
    private int wrongCount;
    private int accuracyBefore; // % accuracy prior to revision
    private int accuracyAfter;  // % accuracy on revision
    private int improvementPoints; // e.g. +45%
    private long completedAt;

    public RevisionAttempt() {
        // Required for Firestore
    }

    public RevisionAttempt(String id, String revisionId, String studentId, String subjectId,
                           int score, int totalQuestions, int correctCount, int wrongCount,
                           int accuracyBefore, int accuracyAfter, long completedAt) {
        this.id = id;
        this.revisionId = revisionId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.score = score;
        this.totalQuestions = totalQuestions;
        this.correctCount = correctCount;
        this.wrongCount = wrongCount;
        this.accuracyBefore = accuracyBefore;
        this.accuracyAfter = accuracyAfter;
        this.improvementPoints = accuracyAfter - accuracyBefore;
        this.completedAt = completedAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getRevisionId() { return revisionId; }
    public void setRevisionId(String revisionId) { this.revisionId = revisionId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectCount() { return correctCount; }
    public void setCorrectCount(int correctCount) { this.correctCount = correctCount; }

    public int getWrongCount() { return wrongCount; }
    public void setWrongCount(int wrongCount) { this.wrongCount = wrongCount; }

    public int getAccuracyBefore() { return accuracyBefore; }
    public void setAccuracyBefore(int accuracyBefore) { this.accuracyBefore = accuracyBefore; }

    public int getAccuracyAfter() { return accuracyAfter; }
    public void setAccuracyAfter(int accuracyAfter) { this.accuracyAfter = accuracyAfter; }

    public int getImprovementPoints() { return improvementPoints; }
    public void setImprovementPoints(int improvementPoints) { this.improvementPoints = improvementPoints; }

    public long getCompletedAt() { return completedAt; }
    public void setCompletedAt(long completedAt) { this.completedAt = completedAt; }
}
