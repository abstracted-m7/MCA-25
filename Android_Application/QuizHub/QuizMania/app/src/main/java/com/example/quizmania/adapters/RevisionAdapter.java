package com.example.quizmania.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quizmania.R;
import com.example.quizmania.model.RevisionQuiz;

import java.util.ArrayList;
import java.util.List;

public class RevisionAdapter extends RecyclerView.Adapter<RevisionAdapter.RevisionViewHolder> {

    public interface OnRevisionClickListener {
        void onRevisionClick(RevisionQuiz quiz);
    }

    private final List<RevisionQuiz> revisionList = new ArrayList<>();
    private final OnRevisionClickListener listener;

    public RevisionAdapter(OnRevisionClickListener listener) {
        this.listener = listener;
    }

    public void setRevisionList(List<RevisionQuiz> newList) {
        revisionList.clear();
        if (newList != null) {
            revisionList.addAll(newList);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RevisionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_revision, parent, false);
        return new RevisionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RevisionViewHolder holder, int position) {
        RevisionQuiz rq = revisionList.get(position);

        String subject = rq.getSubjectName() != null ? rq.getSubjectName() : "Subject";
        holder.tvTitle.setText(subject + " Personalized Revision");
        holder.tvStatus.setText(rq.getStatus() != null ? rq.getStatus() : "READY");

        int qCount = rq.getQuestions() != null ? rq.getQuestions().size() : (rq.getQuestionIds() != null ? rq.getQuestionIds().size() : 0);
        holder.tvQCount.setText(qCount + " Unique Questions");

        if (rq.getWeakTopics() != null && !rq.getWeakTopics().isEmpty()) {
            StringBuilder sb = new StringBuilder("Weak topics: ");
            for (int i = 0; i < rq.getWeakTopics().size(); i++) {
                sb.append(rq.getWeakTopics().get(i));
                if (i < rq.getWeakTopics().size() - 1) sb.append(", ");
            }
            holder.tvWeakTopics.setText(sb.toString());
            holder.tvWeakTopics.setVisibility(View.VISIBLE);
        } else {
            holder.tvWeakTopics.setVisibility(View.GONE);
        }

        holder.btnStart.setOnClickListener(v -> {
            if (listener != null) listener.onRevisionClick(rq);
        });
    }

    @Override
    public int getItemCount() {
        return revisionList.size();
    }

    static class RevisionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvStatus;
        TextView tvWeakTopics;
        TextView tvQCount;
        Button btnStart;

        RevisionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvRevisionTitle);
            tvStatus = itemView.findViewById(R.id.tvRevisionStatus);
            tvWeakTopics = itemView.findViewById(R.id.tvWeakTopicsList);
            tvQCount = itemView.findViewById(R.id.tvRevisionQCount);
            btnStart = itemView.findViewById(R.id.btnStartRevision);
        }
    }
}
