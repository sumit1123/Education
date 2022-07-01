package com.example.education.subjects;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.R;
import com.example.education.chapter.ChapterActivity;
import com.example.education.response.SubjectResponse;
import com.example.education.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    private Context mContext;
    List<SubjectResponse> subjectResponses = new ArrayList<>();

    public void setData(List<SubjectResponse> subjectResponses) {
        this.subjectResponses = subjectResponses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_root;
        TextView tv_title;
        CircleImageView img_subject_view;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_root = itemView.findViewById(R.id.rl_root);
            tv_title = itemView.findViewById(R.id.tv_title);
            img_subject_view = itemView.findViewById(R.id.img_subject_view);
        }
    }

    public SubjectsAdapter(Context context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_subject, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.get().load(Constant.IMAGE_URL + subjectResponses.get(position).subject_image).into(holder.img_subject_view);
        holder.tv_title.setText(subjectResponses.get(position).subject_name);
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, ChapterActivity.class);
                i.putExtra("subjectID", subjectResponses.get(holder.getAdapterPosition()).subject_id);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subjectResponses.size();
    }
}