package com.example.education.set;

import android.content.Context;
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
import com.example.education.videolist.VideoListActivity;

import java.util.ArrayList;
import java.util.List;


public class SetAdapterExam extends RecyclerView.Adapter<SetAdapterExam.ViewHolder> {

    private Context mContext;
    List<SetResponse> setResponses = new ArrayList<>();

    public void setData(List<SetResponse> setResponses) {
        this.setResponses = setResponses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_root;
        TextView tv_title , exam_attempted;
        Button bt_start;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_root = itemView.findViewById(R.id.rl_root);
            exam_attempted = itemView.findViewById(R.id.exam_attempted);
            tv_title = itemView.findViewById(R.id.tv_title);
            bt_start = itemView.findViewById(R.id.bt_start);
        }
    }

    public SetAdapterExam(Context context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_set_exam, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_title.setText(setResponses.get(position).exam_name);
        holder.exam_attempted.setText("exam_attempted " + EducationApplication.sharedPreferences.getInt("exam_attempted" , 0));
        holder.bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("notes"))
                {
                    Intent i = new Intent(mContext, NotesActivity.class);
                    mContext.startActivity(i);
                }
                else if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("video"))
                {
                    Intent i = new Intent(mContext, VideoListActivity.class);
                    mContext.startActivity(i);
                }
                else
                {
                    Intent i = new Intent(mContext, MCQActivity.class);
                    i.putExtra("examid", setResponses.get(holder.getAdapterPosition()).exam_id);
                    i.putExtra("examduration", setResponses.get(holder.getAdapterPosition()).exam_duration);
                    i.putExtra("title", setResponses.get(holder.getAdapterPosition()).exam_name);
                    i.putExtra("examtype", setResponses.get(holder.getAdapterPosition()).exam_type);
                    mContext.startActivity(i);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return setResponses.size();
    }
}