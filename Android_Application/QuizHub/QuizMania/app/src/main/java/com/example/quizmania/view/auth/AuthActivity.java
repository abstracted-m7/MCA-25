package com.example.quizmania.view.auth;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivityAuthBinding;
import com.example.quizmania.model.User;
import com.example.quizmania.repository.AuthRepository;
import com.example.quizmania.util.SessionManager;
import com.example.quizmania.view.dashboard.AdminDashboardActivity;
import com.example.quizmania.view.dashboard.StudentDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;

public class AuthActivity extends AppCompatActivity {

    private ActivityAuthBinding binding;
    private AuthRepository authRepository;
    private boolean isRegisterMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authRepository = new AuthRepository();

        // Check if user is already logged in
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            binding.progressBar.setVisibility(View.VISIBLE);
            authRepository.fetchUserProfile(uid, new AuthRepository.AuthCallback() {
                @Override
                public void onSuccess(User user) {
                    binding.progressBar.setVisibility(View.GONE);
                    navigateBasedOnRole(user);
                }

                @Override
                public void onFailure(String errorMessage) {
                    binding.progressBar.setVisibility(View.GONE);
                }
            });
        }

        setupModeTabs();
        setupRoleSelector();

        binding.btnSubmitAuth.setOnClickListener(v -> {
            if (isRegisterMode) {
                performRegister();
            } else {
                performLogin();
            }
        });

        binding.tvToggleMode.setOnClickListener(v -> {
            setRegisterMode(!isRegisterMode);
        });
    }

    private void setupModeTabs() {
        binding.tabLogin.setOnClickListener(v -> setRegisterMode(false));
        binding.tabRegister.setOnClickListener(v -> setRegisterMode(true));
    }

    private void setupRoleSelector() {
        binding.rgRole.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbStudent) {
                binding.tilRollNumber.setVisibility(View.VISIBLE);
            } else {
                binding.tilRollNumber.setVisibility(View.GONE);
            }
        });
    }

    private void setRegisterMode(boolean register) {
        isRegisterMode = register;
        int primaryColor = ContextCompat.getColor(this, R.color.primary);
        int secondaryColor = ContextCompat.getColor(this, R.color.text_secondary);
        int transparentColor = ContextCompat.getColor(this, R.color.transparent);
        int whiteColor = ContextCompat.getColor(this, R.color.white);

        if (register) {
            binding.tabRegister.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
            binding.tabRegister.setTextColor(whiteColor);
            binding.tabLogin.setBackgroundTintList(ColorStateList.valueOf(transparentColor));
            binding.tabLogin.setTextColor(secondaryColor);

            binding.layoutRoleSelector.setVisibility(View.VISIBLE);
            binding.tilName.setVisibility(View.VISIBLE);
            binding.tilConfirmPassword.setVisibility(View.VISIBLE);
            if (binding.rbStudent.isChecked()) {
                binding.tilRollNumber.setVisibility(View.VISIBLE);
            }

            binding.btnSubmitAuth.setText("Create QuizHub Account");
            binding.tvToggleMode.setText("Already have an account? Sign In");
        } else {
            binding.tabLogin.setBackgroundTintList(ColorStateList.valueOf(primaryColor));
            binding.tabLogin.setTextColor(whiteColor);
            binding.tabRegister.setBackgroundTintList(ColorStateList.valueOf(transparentColor));
            binding.tabRegister.setTextColor(secondaryColor);

            binding.layoutRoleSelector.setVisibility(View.GONE);
            binding.tilName.setVisibility(View.GONE);
            binding.tilRollNumber.setVisibility(View.GONE);
            binding.tilConfirmPassword.setVisibility(View.GONE);

            binding.btnSubmitAuth.setText("Sign In to QuizHub");
            binding.tvToggleMode.setText("Don't have an account? Register");
        }
    }

    private void performLogin() {
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            binding.etEmail.setError("Email is required");
            return;
        }
        if (password.isEmpty()) {
            binding.etPassword.setError("Password is required");
            return;
        }

        setLoading(true);
        authRepository.loginWithCallback(email, password, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                setLoading(false);
                navigateBasedOnRole(user);
            }

            @Override
            public void onFailure(String errorMessage) {
                setLoading(false);
                Toast.makeText(AuthActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void performRegister() {
        String name = binding.etName.getText().toString().trim();
        String email = binding.etEmail.getText().toString().trim();
        String password = binding.etPassword.getText().toString().trim();
        String confirmPassword = binding.etConfirmPassword.getText().toString().trim();
        String role = binding.rbTeacher.isChecked() ? "TEACHER" : "STUDENT";
        String rollNumber = binding.etRollNumber.getText().toString().trim();

        if (name.isEmpty()) {
            binding.etName.setError("Name is required");
            return;
        }
        if ("STUDENT".equals(role) && rollNumber.isEmpty()) {
            binding.etRollNumber.setError("Roll number is required for students (e.g. BWU/MCA/25/040)");
            return;
        }
        if (email.isEmpty()) {
            binding.etEmail.setError("Email is required");
            return;
        }
        if (password.length() < 6) {
            binding.etPassword.setError("Password must be at least 6 characters");
            return;
        }
        if (!password.equals(confirmPassword)) {
            binding.etConfirmPassword.setError("Passwords do not match");
            return;
        }

        setLoading(true);
        authRepository.register(name, email, password, rollNumber, role, new AuthRepository.AuthCallback() {
            @Override
            public void onSuccess(User user) {
                setLoading(false);
                Toast.makeText(AuthActivity.this, "Welcome to QuizHub, " + user.getName() + "!", Toast.LENGTH_SHORT).show();
                navigateBasedOnRole(user);
            }

            @Override
            public void onFailure(String errorMessage) {
                setLoading(false);
                Toast.makeText(AuthActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateBasedOnRole(User user) {
        SessionManager.getInstance(this).saveUserProfile(
                user.getId(),
                user.getRole(),
                user.getName(),
                user.getRollNumber(),
                user.getEmail()
        );

        if (user.isTeacher()) {
            Intent intent = new Intent(this, AdminDashboardActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, StudentDashboardActivity.class);
            startActivity(intent);
        }
        finish();
    }

    private void setLoading(boolean loading) {
        binding.progressBar.setVisibility(loading ? View.VISIBLE : View.GONE);
        binding.btnSubmitAuth.setEnabled(!loading);
    }
}
