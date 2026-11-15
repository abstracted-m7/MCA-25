package com.example.quizmania.view.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.util.SessionManager;
import com.example.quizmania.view.dashboard.AdminDashboardActivity;
import com.example.quizmania.view.dashboard.StudentDashboardActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // No UI needed; just redirect based on auth state
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            FirebaseFirestore.getInstance().collection("users").document(uid)
                    .get()
                    .addOnSuccessListener(document -> {
                        String role = document.getString("role");
                        if (role == null) role = "student";
                        // Store in SessionManager for quick access
                        SessionManager.getInstance(this).saveUser(uid, role);
                        navigateByRole(role);
                    })
                    .addOnFailureListener(e -> navigateByRole("student"));
        } else {
            // No logged-in user, go to launch screen
            startActivity(new Intent(this, com.example.quizmania.view.launch.LaunchActivity.class));
            finish();
        }
    }

    private void navigateByRole(String role) {
        Intent intent;
        if ("admin".equalsIgnoreCase(role)) {
            intent = new Intent(this, AdminDashboardActivity.class);
        } else {
            intent = new Intent(this, StudentDashboardActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
