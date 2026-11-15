package com.example.quizmania.teacher;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.quizmania.adapters.SubjectAdapter;
import com.example.quizmania.databinding.ActivityManageSubjectBinding;
import com.example.quizmania.model.Subject;
import com.example.quizmania.model.Topic;
import com.example.quizmania.repository.SubjectRepository;

import java.util.ArrayList;
import java.util.List;

public class ManageSubjectActivity extends AppCompatActivity {

    private ActivityManageSubjectBinding binding;
    private SubjectRepository repository;
    private SubjectAdapter adapter;
    private final List<Subject> subjectList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageSubjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = new SubjectRepository();

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        binding.rvSubjects.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SubjectAdapter(this::showAddTopicDialog);
        binding.rvSubjects.setAdapter(adapter);

        binding.btnAddSubject.setOnClickListener(v -> showAddSubjectDialog());

        loadSubjects();
    }

    private void loadSubjects() {
        binding.progressBar.setVisibility(View.VISIBLE);
        repository.fetchSubjects(new SubjectRepository.SubjectsCallback() {
            @Override
            public void onSuccess(List<Subject> subjects) {
                binding.progressBar.setVisibility(View.GONE);
                subjectList.clear();
                if (subjects != null) {
                    subjectList.addAll(subjects);
                }
                adapter.setSubjects(subjectList);
            }

            @Override
            public void onFailure(String error) {
                binding.progressBar.setVisibility(View.GONE);
                Toast.makeText(ManageSubjectActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddSubjectDialog() {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 20);

        final EditText etName = new EditText(this);
        etName.setHint("Subject Name (e.g. Computer Networks)");
        layout.addView(etName);

        final EditText etDesc = new EditText(this);
        etDesc.setHint("Description (e.g. TCP/IP, OSI model, Routing)");
        layout.addView(etDesc);

        new AlertDialog.Builder(this)
                .setTitle("Add New Subject")
                .setView(layout)
                .setPositiveButton("Create", (dialog, which) -> {
                    String name = etName.getText().toString().trim();
                    String desc = etDesc.getText().toString().trim();
                    if (!name.isEmpty()) {
                        Subject s = new Subject();
                        s.setSubjectName(name);
                        s.setDescription(desc);
                        s.setStatus("ACTIVE");
                        s.setCreatedBy("TEACHER");

                        repository.addSubject(s, new SubjectRepository.ActionCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(ManageSubjectActivity.this, "Subject added!", Toast.LENGTH_SHORT).show();
                                loadSubjects();
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(ManageSubjectActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showAddTopicDialog(Subject subject) {
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 20, 40, 20);

        final EditText etTopic = new EditText(this);
        etTopic.setHint("Topic Name (e.g. Socket Programming)");
        layout.addView(etTopic);

        new AlertDialog.Builder(this)
                .setTitle("Add Topic to " + subject.getSubjectName())
                .setView(layout)
                .setPositiveButton("Add Topic", (dialog, which) -> {
                    String topicName = etTopic.getText().toString().trim();
                    if (!topicName.isEmpty()) {
                        Topic t = new Topic();
                        t.setSubjectId(subject.getSubjectId());
                        t.setTopicName(topicName);

                        repository.addTopic(t, new SubjectRepository.ActionCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(ManageSubjectActivity.this, "Topic added to " + subject.getSubjectName(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(String error) {
                                Toast.makeText(ManageSubjectActivity.this, error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
