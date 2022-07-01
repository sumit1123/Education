package com.example.education.chapter;

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
import com.example.education.notes.NotesActivity;
import com.example.education.response.ChapterResponse;
import com.example.education.set.SetActivity;
import com.example.education.utils.Constant;
import com.example.education.videolist.VideoListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ViewHolder> {

    private Context mContext;
    List<ChapterResponse> chapterResponses = new ArrayList<>();
    String subjectID;

    public void setData(List<ChapterResponse> chapterResponses, String subjectID) {
        this.chapterResponses = chapterResponses;
        this.subjectID = subjectID;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_root;
        TextView tv_title;
        CircleImageView img_chapter;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_root = itemView.findViewById(R.id.rl_root);
            tv_title = itemView.findViewById(R.id.tv_title);
            img_chapter = itemView.findViewById(R.id.img_chapter);
        }
    }

    public ChapterAdapter(Context context) {
        mContext = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_chapter, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Picasso.get().load(Constant.IMAGE_URL + chapterResponses.get(position).topic_image).into(holder.img_chapter);
        holder.tv_title.setText(chapterResponses.get(position).topic_name);
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EducationApplication.sharedPreferences.getString("type", "").equalsIgnoreCase("video")) {
                    Intent i = new Intent(mContext, VideoListActivity.class);
                    i.putExtra("subjectID", subjectID);
                    i.putExtra("chapter_id", chapterResponses.get(position).topic_id);
                    mContext.startActivity(i);
                } else if (EducationApplication.sharedPreferences.getString("type", "").equalsIgnoreCase("notes")) {
                    Intent i = new Intent(mContext, NotesActivity.class);
                    i.putExtra("subjectID", subjectID);
                    i.putExtra("chapter_id", chapterResponses.get(position).topic_id);
                    mContext.startActivity(i);
                } else {
                    Intent i = new Intent(mContext, SetActivity.class);
                    i.putExtra("subjectID", chapterResponses.get(holder.getAdapterPosition()).subject_id);
                    i.putExtra("topicID", chapterResponses.get(holder.getAdapterPosition()).topic_id);
                    mContext.startActivity(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return chapterResponses.size();
    }
}