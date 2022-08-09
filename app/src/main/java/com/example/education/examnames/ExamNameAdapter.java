package com.example.education.examnames;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.mcq.MCQActivity;
import com.example.education.notes.NotesActivity;
import com.example.education.response.SetResponse;

import java.util.ArrayList;
import java.util.List;

public class ExamNameAdapter extends RecyclerView.Adapter<ExamNameAdapter.ViewHolder> {

    private Activity mContext;
    List<SetResponse> setResponses = new ArrayList<>();

    public void setData(List<SetResponse> setResponses) {
        this.setResponses = setResponses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_root;
        TextView tv_title;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_root = itemView.findViewById(R.id.rl_root);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

    public ExamNameAdapter(Activity context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_exam_name, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_title.setText(setResponses.get(position).exam_name);
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext , TrackResultListActivity.class);
                i.putExtra("exam_id" , setResponses.get(holder.getAdapterPosition()).exam_id);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return setResponses.size();
    }
}