package com.example.quizmania.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Quiz implements Serializable {

    private String id;
    private String title;
    private String description;
    private String subjectId;
    private String subjectName;
    private String topicId;
    private String topicName;
    private String categoryId;
    private String difficulty; // "Beginner", "Intermediate", "Advanced"
    private int durationMinutes; // e.g. 10 mins
    private int totalMarks;
    private int attemptLimit;
    private String status; // "DRAFT", "PUBLISHED", "ARCHIVED"
    private String createdBy;
    private long startTime;
    private long endTime;
    private long createdAt;
    private List<String> questionIds;
    private List<Question> questions;

    public Quiz() {
        // Required for Firestore/Gson
        this.questions = new ArrayList<>();
        this.questionIds = new ArrayList<>();
        this.durationMinutes = 10;
        this.status = "PUBLISHED";
        this.difficulty = "Intermediate";
    }

    public Quiz(String id, String title, String description,
                String categoryId, long createdAt) {
        this();
        this.id = id;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getTopicId() { return topicId; }
    public void setTopicId(String topicId) { this.topicId = topicId; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public String getCategoryId() { return categoryId; }
    public void setCategoryId(String categoryId) { this.categoryId = categoryId; }

    public String getDifficulty() { return difficulty != null ? difficulty : "Intermediate"; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getDurationMinutes() { return durationMinutes > 0 ? durationMinutes : 10; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getTotalMarks() { return totalMarks > 0 ? totalMarks : (questions != null ? questions.size() : 10); }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public int getAttemptLimit() { return attemptLimit; }
    public void setAttemptLimit(int attemptLimit) { this.attemptLimit = attemptLimit; }

    public String getStatus() { return status != null ? status : "PUBLISHED"; }
    public void setStatus(String status) { this.status = status; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public long getStartTime() { return startTime; }
    public void setStartTime(long startTime) { this.startTime = startTime; }

    public long getEndTime() { return endTime; }
    public void setEndTime(long endTime) { this.endTime = endTime; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

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
}