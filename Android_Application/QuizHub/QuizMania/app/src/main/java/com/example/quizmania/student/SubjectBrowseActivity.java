package com.example.quizmania.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivitySubjectBrowseBinding;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.model.Subject;
import com.example.quizmania.repository.QuizRepository;
import com.example.quizmania.repository.SubjectRepository;
import com.example.quizmania.view.dashboard.QuizAdapter;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;
import java.util.List;

public class SubjectBrowseActivity extends AppCompatActivity {

    private ActivitySubjectBrowseBinding binding;
    private SubjectRepository subjectRepository;
    private QuizRepository quizRepository;
    private QuizAdapter adapter;
    private final List<Quiz> allQuizzes = new ArrayList<>();
    private String selectedSubjectId = "ALL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubjectBrowseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        subjectRepository = new SubjectRepository();
        quizRepository = new QuizRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupRecyclerView();
        loadSubjects();
        loadQuizzes();
    }

    private void setupRecyclerView() {
        binding.rvSubjectQuizzes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizAdapter();
        binding.rvSubjectQuizzes.setAdapter(adapter);
    }

    private void loadSubjects() {
        subjectRepository.fetchSubjects(new SubjectRepository.SubjectsCallback() {
            @Override
            public void onSuccess(List<Subject> subjects) {
                populateSubjectChips(subjects);
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(SubjectBrowseActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSubjectChips(List<Subject> subjects) {
        binding.chipGroupSubjects.removeAllViews();

        Chip chipAll = new Chip(this);
        chipAll.setText("All Subjects");
        chipAll.setCheckable(true);
        chipAll.setChecked(true);
        chipAll.setOnCheckedChangeListener((btn, checked) -> {
            if (checked) {
                selectedSubjectId = "ALL";
                filterQuizzes();
            }
        });
        binding.chipGroupSubjects.addView(chipAll);

        for (Subject s : subjects) {
            Chip chip = new Chip(this);
            chip.setText(s.getSubjectName());
            chip.setCheckable(true);
            chip.setOnCheckedChangeListener((btn, checked) -> {
                if (checked) {
                    selectedSubjectId = s.getSubjectId();
                    filterQuizzes();
                }
            });
            binding.chipGroupSubjects.addView(chip);
        }
    }

    private void loadQuizzes() {
        binding.progressBar.setVisibility(View.VISIBLE);
        androidx.lifecycle.MutableLiveData<List<Quiz>> liveData = new androidx.lifecycle.MutableLiveData<>();
        liveData.observe(this, quizzes -> {
            binding.progressBar.setVisibility(View.GONE);
            allQuizzes.clear();
            if (quizzes != null) {
                allQuizzes.addAll(quizzes);
            }
            filterQuizzes();
        });
        quizRepository.fetchQuizzes(liveData);
    }

    private void filterQuizzes() {
        List<Quiz> filtered = new ArrayList<>();
        if ("ALL".equals(selectedSubjectId)) {
            filtered.addAll(allQuizzes);
        } else {
            for (Quiz q : allQuizzes) {
                if (selectedSubjectId.equalsIgnoreCase(q.getSubjectId())) {
                    filtered.add(q);
                }
            }
        }

        binding.tvEmptyState.setVisibility(filtered.isEmpty() ? View.VISIBLE : View.GONE);
        adapter.setQuizList(filtered, quiz -> {
            Intent intent = new Intent(SubjectBrowseActivity.this, AttemptQuizActivity.class);
            intent.putExtra(AttemptQuizActivity.EXTRA_QUIZ_ID, quiz.getId());
            startActivity(intent);
        });
    }
}
