package com.example.education.set;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.mcq.MCQActivity;
import com.example.education.notes.NotesActivity;
import com.example.education.notes.NotesDetailActivity;
import com.example.education.response.SetResponse;

import java.util.ArrayList;
import java.util.List;


public class SetAdapter extends RecyclerView.Adapter<SetAdapter.ViewHolder> {

    private Activity mContext;
    List<SetResponse> setResponses = new ArrayList<>();

    public void setData(List<SetResponse> setResponses) {
        this.setResponses = setResponses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_root;
        TextView tv_title;
        Button bt_start;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_root = itemView.findViewById(R.id.rl_root);
            tv_title = itemView.findViewById(R.id.tv_title);
            bt_start = itemView.findViewById(R.id.bt_start);
        }
    }

    public SetAdapter(Activity context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_set, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_title.setText(setResponses.get(position).exam_name);
        holder.bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EducationApplication.sharedPreferences.getString("purchased_course_id" , "")
                        .equalsIgnoreCase(setResponses.get(holder.getAdapterPosition()).course_id))
                {
                    if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("notes"))
                    {
                        Intent i = new Intent(mContext, NotesActivity.class);
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
                        mContext.finish();
                    }
                }
                else
                if(EducationApplication.sharedPreferences.getString("ForMyCourse" , "").equalsIgnoreCase("true"))
                {
                    if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("notes"))
                    {
                        Intent i = new Intent(mContext, NotesActivity.class);
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
                        mContext.finish();
                    }
                }
                else
                if(setResponses.get(holder.getAdapterPosition()).use_for_demo.equalsIgnoreCase("1"))
                {
                    if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("notes"))
                    {
                        Intent i = new Intent(mContext, NotesActivity.class);
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
                        mContext.finish();
                    }

                }
                else
                {
                    showDialog();
                }

            }
        });
    }

    public void showDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Purchase this course first");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return setResponses.size();
    }
}