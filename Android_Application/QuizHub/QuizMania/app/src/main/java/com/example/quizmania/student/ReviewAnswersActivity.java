package com.example.quizmania.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmania.R;
import com.example.quizmania.databinding.ActivityReviewAnswersBinding;
import com.example.quizmania.model.Question;
import com.example.quizmania.model.Quiz;
import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewAnswersActivity extends AppCompatActivity {

    private ActivityReviewAnswersBinding binding;
    private Quiz quiz;
    private Map<Integer, Integer> userAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewAnswersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.toolbar.setNavigationOnClickListener(v -> finish());

        String quizJson = getIntent().getStringExtra("EXTRA_QUIZ_JSON");
        if (quizJson != null) {
            quiz = new Gson().fromJson(quizJson, Quiz.class);
        }

        String userAnswersJson = getIntent().getStringExtra("EXTRA_USER_ANSWERS_JSON");
        if (userAnswersJson != null) {
            Type type = new TypeToken<Map<Integer, Integer>>(){}.getType();
            try {
                userAnswers = new Gson().fromJson(userAnswersJson, type);
            } catch (Exception ignored) {}
        }

        binding.rvReviewList.setLayoutManager(new LinearLayoutManager(this));
        List<Question> questions = (quiz != null && quiz.getQuestions() != null) ? quiz.getQuestions() : new ArrayList<>();
        ReviewAdapter adapter = new ReviewAdapter(questions, userAnswers);
        binding.rvReviewList.setAdapter(adapter);
    }

    static class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
        private final List<Question> questions;
        private final Map<Integer, Integer> userAnswers;

        ReviewAdapter(List<Question> questions, Map<Integer, Integer> userAnswers) {
            this.questions = questions;
            this.userAnswers = userAnswers;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mistake, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Question q = questions.get(position);
            holder.tvTopic.setText(q.getTopicName() != null ? q.getTopicName() : "Question " + (position + 1));
            holder.tvMasteryBadge.setVisibility(View.GONE);
            holder.btnRetry.setVisibility(View.GONE);

            holder.tvQuestion.setText((position + 1) + ". " + q.getText());
            String correctAns = q.getCorrectAnswer();

            if (userAnswers != null) {
                Integer selectedOptionIndex = userAnswers.get(position);
                int correctOptionIndex = q.getCorrectOptionIndex();

                if (selectedOptionIndex == null || selectedOptionIndex == -1) {
                    // Unanswered / Skipped
                    holder.layoutYourWrongAnswer.setVisibility(View.VISIBLE);
                    holder.tvYourWrongAnswer.setVisibility(View.VISIBLE);
                    holder.tvYourWrongAnswer.setText("Your Answer: Unanswered (Skipped)");
                    holder.tvCorrectAnswer.setText("Correct Answer: " + correctAns);
                } else if (selectedOptionIndex == correctOptionIndex) {
                    // Answered correctly
                    holder.layoutYourWrongAnswer.setVisibility(View.GONE);
                    holder.tvCorrectAnswer.setText("Your Answer: " + correctAns + " (Correct ✓)");
                } else {
                    // Answered wrongly
                    holder.layoutYourWrongAnswer.setVisibility(View.VISIBLE);
                    holder.tvYourWrongAnswer.setVisibility(View.VISIBLE);

                    List<String> options = q.getOptions();
                    String wrongText = (options != null && selectedOptionIndex >= 0 && selectedOptionIndex < options.size())
                            ? options.get(selectedOptionIndex)
                            : "Option " + (selectedOptionIndex + 1);

                    holder.tvYourWrongAnswer.setText("Your Answer: " + wrongText);
                    holder.tvCorrectAnswer.setText("Correct Answer: " + correctAns);
                }
            } else {
                // General review without user response data
                holder.layoutYourWrongAnswer.setVisibility(View.GONE);
                holder.tvCorrectAnswer.setText("Correct Answer: " + correctAns);
            }

            holder.tvExplanation.setText("Explanation: " + (q.getExplanation() != null && !q.getExplanation().isEmpty() ? q.getExplanation() : "No explanation provided."));
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTopic;
            TextView tvMasteryBadge;
            TextView tvQuestion;
            View layoutYourWrongAnswer;
            TextView tvYourWrongAnswer;
            TextView tvCorrectAnswer;
            TextView tvExplanation;
            View btnRetry;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTopic = itemView.findViewById(R.id.tvMistakeTopic);
                tvMasteryBadge = itemView.findViewById(R.id.tvMasteryBadge);
                tvQuestion = itemView.findViewById(R.id.tvMistakeQuestion);
                layoutYourWrongAnswer = itemView.findViewById(R.id.layoutYourWrongAnswer);
                tvYourWrongAnswer = itemView.findViewById(R.id.tvYourWrongAnswer);
                tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswerSnapshot);
                tvExplanation = itemView.findViewById(R.id.tvMistakeExplanation);
                btnRetry = itemView.findViewById(R.id.btnRetryQuestion);
            }
        }
    }
}
