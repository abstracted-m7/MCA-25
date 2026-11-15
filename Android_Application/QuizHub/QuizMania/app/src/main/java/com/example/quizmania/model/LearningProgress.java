package com.example.quizmania.model;

import java.io.Serializable;

public class LearningProgress implements Serializable {
    private String studentId;
    private String subjectId;
    private String subjectName;
    private int completedAttempts;
    private boolean pendingRevision;
    private String currentBatchId;
    private long updatedAt;

    public LearningProgress() {
        // Required for Firestore
    }

    public LearningProgress(String studentId, String subjectId, String subjectName) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.completedAttempts = 0;
        this.pendingRevision = false;
        this.updatedAt = System.currentTimeMillis();
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public int getCompletedAttempts() { return completedAttempts; }
    public void setCompletedAttempts(int completedAttempts) { this.completedAttempts = completedAttempts; }

    public boolean isPendingRevision() { return pendingRevision; }
    public void setPendingRevision(boolean pendingRevision) { this.pendingRevision = pendingRevision; }

    public String getCurrentBatchId() { return currentBatchId; }
    public void setCurrentBatchId(String currentBatchId) { this.currentBatchId = currentBatchId; }

    public long getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(long updatedAt) { this.updatedAt = updatedAt; }

    public int getAttemptsTowardsRevision() {
        return completedAttempts % 5;
    }
}
