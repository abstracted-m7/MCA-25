package com.example.quizmania.view.launch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quizmania.R;
import com.example.quizmania.view.auth.AuthActivity;

public class LaunchActivity extends AppCompatActivity {
    public static final String EXTRA_ROLE = "extra_role";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        findViewById(R.id.btnAdminLogin).setOnClickListener(v -> openAuth("admin"));
        findViewById(R.id.btnStudentLogin).setOnClickListener(v -> openAuth("student"));
    }

    private void openAuth(String role) {
        Intent intent = new Intent(this, AuthActivity.class);
        intent.putExtra(EXTRA_ROLE, role);
        startActivity(intent);
        finish();
    }
}
