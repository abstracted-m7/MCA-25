package com.example.quizmania.student;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.adapters.MistakeAdapter;
import com.example.quizmania.databinding.ActivityMistakeNotebookBinding;
import com.example.quizmania.model.WrongAnswer;
import com.example.quizmania.repository.AdaptiveLearningRepository;
import com.example.quizmania.util.SessionManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MistakeNotebookActivity extends AppCompatActivity {

    private ActivityMistakeNotebookBinding binding;
    private AdaptiveLearningRepository repository;
    private MistakeAdapter adapter;
    private final List<WrongAnswer> mistakesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMistakeNotebookBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new AdaptiveLearningRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupRecyclerView();
        loadStudentMistakes();
    }

    private void setupRecyclerView() {
        binding.rvMistakes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MistakeAdapter(this::showRetryQuestionDialog);
        binding.rvMistakes.setAdapter(adapter);
    }

    private void loadStudentMistakes() {
        binding.progressBar.setVisibility(View.VISIBLE);
        String studentId = SessionManager.getInstance(this).getUid();
        if (studentId == null) studentId = "STU_ANON";

        repository.fetchStudentMistakes(studentId, new AdaptiveLearningRepository.MistakesCallback() {
            @Override
            public void onSuccess(List<WrongAnswer> mistakes) {
                binding.progressBar.setVisibility(View.GONE);
                mistakesList.clear();
                if (mistakes != null) {
                    mistakesList.addAll(mistakes);
                }
                updateMasteryCounters(mistakesList);
                adapter.setMistakes(mistakesList);
                binding.tvEmptyMistakes.setVisibility(mistakesList.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(String error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(MistakeNotebookActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateMasteryCounters(List<WrongAnswer> list) {
        Set<String> weak = new HashSet<>();
        Set<String> practicing = new HashSet<>();
        Set<String> mastered = new HashSet<>();

        for (WrongAnswer wa : list) {
            String qId = wa.getQuestionId() != null ? wa.getQuestionId() : wa.getId();
            String status = wa.getMasteryStatus();
            if ("Mastered".equalsIgnoreCase(status)) {
                mastered.add(qId);
            } else if ("Practicing".equalsIgnoreCase(status)) {
                practicing.add(qId);
            } else {
                weak.add(qId);
            }
        }

        weak.removeAll(practicing);
        weak.removeAll(mastered);

        binding.tvCountWeak.setText(String.valueOf(weak.size()));
        binding.tvCountPracticing.setText(String.valueOf(practicing.size()));
        binding.tvCountMastered.setText(String.valueOf(mastered.size()));
    }

    private void showRetryQuestionDialog(WrongAnswer mistake) {
        List<String> options = mistake.getOptions();
        if (options == null || options.isEmpty()) {
            Toast.makeText(this, "Options not available for instant retry", Toast.LENGTH_SHORT).show();
            return;
        }

        CharSequence[] items = options.toArray(new CharSequence[0]);

        new AlertDialog.Builder(this)
                .setTitle(mistake.getQuestionText())
                .setItems(items, (dialog, which) -> {
                    boolean isCorrect = (which == mistake.getCorrectOptionIndex());
                    if (isCorrect) {
                        Toast.makeText(this, "✓ Correct! Great job revising this question.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "✗ Incorrect. Correct answer: " + mistake.getCorrectAnswer(), Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
