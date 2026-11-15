package com.example.quizmania.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmania.R;
import com.example.quizmania.model.WrongAnswer;

import java.util.ArrayList;
import java.util.List;

public class MistakeAdapter extends RecyclerView.Adapter<MistakeAdapter.MistakeViewHolder> {

    public interface OnRetryListener {
        void onRetry(WrongAnswer mistake);
    }

    private final List<WrongAnswer> mistakes = new ArrayList<>();
    private final OnRetryListener retryListener;

    public MistakeAdapter(OnRetryListener retryListener) {
        this.retryListener = retryListener;
    }

    public void setMistakes(List<WrongAnswer> newMistakes) {
        mistakes.clear();
        if (newMistakes != null) {
            mistakes.addAll(newMistakes);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MistakeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mistake, parent, false);
        return new MistakeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MistakeViewHolder holder, int position) {
        WrongAnswer mistake = mistakes.get(position);
        Context context = holder.itemView.getContext();

        holder.tvTopic.setText(mistake.getTopicName() != null ? mistake.getTopicName() : "General");
        holder.tvQuestion.setText(mistake.getQuestionText());
        holder.tvWrongAnswer.setText("Your answer: " + mistake.getSelectedAnswer());
        holder.tvCorrectAnswer.setText("Correct answer: " + mistake.getCorrectAnswer());

        String exp = mistake.getExplanation();
        holder.tvExplanation.setText(exp != null && !exp.isEmpty() ? "Explanation: " + exp : "No explanation available.");

        // Mastery Badge
        String status = mistake.getMasteryStatus() != null ? mistake.getMasteryStatus() : "Weak";
        holder.tvMasteryBadge.setText(status);
        if ("Mastered".equalsIgnoreCase(status)) {
            holder.tvMasteryBadge.setBackgroundResource(R.drawable.bg_badge_mastered);
            holder.tvMasteryBadge.setTextColor(ContextCompat.getColor(context, R.color.mastery_mastered));
        } else if ("Practicing".equalsIgnoreCase(status)) {
            holder.tvMasteryBadge.setBackgroundResource(R.drawable.bg_badge_practicing);
            holder.tvMasteryBadge.setTextColor(ContextCompat.getColor(context, R.color.mastery_practicing));
        } else {
            holder.tvMasteryBadge.setBackgroundResource(R.drawable.bg_badge_weak);
            holder.tvMasteryBadge.setTextColor(ContextCompat.getColor(context, R.color.mastery_weak));
        }

        holder.btnRetry.setOnClickListener(v -> {
            if (retryListener != null) retryListener.onRetry(mistake);
        });
    }

    @Override
    public int getItemCount() {
        return mistakes.size();
    }

    static class MistakeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTopic;
        TextView tvMasteryBadge;
        TextView tvQuestion;
        TextView tvWrongAnswer;
        TextView tvCorrectAnswer;
        TextView tvExplanation;
        Button btnRetry;

        MistakeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTopic = itemView.findViewById(R.id.tvMistakeTopic);
            tvMasteryBadge = itemView.findViewById(R.id.tvMasteryBadge);
            tvQuestion = itemView.findViewById(R.id.tvMistakeQuestion);
            tvWrongAnswer = itemView.findViewById(R.id.tvYourWrongAnswer);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswerSnapshot);
            tvExplanation = itemView.findViewById(R.id.tvMistakeExplanation);
            btnRetry = itemView.findViewById(R.id.btnRetryQuestion);
        }
    }
}
