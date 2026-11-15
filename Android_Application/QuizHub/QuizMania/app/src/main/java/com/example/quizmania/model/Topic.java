package com.example.quizmania.model;

import java.io.Serializable;

public class Topic implements Serializable {
    private String topicId;
    private String subjectId;
    private String topicName;
    private String description;

    public Topic() {
        // Required for Firestore
    }

    public Topic(String topicId, String subjectId, String topicName) {
        this.topicId = topicId;
        this.subjectId = subjectId;
        this.topicName = topicName;
    }

    public String getTopicId() { return topicId; }
    public void setTopicId(String topicId) { this.topicId = topicId; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return topicName != null ? topicName : "";
    }
}
