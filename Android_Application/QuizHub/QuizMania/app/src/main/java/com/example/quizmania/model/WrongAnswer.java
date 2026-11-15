package com.example.quizmania.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WrongAnswer implements Serializable {
    private String id;
    private String studentId;
    private String rollNumber;
    private String attemptId;
    private String quizId;
    private String questionId;
    private String subjectId;
    private String subjectName;
    private String topicId;
    private String topicName;
    private String questionText;
    private List<String> options;
    private String selectedAnswer;
    private String correctAnswer;
    private int correctOptionIndex;
    private String explanation;
    private long createdAt;
    private String revisionBatchId;
    private String masteryStatus; // "Weak", "Practicing", "Mastered"
    private int masteryCorrectCount;

    public WrongAnswer() {
        // Required for Firestore
        this.options = new ArrayList<>();
        this.masteryStatus = "Weak";
        this.masteryCorrectCount = 0;
    }

    public WrongAnswer(String id, String studentId, String rollNumber, String attemptId,
                       String quizId, String questionId, String subjectId, String topicId,
                       String questionText, List<String> options, String selectedAnswer,
                       String correctAnswer, String explanation, long createdAt) {
        this();
        this.id = id;
        this.studentId = studentId;
        this.rollNumber = rollNumber;
        this.attemptId = attemptId;
        this.quizId = quizId;
        this.questionId = questionId;
        this.subjectId = subjectId;
        this.topicId = topicId;
        this.questionText = questionText;
        this.options = options != null ? options : new ArrayList<>();
        this.selectedAnswer = selectedAnswer;
        this.correctAnswer = correctAnswer;
        this.explanation = explanation;
        this.createdAt = createdAt;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }

    public String getAttemptId() { return attemptId; }
    public void setAttemptId(String attemptId) { this.attemptId = attemptId; }

    public String getQuizId() { return quizId; }
    public void setQuizId(String quizId) { this.quizId = quizId; }

    public String getQuestionId() { return questionId; }
    public void setQuestionId(String questionId) { this.questionId = questionId; }

    public String getSubjectId() { return subjectId; }
    public void setSubjectId(String subjectId) { this.subjectId = subjectId; }

    public String getSubjectName() { return subjectName; }
    public void setSubjectName(String subjectName) { this.subjectName = subjectName; }

    public String getTopicId() { return topicId; }
    public void setTopicId(String topicId) { this.topicId = topicId; }

    public String getTopicName() { return topicName; }
    public void setTopicName(String topicName) { this.topicName = topicName; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public List<String> getOptions() { return options != null ? options : new ArrayList<>(); }
    public void setOptions(List<String> options) { this.options = options; }

    public String getSelectedAnswer() { return selectedAnswer; }
    public void setSelectedAnswer(String selectedAnswer) { this.selectedAnswer = selectedAnswer; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }

    public int getCorrectOptionIndex() { return correctOptionIndex; }
    public void setCorrectOptionIndex(int correctOptionIndex) { this.correctOptionIndex = correctOptionIndex; }

    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }

    public String getRevisionBatchId() { return revisionBatchId; }
    public void setRevisionBatchId(String revisionBatchId) { this.revisionBatchId = revisionBatchId; }

    public String getMasteryStatus() { return masteryStatus != null ? masteryStatus : "Weak"; }
    public void setMasteryStatus(String masteryStatus) { this.masteryStatus = masteryStatus; }

    public int getMasteryCorrectCount() { return masteryCorrectCount; }
    public void setMasteryCorrectCount(int masteryCorrectCount) { this.masteryCorrectCount = masteryCorrectCount; }
}
