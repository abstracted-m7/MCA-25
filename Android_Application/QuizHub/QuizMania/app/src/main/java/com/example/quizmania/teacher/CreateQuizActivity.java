package com.example.quizmania.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivityCreateQuizBinding;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.Subject;
import com.example.quizmania.repository.QuestionRepository;
import com.example.quizmania.repository.QuizRepository;
import com.example.quizmania.repository.SubjectRepository;
import com.example.quizmania.util.SessionManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateQuizActivity extends AppCompatActivity {

    private ActivityCreateQuizBinding binding;
    private SubjectRepository subjectRepository;
    private QuestionRepository questionRepository;
    private QuizRepository quizRepository;

    private final List<Subject> subjectList = new ArrayList<>();
    private final List<Question> questionsInQuiz = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        subjectRepository = new SubjectRepository();
        questionRepository = new QuestionRepository();
        quizRepository = new QuizRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupDifficultySpinner();
        loadSubjects();
        setupQuestionBankIntegration();
        refreshAddedQuestionsUI();

        binding.btnPublishQuiz.setOnClickListener(v -> publishQuiz());
    }

    private void setupDifficultySpinner() {
        String[] difficulties = new String[]{"Beginner", "Intermediate", "Advanced"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
        binding.spinnerQuizDifficulty.setAdapter(adapter);
        binding.spinnerQuizDifficulty.setSelection(1);
    }

    private void loadSubjects() {
        subjectRepository.fetchSubjects(new SubjectRepository.SubjectsCallback() {
            @Override
            public void onSuccess(List<Subject> subjects) {
                subjectList.clear();
                if (subjects != null) subjectList.addAll(subjects);

                List<String> names = new ArrayList<>();
                for (Subject s : subjectList) names.add(s.getSubjectName());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateQuizActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                binding.spinnerQuizSubject.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(CreateQuizActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupQuestionBankIntegration() {
        // Add Question button
        binding.btnAddQuestionToQuiz.setOnClickListener(v -> addQuestionFromInputs());

        // Import existing questions from bank
        binding.btnImportFromBank.setOnClickListener(v -> importQuestionsFromBank());
    }

    private void addQuestionFromInputs() {
        String topic = binding.etQuestionTopic.getText().toString().trim();
        String qText = binding.etQuestionText.getText().toString().trim();
        String opA = binding.etOptionA.getText().toString().trim();
        String opB = binding.etOptionB.getText().toString().trim();
        String opC = binding.etOptionC.getText().toString().trim();
        String opD = binding.etOptionD.getText().toString().trim();
        String explanation = binding.etExplanation.getText().toString().trim();

        if (qText.isEmpty()) {
            binding.etQuestionText.setError("Question text is required");
            binding.etQuestionText.requestFocus();
            return;
        }

        if (opA.isEmpty() || opB.isEmpty() || opC.isEmpty() || opD.isEmpty()) {
            Toast.makeText(this, "Please enter all 4 options (A, B, C, D)", Toast.LENGTH_SHORT).show();
            return;
        }

        int correctIndex = 0;
        int checkedId = binding.rgCorrectOption.getCheckedRadioButtonId();
        if (checkedId == binding.rbOptionB.getId()) correctIndex = 1;
        else if (checkedId == binding.rbOptionC.getId()) correctIndex = 2;
        else if (checkedId == binding.rbOptionD.getId()) correctIndex = 3;

        Subject currentSubject = getSelectedSubject();

        Question q = new Question();
        q.setId("Q_" + System.currentTimeMillis() + "_" + (questionsInQuiz.size() + 1));
        q.setText(qText);
        q.setSubjectId(currentSubject.getSubjectId());
        q.setTopicName(topic.isEmpty() ? currentSubject.getSubjectName() : topic);
        q.setDifficulty(binding.spinnerQuizDifficulty.getSelectedItem() != null
                ? binding.spinnerQuizDifficulty.getSelectedItem().toString()
                : "Intermediate");
        q.setOptions(Arrays.asList(opA, opB, opC, opD));
        q.setCorrectOptionIndex(correctIndex);
        q.setExplanation(explanation.isEmpty() ? "No explanation provided." : explanation);
        q.setCreatedBy("TEACHER");
        q.setCreatedAt(System.currentTimeMillis());

        questionsInQuiz.add(q);
        refreshAddedQuestionsUI();

        // Clear input fields for next question
        binding.etQuestionText.setText("");
        binding.etOptionA.setText("");
        binding.etOptionB.setText("");
        binding.etOptionC.setText("");
        binding.etOptionD.setText("");
        binding.etExplanation.setText("");
        binding.rbOptionA.setChecked(true);
        binding.etQuestionText.requestFocus();

        Toast.makeText(this, "Question " + questionsInQuiz.size() + " added to quiz!", Toast.LENGTH_SHORT).show();
    }

    private void importQuestionsFromBank() {
        Subject currentSubject = getSelectedSubject();
        binding.btnImportFromBank.setEnabled(false);

        questionRepository.fetchQuestionsBySubject(currentSubject.getSubjectId(), new QuestionRepository.QuestionsCallback() {
            @Override
            public void onSuccess(List<Question> questions) {
                binding.btnImportFromBank.setEnabled(true);
                if (questions == null || questions.isEmpty()) {
                    Toast.makeText(CreateQuizActivity.this, "No existing questions found in Question Bank for " + currentSubject.getSubjectName(), Toast.LENGTH_SHORT).show();
                    return;
                }

                int imported = 0;
                for (Question bankQ : questions) {
                    boolean alreadyExists = false;
                    for (Question current : questionsInQuiz) {
                        if (current.getText().equalsIgnoreCase(bankQ.getText())) {
                            alreadyExists = true;
                            break;
                        }
                    }
                    if (!alreadyExists) {
                        questionsInQuiz.add(bankQ);
                        imported++;
                    }
                }

                refreshAddedQuestionsUI();
                Toast.makeText(CreateQuizActivity.this, "Imported " + imported + " questions from Question Bank!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String error) {
                binding.btnImportFromBank.setEnabled(true);
                Toast.makeText(CreateQuizActivity.this, "Failed to import from bank: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshAddedQuestionsUI() {
        binding.tvQuestionsHeader.setText("2. Quiz Questions (" + questionsInQuiz.size() + " Added)");
        binding.tvNoQuestionsAdded.setVisibility(questionsInQuiz.isEmpty() ? View.VISIBLE : View.GONE);
        binding.layoutAddedQuestions.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < questionsInQuiz.size(); i++) {
            Question q = questionsInQuiz.get(i);
            int index = i;

            View card = inflater.inflate(R.layout.item_added_question, binding.layoutAddedQuestions, false);

            TextView tvNum = card.findViewById(R.id.tvAddedQuestionNum);
            TextView tvTopic = card.findViewById(R.id.tvAddedQuestionTopic);
            TextView tvText = card.findViewById(R.id.tvAddedQuestionText);
            TextView tvOptions = card.findViewById(R.id.tvAddedQuestionOptions);
            TextView tvCorrect = card.findViewById(R.id.tvAddedQuestionCorrect);
            TextView tvExp = card.findViewById(R.id.tvAddedQuestionExplanation);
            ImageButton btnRemove = card.findViewById(R.id.btnRemoveQuestion);

            tvNum.setText("Q" + (index + 1));
            tvTopic.setText(q.getTopicName() != null ? q.getTopicName() : "General");
            tvText.setText(q.getText());

            List<String> ops = q.getOptions();
            String opA = ops.size() > 0 ? ops.get(0) : "";
            String opB = ops.size() > 1 ? ops.get(1) : "";
            String opC = ops.size() > 2 ? ops.get(2) : "";
            String opD = ops.size() > 3 ? ops.get(3) : "";
            tvOptions.setText("A: " + opA + "   |   B: " + opB + "\nC: " + opC + "   |   D: " + opD);

            tvCorrect.setText("✓ Correct Answer: " + q.getCorrectAnswer());
            tvExp.setText("Explanation: " + (q.getExplanation() != null ? q.getExplanation() : "None"));

            btnRemove.setOnClickListener(v -> {
                questionsInQuiz.remove(index);
                refreshAddedQuestionsUI();
            });

            binding.layoutAddedQuestions.addView(card);
        }
    }

    private Subject getSelectedSubject() {
        int pos = binding.spinnerQuizSubject.getSelectedItemPosition();
        if (pos >= 0 && pos < subjectList.size()) {
            return subjectList.get(pos);
        }
        return new Subject("SUB_JAVA", "Java Programming", "", "SYSTEM", "ACTIVE");
    }

    private void publishQuiz() {
        String title = binding.etQuizTitle.getText().toString().trim();
        String desc = binding.etQuizDescription.getText().toString().trim();
        String durationStr = binding.etQuizDuration.getText().toString().trim();

        if (title.isEmpty()) {
            binding.etQuizTitle.setError("Quiz title is required");
            binding.etQuizTitle.requestFocus();
            return;
        }

        if (questionsInQuiz.isEmpty()) {
            Toast.makeText(this, "Please add at least 1 question before publishing!", Toast.LENGTH_LONG).show();
            binding.etQuestionText.requestFocus();
            return;
        }

        int duration = 10;
        try {
            duration = Integer.parseInt(durationStr);
        } catch (Exception ignored) {}

        Subject selSubject = getSelectedSubject();

        binding.btnPublishQuiz.setEnabled(false);
        binding.progressBarPublish.setVisibility(View.VISIBLE);

        // 1. Save all newly created questions to the central Question Bank in Firestore
        for (Question q : questionsInQuiz) {
            questionRepository.addQuestion(q, new QuestionRepository.ActionCallback() {
                @Override public void onSuccess() {}
                @Override public void onFailure(String error) {}
            });
        }

        // 2. Publish Quiz to quizzes collection
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setDescription(desc.isEmpty() ? "Official published quiz for " + selSubject.getSubjectName() : desc);
        quiz.setSubjectId(selSubject.getSubjectId());
        quiz.setSubjectName(selSubject.getSubjectName());
        quiz.setDifficulty(binding.spinnerQuizDifficulty.getSelectedItem() != null
                ? binding.spinnerQuizDifficulty.getSelectedItem().toString()
                : "Intermediate");
        quiz.setDurationMinutes(duration);
        quiz.setTotalMarks(questionsInQuiz.size());
        quiz.setStatus("PUBLISHED");
        String teacherUid = SessionManager.getInstance(this).getUid();
        quiz.setCreatedBy(teacherUid != null ? teacherUid : "TEACHER");
        quiz.setCreatedAt(System.currentTimeMillis());

        quizRepository.saveQuizWithCallback(quiz, questionsInQuiz, new QuizRepository.ActionCallback() {
            @Override
            public void onSuccess() {
                binding.progressBarPublish.setVisibility(View.GONE);
                Toast.makeText(CreateQuizActivity.this, "Quiz published successfully with " + questionsInQuiz.size() + " questions! Now visible in student portal.", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(String error) {
                binding.progressBarPublish.setVisibility(View.GONE);
                binding.btnPublishQuiz.setEnabled(true);
                Toast.makeText(CreateQuizActivity.this, "Error publishing quiz: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}