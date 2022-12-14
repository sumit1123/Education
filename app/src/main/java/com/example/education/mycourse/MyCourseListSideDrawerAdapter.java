package com.example.education.mycourse;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.buynow.PaymentActivity;
import com.example.education.certificate.FinalExamFormActivity;
import com.example.education.examnames.ExamNameListActivity;
import com.example.education.response.CourseResponse;
import com.example.education.utils.Constant;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyCourseListSideDrawerAdapter extends RecyclerView.Adapter<MyCourseListSideDrawerAdapter.ViewHolder> {

    Activity context;
    List<CourseResponse> courseResponses = new ArrayList<>();
    boolean isMyCourse;
    String fromcertificate ,track;

    public MyCourseListSideDrawerAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.mycourseside_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.get().load(Constant.IMAGE_URL + courseResponses.get(holder.getAdapterPosition()).course_image).placeholder(R.drawable.video_placeholder).into(holder.course_img);
        holder.course_name.setText(courseResponses.get(holder.getAdapterPosition()).course_name);
        if (isMyCourse) {
            holder.bt_demo.setVisibility(View.GONE);
            holder.bt_buynow.setVisibility(View.GONE);
        } else {
            holder.bt_demo.setVisibility(View.VISIBLE);
            holder.bt_buynow.setVisibility(View.VISIBLE);
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isMyCourse) {
                    if (fromcertificate != null && fromcertificate.equalsIgnoreCase("true")) {
                        Intent demo = new Intent(context, FinalExamFormActivity.class);
                        demo.putExtra("courseid" , courseResponses.get(holder.getAdapterPosition()).course_id);
                        context.startActivity(demo);
                    } else if(track != null && track.equalsIgnoreCase("true")){
                        EducationApplication.editor.putString("courseid", courseResponses.get(holder.getAdapterPosition()).course_id).apply();
                        Intent demo = new Intent(context, ExamNameListActivity.class);
                        context.startActivity(demo);
                    }
                    else
                    {
                        EducationApplication.editor.putString("courseid", courseResponses.get(holder.getAdapterPosition()).course_id).apply();
                        Intent demo = new Intent(context, DemoActivity.class);
                        demo.putExtra("from", "MyCourse");
                        context.startActivity(demo);
                    }

                }
            }
        });

        holder.bt_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("courseid", courseResponses.get(holder.getAdapterPosition()).course_id).apply();
                Intent demo = new Intent(context, DemoActivity.class);
                demo.putExtra("position", holder.getAdapterPosition());
                context.startActivity(demo);
            }
        });

        holder.bt_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("courseid", courseResponses.get(holder.getAdapterPosition()).course_id).apply();
                Intent demo = new Intent(context, PaymentActivity.class);
                demo.putExtra("courseDetail", courseResponses.get(holder.getAdapterPosition()));
                demo.putExtra("position" ,    holder.getAdapterPosition());
                context.startActivity(demo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return courseResponses.size();
    }

    public void setData(List<CourseResponse> body, boolean isMyCourse, String fromcertificate, String track) {
        this.courseResponses = body;
        this.fromcertificate = fromcertificate;
        this.isMyCourse = isMyCourse;
        this.track = track;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView course_name;
        ImageView course_img;
        MaterialCardView card;
        Button bt_demo, bt_buynow;

        public ViewHolder(View itemView) {
            super(itemView);
            course_name = itemView.findViewById(R.id.course_name);
            course_img = itemView.findViewById(R.id.course_img);
            card = itemView.findViewById(R.id.card);
            bt_demo = itemView.findViewById(R.id.bt_demo);
            bt_buynow = itemView.findViewById(R.id.bt_buynow);

        }
    }
}  