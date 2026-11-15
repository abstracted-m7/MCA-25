package com.example.quizmania.model;

public class Attempt {
    private String id;
    private String userId;
    private String quizId;
    private int score;
    private long timestamp;

    public Attempt() {
        // Required for Firestore
    }

    public Attempt(String id, String userId, String quizId, int score, long timestamp) {
        this.id = id;
        this.userId = userId;
        this.quizId = quizId;
        this.score = score;
        this.timestamp = timestamp;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}
