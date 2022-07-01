package com.example.education.mcq;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.ahp.AnimateHorizontalProgressBar;
import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.repo.request.Exam;
import com.example.education.repo.request.MCQ;
import com.example.education.response.MCQResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ExamResultAdapter extends RecyclerView.Adapter<ExamResultAdapter.ViewHolder> {

    private Context mContext;
    ArrayList<Exam> examArrayList = new ArrayList<>();
    ViewHolder holder;
    int id= 0;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_question, correct_answer, select_answer;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_question = itemView.findViewById(R.id.tv_question);
            correct_answer = itemView.findViewById(R.id.correct_answer);
            select_answer = itemView.findViewById(R.id.select_answer);

        }
    }

    public ExamResultAdapter(Context context, ArrayList<Exam> examArrayList) {
        mContext = context;
        this.examArrayList = examArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_examresult, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        this.holder = holder;
        id = position +1;
        holder.tv_question.setText(id + ". " +examArrayList.get(position).getQuesiton());
        holder.correct_answer.setText("Correct Ans:- " + examArrayList.get(position).getCorrect_answer_val());
        holder.select_answer.setText("Your Ans:- "  +examArrayList.get(position).getSelect_answer());
    }

    @Override
    public int getItemCount() {
        return examArrayList.size();
    }
}