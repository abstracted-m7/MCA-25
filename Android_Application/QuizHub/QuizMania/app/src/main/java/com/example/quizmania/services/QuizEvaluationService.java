package com.example.quizmania.services;

import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.QuizAttempt;
import com.example.quizmania.model.WrongAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuizEvaluationService {

    public static class EvaluationResult {
        public QuizAttempt attempt;
        public List<WrongAnswer> wrongAnswers;

        public EvaluationResult(QuizAttempt attempt, List<WrongAnswer> wrongAnswers) {
            this.attempt = attempt;
            this.wrongAnswers = wrongAnswers;
        }
    }

    /**
     * Evaluates a completed quiz securely.
     *
     * @param quiz The quiz object containing questions and metadata.
     * @param userAnswers Map of question index -> selected option index (-1 for unanswered).
     * @param studentId Student's UID.
     * @param rollNumber Student's Roll Number.
     * @param studentName Student's display name.
     * @param timeTakenSeconds Time spent answering the quiz.
     * @return EvaluationResult containing the QuizAttempt and list of individual WrongAnswer models.
     */
    public static EvaluationResult evaluateQuiz(Quiz quiz,
                                                Map<Integer, Integer> userAnswers,
                                                String studentId,
                                                String rollNumber,
                                                String studentName,
                                                long timeTakenSeconds) {
        List<Question> questions = quiz.getQuestions();
        int totalQuestions = questions != null ? questions.size() : 0;
        int correctCount = 0;
        int wrongCount = 0;
        int unansweredCount = 0;

        List<WrongAnswer> wrongAnswers = new ArrayList<>();
        long timestamp = System.currentTimeMillis();

        for (int i = 0; i < totalQuestions; i++) {
            Question q = questions.get(i);
            int correctIndex = q.getCorrectOptionIndex();
            Integer selectedIndex = userAnswers != null ? userAnswers.get(i) : null;

            if (selectedIndex == null || selectedIndex == -1) {
                unansweredCount++;
                wrongCount++;

                // Unanswered also constitutes a learning gap to review
                WrongAnswer wa = createWrongAnswer(q, studentId, rollNumber, quiz.getId(),
                        "Unanswered", timestamp);
                wrongAnswers.add(wa);
            } else if (selectedIndex == correctIndex) {
                correctCount++;
            } else {
                wrongCount++;
                String selectedText = (q.getOptions() != null && selectedIndex >= 0 && selectedIndex < q.getOptions().size())
                        ? q.getOptions().get(selectedIndex)
                        : "Option " + (selectedIndex + 1);

                WrongAnswer wa = createWrongAnswer(q, studentId, rollNumber, quiz.getId(),
                        selectedText, timestamp);
                wrongAnswers.add(wa);
            }
        }

        int score = correctCount;
        int percentage = totalQuestions > 0 ? (correctCount * 100) / totalQuestions : 0;

        QuizAttempt attempt = new QuizAttempt();
        attempt.setStudentId(studentId);
        attempt.setRollNumber(rollNumber);
        attempt.setStudentName(studentName);
        attempt.setQuizId(quiz.getId());
        attempt.setQuizTitle(quiz.getTitle() != null ? quiz.getTitle() : "Quiz");
        attempt.setSubjectId(quiz.getSubjectId() != null ? quiz.getSubjectId() : "SUB_GENERAL");
        attempt.setSubjectName(quiz.getSubjectName() != null ? quiz.getSubjectName() : "General");
        attempt.setScore(score);
        attempt.setTotalQuestions(totalQuestions);
        attempt.setCorrectCount(correctCount);
        attempt.setWrongCount(wrongCount);
        attempt.setUnansweredCount(unansweredCount);
        attempt.setPercentage(percentage);
        attempt.setTimeTakenSeconds(timeTakenSeconds);
        attempt.setCompletedAt(timestamp);

        return new EvaluationResult(attempt, wrongAnswers);
    }

    private static WrongAnswer createWrongAnswer(Question q,
                                                 String studentId,
                                                 String rollNumber,
                                                 String quizId,
                                                 String selectedAnswerText,
                                                 long timestamp) {
        WrongAnswer wa = new WrongAnswer();
        wa.setStudentId(studentId);
        wa.setRollNumber(rollNumber);
        wa.setQuizId(quizId);
        wa.setQuestionId(q.getId());
        wa.setSubjectId(q.getSubjectId());
        wa.setTopicId(q.getTopicId());
        wa.setTopicName(q.getTopicName() != null ? q.getTopicName() : "General");
        wa.setQuestionText(q.getText());
        wa.setOptions(q.getOptions());
        wa.setSelectedAnswer(selectedAnswerText);
        wa.setCorrectAnswer(q.getCorrectAnswer());
        wa.setCorrectOptionIndex(q.getCorrectOptionIndex());
        wa.setExplanation(q.getExplanation());
        wa.setCreatedAt(timestamp);
        wa.setMasteryStatus("Weak");
        wa.setMasteryCorrectCount(0);
        return wa;
    }
}
