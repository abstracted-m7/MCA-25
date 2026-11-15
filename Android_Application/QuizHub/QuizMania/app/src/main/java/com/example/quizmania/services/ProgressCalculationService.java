package com.example.quizmania.services;

import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.WrongAnswer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ProgressCalculationService {

    public static class StudentAnalytics {
        public int totalQuizzes;
        public int totalQuestions;
        public int totalCorrect;
        public int totalWrong;
        public int overallAccuracy;
        public int averageScore;
        public int streakDays;
        public Map<String, Integer> subjectAccuracy; // Subject -> accuracy %
        public List<String> weakTopics;
        public List<String> strongTopics;
        public int weakQuestionsCount;
        public int practicingQuestionsCount;
        public int masteredQuestionsCount;
    }

    public static StudentAnalytics computeAnalytics(List<QuizAttempt> attempts, List<WrongAnswer> mistakes) {
        StudentAnalytics stats = new StudentAnalytics();
        stats.subjectAccuracy = new LinkedHashMap<>();
        stats.weakTopics = new ArrayList<>();
        stats.strongTopics = new ArrayList<>();

        if (attempts == null) attempts = new ArrayList<>();
        if (mistakes == null) mistakes = new ArrayList<>();

        stats.totalQuizzes = attempts.size();

        Map<String, int[]> subjectMap = new HashMap<>(); // [totalCorrect, totalQuestions]
        Set<String> attemptDates = new HashSet<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        int totalPercent = 0;
        for (QuizAttempt a : attempts) {
            stats.totalQuestions += a.getTotalQuestions();
            stats.totalCorrect += a.getCorrectCount();
            stats.totalWrong += a.getWrongCount();
            totalPercent += a.getPercentage();

            String subj = a.getSubjectName() != null ? a.getSubjectName() : "General";
            int[] arr = subjectMap.getOrDefault(subj, new int[]{0, 0});
            arr[0] += a.getCorrectCount();
            arr[1] += a.getTotalQuestions();
            subjectMap.put(subj, arr);

            if (a.getCompletedAt() > 0) {
                attemptDates.add(sdf.format(new Date(a.getCompletedAt())));
            }
        }

        if (stats.totalQuestions > 0) {
            stats.overallAccuracy = (stats.totalCorrect * 100) / stats.totalQuestions;
        }
        if (!attempts.isEmpty()) {
            stats.averageScore = totalPercent / attempts.size();
        } else {
            stats.averageScore = stats.overallAccuracy;
        }

        stats.streakDays = attemptDates.size(); // Distinct active days

        // Subject breakdown
        for (Map.Entry<String, int[]> entry : subjectMap.entrySet()) {
            int qTotal = entry.getValue()[1];
            int qCorr = entry.getValue()[0];
            int acc = qTotal > 0 ? (qCorr * 100) / qTotal : 0;
            stats.subjectAccuracy.put(entry.getKey(), acc);
        }

        // Mistake analysis & Mastery counts
        Map<String, Integer> topicMistakes = new HashMap<>();
        Set<String> weakQ = new HashSet<>();
        Set<String> practicingQ = new HashSet<>();
        Set<String> masteredQ = new HashSet<>();

        for (WrongAnswer wa : mistakes) {
            String topic = wa.getTopicName() != null ? wa.getTopicName() : "Core Principles";
            topicMistakes.put(topic, topicMistakes.getOrDefault(topic, 0) + 1);

            String status = wa.getMasteryStatus();
            String qId = wa.getQuestionId() != null ? wa.getQuestionId() : wa.getId();
            if ("Mastered".equalsIgnoreCase(status)) {
                masteredQ.add(qId);
            } else if ("Practicing".equalsIgnoreCase(status)) {
                practicingQ.add(qId);
            } else {
                weakQ.add(qId);
            }
        }

        // Questions in practicing/mastered should not count in weak
        weakQ.removeAll(practicingQ);
        weakQ.removeAll(masteredQ);

        stats.weakQuestionsCount = weakQ.size();
        stats.practicingQuestionsCount = practicingQ.size();
        stats.masteredQuestionsCount = masteredQ.size();

        // Sort weak topics
        List<Map.Entry<String, Integer>> sortedTopics = new ArrayList<>(topicMistakes.entrySet());
        sortedTopics.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> e : sortedTopics) {
            stats.weakTopics.add(e.getKey() + " (" + e.getValue() + " mistakes)");
        }

        return stats;
    }
}
