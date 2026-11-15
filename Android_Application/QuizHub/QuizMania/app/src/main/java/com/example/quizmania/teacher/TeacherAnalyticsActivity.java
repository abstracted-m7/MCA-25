package com.example.quizmania.teacher;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.databinding.ActivityTeacherAnalyticsBinding;
import com.example.quizmania.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TeacherAnalyticsActivity extends AppCompatActivity {

    private ActivityTeacherAnalyticsBinding binding;
    private StudentRepository studentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTeacherAnalyticsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        studentRepository = new StudentRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        loadClassAnalytics();
    }

    private void loadClassAnalytics() {
        studentRepository.fetchClassAnalytics(analytics -> {
            Integer totalAttempts = (Integer) analytics.get("totalAttempts");
            Integer overallAccuracy = (Integer) analytics.get("overallAccuracy");

            binding.tvClassTotalAttempts.setText(String.valueOf(totalAttempts != null ? totalAttempts : 0));
            binding.tvClassOverallAccuracy.setText((overallAccuracy != null ? overallAccuracy : 0) + "%");

            @SuppressWarnings("unchecked")
            Map<String, Integer> subjectAverages = (Map<String, Integer>) analytics.get("subjectAverages");
            if (subjectAverages != null && !subjectAverages.isEmpty()) {
                binding.classSubjectBarChart.setData(subjectAverages);
            }

            @SuppressWarnings("unchecked")
            Map<String, Integer> weakTopics = (Map<String, Integer>) analytics.get("weakTopics");
            if (weakTopics != null && !weakTopics.isEmpty()) {
                List<Map.Entry<String, Integer>> list = new ArrayList<>(weakTopics.entrySet());
                list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, Integer> entry : list) {
                    sb.append("• ").append(entry.getKey()).append(" — ").append(entry.getValue()).append(" class errors\n");
                }
                binding.tvClassWeakTopics.setText(sb.toString().trim());
            } else {
                binding.tvClassWeakTopics.setText("No significant weak topics detected across the class.");
            }

            @SuppressWarnings("unchecked")
            Map<String, Integer> frequentMistakes = (Map<String, Integer>) analytics.get("frequentMistakes");
            if (frequentMistakes != null && !frequentMistakes.isEmpty()) {
                List<Map.Entry<String, Integer>> list = new ArrayList<>(frequentMistakes.entrySet());
                list.sort((a, b) -> b.getValue().compareTo(a.getValue()));

                StringBuilder sb = new StringBuilder();
                int count = 0;
                for (Map.Entry<String, Integer> entry : list) {
                    sb.append("• ").append(entry.getKey()).append(" (Missed ").append(entry.getValue()).append(" times)\n\n");
                    count++;
                    if (count >= 5) break; // Top 5 frequently missed
                }
                binding.tvFrequentMissedQuestions.setText(sb.toString().trim());
            } else {
                binding.tvFrequentMissedQuestions.setText("No frequent question errors recorded yet.");
            }
        });
    }
}
