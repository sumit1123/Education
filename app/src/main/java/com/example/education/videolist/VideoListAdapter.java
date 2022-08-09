package com.example.education.videolist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerView;
import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.response.CourseResponse;
import com.example.education.response.VideoResponse;
import com.example.education.utils.Constant;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.ViewHolder> {

    private Context mContext;
    VimeoPlayerView vimeoPlayerView;
    List<VideoResponse> videoResponses = new ArrayList();
    ImageView btVimeoFullsceen;

    public void setData(List<VideoResponse> body) {
        this.videoResponses = body;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_title;
        ImageView img_preview ,img_lock;
        MaterialCardView card_root;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            img_preview = itemView.findViewById(R.id.img_preview);
            img_lock = itemView.findViewById(R.id.img_lock);
            card_root = itemView.findViewById(R.id.card_root);
        }
    }

    public VideoListAdapter(Context context, ArrayList<String> stringArrayList, VimeoPlayerView vimeoPlayerView, ImageView btVimeoFullsceen) {
        mContext = context;
        this.vimeoPlayerView = vimeoPlayerView;
        this.btVimeoFullsceen = btVimeoFullsceen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_video, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_title.setText(videoResponses.get(position).videoTitle);
        Picasso.get().load(Constant.IMAGE_URL+videoResponses.get(position).preview_image).into(holder.img_preview);
        if(videoResponses.get(holder.getAdapterPosition()).use_for_demo.equalsIgnoreCase("1")){
            holder.img_lock.setVisibility(View.GONE);
        }
        else
        {
            holder.img_lock.setVisibility(View.VISIBLE);
        }
        holder.card_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EducationApplication.sharedPreferences.getString("purchased_course_id" , "")
                        .equalsIgnoreCase(videoResponses.get(holder.getAdapterPosition()).course_id))
                {
                    vimeoPlayerView.setVisibility(View.VISIBLE);
                    btVimeoFullsceen.setVisibility(View.VISIBLE);
                    vimeoPlayerView.initialize(true, Integer.parseInt(videoResponses.get(holder.getAdapterPosition()).videoId) ,
                            videoResponses.get(holder.getAdapterPosition()).hash_key ,videoResponses.get(holder.getAdapterPosition()).video_url);
                    vimeoPlayerView.setMenuVisibility(true);
                    vimeoPlayerView.setFullscreenVisibility(true);
                }
                else
                if(EducationApplication.sharedPreferences.getString("ForMyCourse", "").equalsIgnoreCase("true"))
                {
                    vimeoPlayerView.setVisibility(View.VISIBLE);
                    btVimeoFullsceen.setVisibility(View.VISIBLE);
                    vimeoPlayerView.initialize(true, Integer.parseInt(videoResponses.get(holder.getAdapterPosition()).videoId));
                    vimeoPlayerView.setMenuVisibility(true);
                    vimeoPlayerView.setFullscreenVisibility(true);
                }
                else if(videoResponses.get(holder.getAdapterPosition()).use_for_demo.equalsIgnoreCase("1"))
                {
                    vimeoPlayerView.setVisibility(View.VISIBLE);
                    btVimeoFullsceen.setVisibility(View.VISIBLE);
                    vimeoPlayerView.initialize(true, Integer.parseInt(videoResponses.get(holder.getAdapterPosition()).videoId));
                    vimeoPlayerView.setMenuVisibility(true);
                    vimeoPlayerView.setFullscreenVisibility(true);
                }
                else
                {
                    showDialog();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return videoResponses.size();
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


}