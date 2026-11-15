package com.example.quizmania.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivityDashboardBinding;
import com.example.quizmania.view.auth.AuthActivity;
import com.example.quizmania.view.quiz.AiQuizActivity;
import com.example.quizmania.view.quiz.QuizEngineActivity;
import com.example.quizmania.viewmodel.DashboardViewModel;

public class DashboardActivity extends AppCompatActivity {

    private ActivityDashboardBinding binding;
    private DashboardViewModel viewModel;
    private QuizAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        viewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        
        setupRecyclerView();

        binding.fabAddQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, AiQuizActivity.class);
            startActivity(intent);
        });

        viewModel.getQuizzes().observe(this, quizzes -> {
            if (quizzes != null) {
                adapter.setQuizList(quizzes, quiz -> {
                    Intent intent = new Intent(DashboardActivity.this, QuizEngineActivity.class);
                    intent.putExtra("QUIZ_ID", quiz.getId());
                    startActivity(intent);
                });
            }
        });
    }

    private void setupRecyclerView() {
        binding.rvQuizzes.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuizAdapter();
        binding.rvQuizzes.setAdapter(adapter);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        viewModel.fetchQuizzes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_logout) {
            viewModel.logout();
            startActivity(new Intent(this, AuthActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
