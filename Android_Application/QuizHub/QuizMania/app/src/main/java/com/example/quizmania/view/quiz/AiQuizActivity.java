package com.example.quizmania.view.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.quizmania.databinding.ActivityAiQuizBinding;
import com.example.quizmania.model.Quiz;
import com.example.quizmania.student.AttemptQuizActivity;
import com.example.quizmania.viewmodel.AiQuizViewModel;
import com.google.gson.Gson;

public class AiQuizActivity extends AppCompatActivity {

    private ActivityAiQuizBinding binding;
    private AiQuizViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAiQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (binding.toolbar != null) {
            binding.toolbar.setNavigationOnClickListener(v -> finish());
        }

        viewModel = new ViewModelProvider(this).get(AiQuizViewModel.class);

        String[] modelNames = {
                "OpenRouter Auto (Free)",
                "Meta Llama 3 8B (Free)",
                "Mistral 7B Instruct (Free)"
        };

        String[] modelIds = {
                "openrouter/free",
                "meta-llama/llama-3-8b-instruct:free",
                "mistralai/mistral-7b-instruct:free"
        };

        ArrayAdapter<String> modelAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                modelNames
        );
        modelAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerModel.setAdapter(modelAdapter);
        binding.spinnerModel.setSelection(0);

        binding.btnGenerate.setOnClickListener(v -> {
            String topic = binding.etTopic.getText().toString().trim();
            String numStr = binding.etNumQuestions.getText().toString().trim();

            int selectModel = binding.spinnerModel.getSelectedItemPosition();
            String model = (selectModel >= 0 && selectModel < modelIds.length) ? modelIds[selectModel] : "openrouter/free";

            if (topic.isEmpty()) {
                binding.etTopic.setError("Topic is required");
                return;
            }

            int num = 5;
            try {
                num = Integer.parseInt(numStr);
            } catch (NumberFormatException ignored) {}

            if (num < 1) num = 5;
            if (num > 20) num = 20;

            binding.progressBar.setVisibility(View.VISIBLE);
            binding.btnGenerate.setEnabled(false);
            binding.tvStatus.setText("Contacting OpenRouter AI Engine & Generating Questions...");

            viewModel.generateAiQuiz(topic, num, model);
        });

        viewModel.getGenerationStatus().observe(this, status -> {
            if (status != null) binding.tvStatus.setText(status);
        });

        viewModel.getGenerationSuccess().observe(this, isSuccess -> {
            if (isSuccess == null) return;
            binding.progressBar.setVisibility(View.GONE);
            binding.btnGenerate.setEnabled(true);

            if (isSuccess) {
                Toast.makeText(this, "Quiz Generated Successfully!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to generate AI quiz. Please verify network and API key.", Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getGeneratedQuiz().observe(this, quiz -> {
            if (quiz == null) return;

            String quizJson = new Gson().toJson(quiz);

            // Launch modern AttemptQuizActivity
            Intent intent = new Intent(AiQuizActivity.this, AttemptQuizActivity.class);
            intent.putExtra(AttemptQuizActivity.EXTRA_QUIZ_JSON, quizJson);
            startActivity(intent);
            finish();
        });
    }
}