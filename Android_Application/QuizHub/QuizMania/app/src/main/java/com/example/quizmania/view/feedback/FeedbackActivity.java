package com.example.quizmania.view.feedback;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quizmania.databinding.ActivityFeedbackBinding;
import com.example.quizmania.util.EmailSender;
import com.example.quizmania.util.SessionManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {

    public static final String TARGET_EMAIL = "manishgiri8101@gmail.com";

    private ActivityFeedbackBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedbackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = SessionManager.getInstance(this);

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        setupUserInfo();
        setupCategorySpinner();

        binding.btnSendFeedback.setOnClickListener(v -> sendFeedback());
    }

    private void setupUserInfo() {
        String name = sessionManager.getName() != null ? sessionManager.getName() : "User";
        String role = sessionManager.getRole() != null ? sessionManager.getRole() : "STUDENT";
        String roll = sessionManager.getRollNumber();

        String displayInfo = "User: " + name + " (" + role + (roll != null && !roll.isEmpty() ? " - " + roll : "") + ")";
        binding.tvUserInfo.setText(displayInfo);
    }

    private void setupCategorySpinner() {
        String[] categories = new String[]{
                "General Feedback",
                "Bug Report / Issue",
                "Quiz Content Suggestion",
                "New Feature Request",
                "Question Bank Improvement",
                "Other"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        binding.spinnerFeedbackCategory.setAdapter(adapter);
    }

    private void sendFeedback() {
        String subject = binding.etFeedbackSubject.getText().toString().trim();
        String message = binding.etFeedbackMessage.getText().toString().trim();
        String category = binding.spinnerFeedbackCategory.getSelectedItem() != null
                ? binding.spinnerFeedbackCategory.getSelectedItem().toString()
                : "General Feedback";

        if (message.isEmpty()) {
            binding.etFeedbackMessage.setError("Please enter your feedback message");
            binding.etFeedbackMessage.requestFocus();
            return;
        }

        String name = sessionManager.getName() != null ? sessionManager.getName() : "Anonymous User";
        String email = sessionManager.getEmail() != null ? sessionManager.getEmail() : "N/A";
        String role = sessionManager.getRole() != null ? sessionManager.getRole() : "STUDENT";
        String roll = sessionManager.getRollNumber() != null ? sessionManager.getRollNumber() : "N/A";

        // Disable button & show progress while sending
        binding.btnSendFeedback.setEnabled(false);
        binding.btnSendFeedback.setText("Sending...");

        // 1. Save feedback to Firestore (backup / admin review)
        Map<String, Object> feedbackData = new HashMap<>();
        feedbackData.put("userId", sessionManager.getUid());
        feedbackData.put("name", name);
        feedbackData.put("email", email);
        feedbackData.put("role", role);
        feedbackData.put("rollNumber", roll);
        feedbackData.put("category", category);
        feedbackData.put("subject", subject);
        feedbackData.put("message", message);
        feedbackData.put("targetEmail", TARGET_EMAIL);
        feedbackData.put("createdAt", System.currentTimeMillis());
        FirebaseFirestore.getInstance().collection("feedback").add(feedbackData);

        // 2. Build email subject & body
        String emailSubject = "[QuizMania " + role + " Feedback] " + (subject.isEmpty() ? category : subject);
        String emailBody = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n"
                + "QuizMania – Feedback Report\n"
                + "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n"
                + "Name     : " + name + "\n"
                + "Email    : " + email + "\n"
                + "Role     : " + role + (roll != null && !roll.equals("N/A") ? " (Roll: " + roll + ")" : "") + "\n"
                + "Category : " + category + "\n\n"
                + "Feedback Message:\n"
                + "──────────────────────────────\n"
                + message + "\n\n"
                + "──────────────────────────────\n"
                + "Sent automatically from QuizMania Android App";

        // 3. Send via SMTP silently – no email app opened
        EmailSender.send(emailSubject, emailBody, new EmailSender.SendCallback() {
            @Override
            public void onSuccess() {
                if (isFinishing()) return;
                Toast.makeText(FeedbackActivity.this,
                        "✅ Feedback sent successfully!", Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(String error) {
                if (isFinishing()) return;
                // Re-enable button so user can retry
                binding.btnSendFeedback.setEnabled(true);
                binding.btnSendFeedback.setText("Send Feedback");
                Toast.makeText(FeedbackActivity.this,
                        "⚠ Could not send email. Your feedback was saved.\nError: " + error,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
