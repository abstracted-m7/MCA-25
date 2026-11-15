package com.example.quizmania.teacher;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.databinding.ActivityAddQuestionBinding;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.Subject;
import com.example.quizmania.repository.QuestionRepository;
import com.example.quizmania.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddQuestionActivity extends AppCompatActivity {

    private ActivityAddQuestionBinding binding;
    private SubjectRepository subjectRepository;
    private QuestionRepository questionRepository;
    private final List<Subject> subjectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddQuestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        subjectRepository = new SubjectRepository();
        questionRepository = new QuestionRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupDifficultySpinner();
        loadSubjects();

        binding.btnSaveQuestion.setOnClickListener(v -> saveQuestion());
    }

    private void setupDifficultySpinner() {
        String[] difficulties = new String[]{"Beginner", "Intermediate", "Advanced"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, difficulties);
        binding.spinnerDifficulty.setAdapter(adapter);
        binding.spinnerDifficulty.setSelection(1); // Default Intermediate
    }

    private void loadSubjects() {
        subjectRepository.fetchSubjects(new SubjectRepository.SubjectsCallback() {
            @Override
            public void onSuccess(List<Subject> subjects) {
                subjectList.clear();
                if (subjects != null) subjectList.addAll(subjects);

                List<String> names = new ArrayList<>();
                for (Subject s : subjectList) names.add(s.getSubjectName());

                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddQuestionActivity.this, android.R.layout.simple_spinner_dropdown_item, names);
                binding.spinnerQuestionSubject.setAdapter(adapter);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(AddQuestionActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveQuestion() {
        String text = binding.etQuestionText.getText().toString().trim();
        String topic = binding.etQuestionTopic.getText().toString().trim();
        String opA = binding.etOptionA.getText().toString().trim();
        String opB = binding.etOptionB.getText().toString().trim();
        String opC = binding.etOptionC.getText().toString().trim();
        String opD = binding.etOptionD.getText().toString().trim();
        String exp = binding.etExplanation.getText().toString().trim();

        if (text.isEmpty()) {
            binding.etQuestionText.setError("Question text is required");
            return;
        }
        if (opA.isEmpty() || opB.isEmpty() || opC.isEmpty() || opD.isEmpty()) {
            Toast.makeText(this, "Please fill in all 4 options", Toast.LENGTH_SHORT).show();
            return;
        }

        int correctIndex = 0;
        int checkedId = binding.rgCorrectOption.getCheckedRadioButtonId();
        if (checkedId == binding.rbOptionB.getId()) correctIndex = 1;
        else if (checkedId == binding.rbOptionC.getId()) correctIndex = 2;
        else if (checkedId == binding.rbOptionD.getId()) correctIndex = 3;

        int subjPos = binding.spinnerQuestionSubject.getSelectedItemPosition();
        String subjectId = (subjPos >= 0 && subjPos < subjectList.size()) ? subjectList.get(subjPos).getSubjectId() : "SUB_GENERAL";

        Question q = new Question();
        q.setText(text);
        q.setSubjectId(subjectId);
        q.setTopicName(topic.isEmpty() ? "General" : topic);
        q.setDifficulty(binding.spinnerDifficulty.getSelectedItem().toString());
        q.setOptions(Arrays.asList(opA, opB, opC, opD));
        q.setCorrectOptionIndex(correctIndex);
        q.setExplanation(exp.isEmpty() ? "No explanation provided." : exp);
        q.setCreatedBy("TEACHER");

        binding.btnSaveQuestion.setEnabled(false);
        questionRepository.addQuestion(q, new QuestionRepository.ActionCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(AddQuestionActivity.this, "Question saved to bank!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(String error) {
                binding.btnSaveQuestion.setEnabled(true);
                Toast.makeText(AddQuestionActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
