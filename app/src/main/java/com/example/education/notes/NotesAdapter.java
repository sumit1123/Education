package com.example.education.notes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.response.ChapterResponse;
import com.example.education.response.NotesResponse;
import com.example.education.set.SetActivity;
import com.example.education.utils.Constant;
import com.example.education.videolist.VideoListActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Response;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context mContext;
    List<NotesResponse> notesResponseList = new ArrayList<>();
    String subjectID;

    public void setData(List<NotesResponse> notesResponseList, String subjectID) {
        this.notesResponseList = notesResponseList;
        this.subjectID = subjectID;
        notifyDataSetChanged();
    }

    public void setData(List<NotesResponse> response) {
        this.notesResponseList = response;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout rl_root;
        TextView tv_title;
        ImageView img_preview;

        public ViewHolder(View itemView) {
            super(itemView);
            rl_root = itemView.findViewById(R.id.rl_root);
            tv_title = itemView.findViewById(R.id.tv_title);
            img_preview = itemView.findViewById(R.id.img_preview);
        }
    }

    public NotesAdapter(Context context) {
        mContext = context;

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
       // Picasso.get().load("").into(holder.img_chapter);
        holder.tv_title.setText(notesResponseList.get(position).pdf_name);
        holder.rl_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(EducationApplication.sharedPreferences.getString("purchased_course_id" , "")
                        .equalsIgnoreCase(notesResponseList.get(holder.getAdapterPosition()).course_id))
                {
                    Intent intent = new Intent(mContext, NotesDetailActivity.class);
                    intent.putExtra("pdffile" ,notesResponseList.get(position).supportFile);
                    mContext.startActivity(intent);
                }
                else
                if(EducationApplication.sharedPreferences.getString("ForMyCourse", "").equalsIgnoreCase("true"))
                {

                    Intent intent = new Intent(mContext, NotesDetailActivity.class);
                    intent.putExtra("pdffile" ,notesResponseList.get(position).supportFile);
                    mContext.startActivity(intent);
                }
                else
                if(notesResponseList.get(position).use_for_demo.equalsIgnoreCase("1"))
                {
                    Intent intent = new Intent(mContext, NotesDetailActivity.class);
                    intent.putExtra("pdffile" ,notesResponseList.get(position).supportFile);
                    mContext.startActivity(intent);
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
        return notesResponseList.size();
    }
}