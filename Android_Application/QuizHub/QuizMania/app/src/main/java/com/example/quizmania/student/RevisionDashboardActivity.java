package com.example.quizmania.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.R;
import com.example.quizmania.adapters.RevisionAdapter;
import com.example.quizmania.databinding.ActivityRevisionDashboardBinding;
import com.example.quizmania.model.LearningProgress;
import com.example.quizmania.model.RevisionQuiz;
import com.example.quizmania.repository.AdaptiveLearningRepository;
import com.example.quizmania.repository.StudentRepository;
import com.example.quizmania.util.SessionManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RevisionDashboardActivity extends AppCompatActivity {

    private ActivityRevisionDashboardBinding binding;
    private AdaptiveLearningRepository repository;
    private StudentRepository studentRepository;
    private RevisionAdapter adapter;
    private final List<RevisionQuiz> revisionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRevisionDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new AdaptiveLearningRepository();
        studentRepository = new StudentRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupRecyclerView();
        setupGenerateAction();
        loadLearningProgress();
        loadPendingRevisions();
    }

    private void setupRecyclerView() {
        binding.rvRevisionQuizzes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RevisionAdapter(this::startRevisionQuiz);
        binding.rvRevisionQuizzes.setAdapter(adapter);
    }

    private void setupGenerateAction() {
        binding.btnGenerateAdaptiveQuiz.setOnClickListener(v -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnGenerateAdaptiveQuiz.setEnabled(false);

            SessionManager sm = SessionManager.getInstance(this);
            String studentId = sm.getUid() != null ? sm.getUid() : "STU_ANON";
            String rollNumber = sm.getRollNumber() != null ? sm.getRollNumber() : "BWU/MCA/25/040";

            repository.generateRevisionBatch(studentId, rollNumber, null, "Adaptive Revision", new AdaptiveLearningRepository.RevisionQuizCallback() {
                @Override
                public void onSuccess(RevisionQuiz revisionQuiz) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnGenerateAdaptiveQuiz.setEnabled(true);
                    Toast.makeText(RevisionDashboardActivity.this, "Adaptive Revision Quiz created from your wrong answers!", Toast.LENGTH_LONG).show();
                    loadPendingRevisions();
                }

                @Override
                public void onFailure(String error) {
                    binding.progressBar.setVisibility(View.GONE);
                    binding.btnGenerateAdaptiveQuiz.setEnabled(true);
                    Toast.makeText(RevisionDashboardActivity.this, "Could not generate revision: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadLearningProgress() {
        String studentId = SessionManager.getInstance(this).getUid();
        if (studentId == null) studentId = "STU_ANON";

        studentRepository.fetchStudentHistory(studentId, (student, attempts, mistakes) -> {
            int attemptCount = (attempts != null) ? attempts.size() : 0;
            int mistakeCount = (mistakes != null) ? mistakes.size() : 0;

            int step = attemptCount >= 5 ? 5 : (attemptCount % 5);
            updateAttemptStepUI(step, attemptCount);

            if (attemptCount >= 5) {
                binding.cardGenerateRevision.setVisibility(View.VISIBLE);
                binding.tvGenerateRevisionDesc.setText(
                        "You have completed " + attemptCount + " quiz attempts with " + mistakeCount +
                        " wrong questions stored in your database. Click below to generate your personalized adaptive revision quiz!");
            } else {
                binding.cardGenerateRevision.setVisibility(View.GONE);
            }
        });
    }

    private void updateAttemptStepUI(int step, int totalCompleted) {
        if (totalCompleted >= 5) {
            binding.tvAttemptTrackerStatus.setText("Milestone reached! " + totalCompleted + " attempts completed – Revision engine active");
        } else {
            int needed = 5 - (totalCompleted % 5);
            binding.tvAttemptTrackerStatus.setText("Progress: " + (totalCompleted % 5) + " / 5 completed towards adaptive revision (" + needed + " more needed)");
        }

        TextView[] steps = new TextView[]{binding.step1, binding.step2, binding.step3, binding.step4, binding.step5};
        for (int i = 0; i < 5; i++) {
            if (i < step) {
                steps[i].setBackgroundResource(R.drawable.bg_option_selected);
                steps[i].setTextColor(getResources().getColor(R.color.primary));
            } else {
                steps[i].setBackgroundResource(R.drawable.bg_option_default);
                steps[i].setTextColor(getResources().getColor(R.color.text_secondary));
            }
        }
    }

    private void loadPendingRevisions() {
        binding.progressBar.setVisibility(View.VISIBLE);
        String studentId = SessionManager.getInstance(this).getUid();
        if (studentId == null) studentId = "STU_ANON";

        repository.fetchPendingRevisionQuizzes(studentId, new AdaptiveLearningRepository.RevisionListCallback() {
            @Override
            public void onSuccess(List<RevisionQuiz> quizzes) {
                binding.progressBar.setVisibility(View.GONE);
                revisionList.clear();
                if (quizzes != null) {
                    revisionList.addAll(quizzes);
                }
                adapter.setRevisionList(revisionList);
                binding.tvEmptyRevisions.setVisibility(revisionList.isEmpty() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onFailure(String error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(RevisionDashboardActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startRevisionQuiz(RevisionQuiz quiz) {
        Intent intent = new Intent(this, RevisionQuizActivity.class);
        intent.putExtra("EXTRA_REVISION_JSON", new Gson().toJson(quiz));
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadLearningProgress();
        loadPendingRevisions();
    }
}
