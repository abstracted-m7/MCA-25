package com.example.quizmania.view.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmania.R;
import com.example.quizmania.model.Quiz;

import java.util.ArrayList;
import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {

    private List<Quiz> quizList = new ArrayList<>();
    private OnQuizClickListener listener;
    private OnQuizDeleteListener deleteListener;
    private String currentTeacherId;

    public interface OnQuizClickListener {
        void onQuizClick(Quiz quiz);
    }

    public interface OnQuizDeleteListener {
        void onQuizDelete(Quiz quiz);
    }

    public void setCurrentTeacherId(String teacherId) {
        this.currentTeacherId = teacherId;
    }

    public void setOnQuizDeleteListener(OnQuizDeleteListener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setQuizList(List<Quiz> quizList, OnQuizClickListener listener) {
        this.quizList = quizList != null ? quizList : new ArrayList<>();
        this.listener = listener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_quiz, parent, false);
        return new QuizViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizViewHolder holder, int position) {
        Quiz quiz = quizList.get(position);
        holder.tvTitle.setText(quiz.getTitle());
        holder.tvDescription.setText(quiz.getDescription() != null ? quiz.getDescription() : "Comprehensive practice quiz.");

        if (holder.tvSubjectBadge != null) {
            String subject = quiz.getSubjectName() != null ? quiz.getSubjectName() : "General";
            holder.tvSubjectBadge.setText(subject);
        }

        if (holder.tvDifficulty != null) {
            holder.tvDifficulty.setText(quiz.getDifficulty() != null ? quiz.getDifficulty() : "Intermediate");
        }

        if (holder.tvQuestionCount != null) {
            int qCount = quiz.getQuestions() != null && !quiz.getQuestions().isEmpty()
                    ? quiz.getQuestions().size()
                    : (quiz.getQuestionIds() != null && !quiz.getQuestionIds().isEmpty() ? quiz.getQuestionIds().size() : 10);
            holder.tvQuestionCount.setText(qCount + " Questions");
        }

        if (holder.tvDuration != null) {
            holder.tvDuration.setText(quiz.getDurationMinutes() + " mins");
        }

        // Only the teacher who created this quiz can see and delete it
        boolean isCreator = currentTeacherId != null && currentTeacherId.equals(quiz.getCreatedBy());
        if (holder.btnDeleteQuiz != null) {
            if (isCreator && deleteListener != null) {
                holder.btnDeleteQuiz.setVisibility(View.VISIBLE);
                holder.btnDeleteQuiz.setOnClickListener(v -> deleteListener.onQuizDelete(quiz));
            } else {
                holder.btnDeleteQuiz.setVisibility(View.GONE);
            }
        }

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onQuizClick(quiz);
        });
    }

    @Override
    public int getItemCount() {
        return quizList.size();
    }

    static class QuizViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvSubjectBadge;
        TextView tvDifficulty;
        TextView tvQuestionCount;
        TextView tvDuration;
        ImageButton btnDeleteQuiz;

        public QuizViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvQuizTitle);
            tvDescription = itemView.findViewById(R.id.tvQuizDescription);
            tvSubjectBadge = itemView.findViewById(R.id.tvSubjectBadge);
            tvDifficulty = itemView.findViewById(R.id.tvDifficulty);
            tvQuestionCount = itemView.findViewById(R.id.tvQuestionCount);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            btnDeleteQuiz = itemView.findViewById(R.id.btnDeleteQuiz);
        }
    }
}
