package com.example.quizmania.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Question implements Serializable {

    private String id;
    private String subjectId;
    private String topicId;
    private String topicName;
    private String text;
    private List<String> options;
    private int correctOptionIndex;
    private String correctAnswer;
    private String explanation;
    private String difficulty; // "Beginner", "Intermediate", "Advanced"
    private String createdBy;
    private long createdAt;

    public Question() {
        // Required for Gson/Firestore
        this.options = new ArrayList<>();
    }

    public Question(String id,
                    String text,
                    List<String> options,
                    int correctOptionIndex,
                    String explanation) {
        this.id = id;
        this.text = text;
        this.options = options != null ? options : new ArrayList<>();
        this.correctOptionIndex = correctOptionIndex;
        this.explanation = explanation;
        if (options != null && correctOptionIndex >= 0 && correctOptionIndex < options.size()) {
            this.correctAnswer = options.get(correctOptionIndex);
        }
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getTopicId() { return topicId; }
    public void setTopicId(String topicId) { this.topicId = topicId; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public List<String> getOptions() { return options != null ? options : new ArrayList<>(); }
    public void setOptions(List<String> options) { this.options = options; }

    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public void setCorrectOptionIndex(int correctOptionIndex) {
        this.correctOptionIndex = correctOptionIndex;
        if (options != null && correctOptionIndex >= 0 && correctOptionIndex < options.size()) {
            this.correctAnswer = options.get(correctOptionIndex);
        }
    }

    public String getCorrectAnswer() {
        if (correctAnswer != null && !correctAnswer.isEmpty()) return correctAnswer;
        if (options != null && correctOptionIndex >= 0 && correctOptionIndex < options.size()) {
            return options.get(correctOptionIndex);
        }
        return "";
    }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public String getDifficulty() { return difficulty != null ? difficulty : "Intermediate"; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public String getCreatedBy() { return createdBy; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}