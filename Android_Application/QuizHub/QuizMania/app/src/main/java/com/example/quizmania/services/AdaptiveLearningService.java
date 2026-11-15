package com.example.quizmania.services;

import android.content.Context;

import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.RevisionAttempt;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.repository.AdaptiveLearningRepository;
import com.example.quizmania.repository.AiQuizRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptiveLearningService {
    private final AdaptiveLearningRepository repository;
    private final AiQuizRepository aiQuizRepository;

    public interface SubmissionListener {
        void onCompleted(QuizAttempt attempt, boolean revisionTriggered, RevisionQuiz generatedRevision);
        void onError(String message);
    }

    public interface RevisionCompletionListener {
        void onCompleted(RevisionAttempt attempt);
        void onError(String message);
    }

    public AdaptiveLearningService() {
        this.repository = new AdaptiveLearningRepository();
        this.aiQuizRepository = new AiQuizRepository();
    }

    /**
     * Complete workflow when student submits a quiz:
     * 1. Evaluates answers.
     * 2. Saves attempt and wrong answer snapshots to Firestore.
     * 3. Increments subject-specific attempt counter.
     * 4. If 5 attempts reached, generates deduplicated revision quiz!
     */
    public void submitQuiz(Quiz quiz,
                           Map<Integer, Integer> userAnswers,
                           String studentId,
                           String rollNumber,
                           String studentName,
                           long timeTakenSeconds,
                           SubmissionListener listener) {

        QuizEvaluationService.EvaluationResult result =
                QuizEvaluationService.evaluateQuiz(quiz, userAnswers, studentId, rollNumber, studentName, timeTakenSeconds);

        repository.recordQuizSubmission(result.attempt, result.wrongAnswers, new AdaptiveLearningRepository.SubmitQuizCallback() {
            @Override
            public void onSuccess(QuizAttempt attempt, boolean revisionTriggered, RevisionQuiz generatedRevision) {
                listener.onCompleted(attempt, revisionTriggered, generatedRevision);
            }

            @Override
            public void onFailure(String error) {
                listener.onError(error);
            }
        });
    }

    /**
     * Submits a completed Revision Quiz and computes accuracy improvement.
     */
    public void submitRevisionQuiz(RevisionQuiz revisionQuiz,
                                   Map<Integer, Integer> userAnswers,
                                   int accuracyBefore,
                                   RevisionCompletionListener listener) {
        List<Question> questions = revisionQuiz.getQuestions();
        int total = questions != null ? questions.size() : 0;
        int correct = 0;
        int wrong = 0;

        Map<String, Boolean> questionResults = new HashMap<>();

        for (int i = 0; i < total; i++) {
            Question q = questions.get(i);
            Integer sel = userAnswers != null ? userAnswers.get(i) : null;
            boolean isCorrect = (sel != null && sel == q.getCorrectOptionIndex());

            if (isCorrect) {
                correct++;
            } else {
                wrong++;
            }
            if (q.getId() != null) {
                questionResults.put(q.getId(), isCorrect);
            }
        }

        int score = correct;
        int accuracyAfter = total > 0 ? (correct * 100) / total : 0;

        RevisionAttempt attempt = new RevisionAttempt();
        attempt.setRevisionId(revisionQuiz.getId());
        attempt.setStudentId(revisionQuiz.getStudentId());
        attempt.setSubjectId(revisionQuiz.getSubjectId());
        attempt.setSubjectName(revisionQuiz.getSubjectName());
        attempt.setScore(score);
        attempt.setTotalQuestions(total);
        attempt.setCorrectCount(correct);
        attempt.setWrongCount(wrong);
        attempt.setAccuracyBefore(accuracyBefore);
        attempt.setAccuracyAfter(accuracyAfter);
        attempt.setImprovementPoints(accuracyAfter - accuracyBefore);
        attempt.setCompletedAt(System.currentTimeMillis());

        repository.recordRevisionAttempt(attempt, questionResults, new AdaptiveLearningRepository.ActionCallback() {
            @Override
            public void onSuccess() {
                listener.onCompleted(attempt);
            }

            @Override
            public void onFailure(String error) {
                listener.onError(error);
            }
        });
    }

    public AdaptiveLearningRepository getRepository() {
        return repository;
    }
}
