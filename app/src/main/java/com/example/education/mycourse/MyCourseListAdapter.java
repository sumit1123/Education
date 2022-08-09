package com.example.education.mycourse;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.buynow.PaymentActivity;
import com.example.education.register.RegisterViewModel;
import com.example.education.response.CourseResponse;
import com.example.education.utils.Constant;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MyCourseListAdapter extends RecyclerView.Adapter<MyCourseListAdapter.ViewHolder>{

    Activity context;
    List<CourseResponse> courseResponses= new ArrayList<>();
    boolean isMyCourse;

    public MyCourseListAdapter(Activity context) {
        this.context = context;
    }

    @Override  
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {  
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());  
        View listItem= layoutInflater.inflate(R.layout.mycourse_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);  
        return viewHolder;  
    }  
  
    @Override  
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.get().load(Constant.IMAGE_URL + courseResponses.get(position).course_image).into(holder.course_img);
        holder.course_name.setText(courseResponses.get(position).course_name);
        if(isMyCourse)
        {
            holder.bt_demo.setVisibility(View.GONE);
            holder.bt_buynow.setVisibility(View.GONE);
        }
        else
        {
            holder.bt_demo.setVisibility(View.VISIBLE);
            holder.bt_buynow.setVisibility(View.VISIBLE);
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMyCourse)
                {
                    EducationApplication.editor.putString("courseid", courseResponses.get(position).course_id).apply();
                    Intent demo = new Intent(context, DemoActivity.class);
                    demo.putExtra("from", "MyCourse");
                    context.startActivity(demo);
                }
            }
        });

        holder.bt_demo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("courseid", courseResponses.get(position).course_id).apply();
                Intent demo = new Intent(context, DemoActivity.class);
                demo.putExtra("position" , position);
                context.startActivity(demo);
            }
        });

        holder.bt_buynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("courseid", courseResponses.get(position).course_id).apply();
                Intent demo = new Intent(context,    PaymentActivity.class);
                demo.putExtra("courseDetail" , courseResponses.get(position));
                demo.putExtra("position" ,     position);
                context.startActivity(demo);
            }
        });
    }

    @Override
    public int getItemCount() {  
        return courseResponses.size();
    }

    public void setData(List<CourseResponse> body , boolean isMyCourse) {
        this.courseResponses = body;
        this.isMyCourse = isMyCourse;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView course_name;
        ImageView course_img;
        MaterialCardView card;
        Button bt_demo ,bt_buynow;

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