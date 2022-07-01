package com.example.education.course;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.register.RegisterViewModel;
import com.example.education.response.CourseResponse;

import java.util.ArrayList;
import java.util.List;


public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    Activity context;
    List<CourseResponse> courseResponses = new ArrayList<>();
    RegisterViewModel registerViewModel;
    int selected_position = -1;

    public CourseListAdapter(Activity context, List<CourseResponse> courseResponses, RegisterViewModel registerViewModel) {
        this.context = context;
        this.courseResponses = courseResponses;
        this.registerViewModel = registerViewModel;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.course_adapter, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.course_name.setText(courseResponses.get(position).course_name);
        holder.course_name.setOnCheckedChangeListener(null);
        holder.course_name.setChecked(selected_position == position);
        if (holder.course_name.isChecked()) {
            EducationApplication.editor.putString("courseid", courseResponses.get(position).course_id).apply();
            EducationApplication.editor.putString("sub_course_id", courseResponses.get(position).course_id).apply();
        }

        holder.course_name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isSelected) {
                selected_position = holder.getAdapterPosition();
                notifyDataSetChanged();
            }
        });

    }


    @Override
    public int getItemCount() {
        return courseResponses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox course_name;

        public ViewHolder(View itemView) {
            super(itemView);
            course_name = itemView.findViewById(R.id.course_name);

        }
    }
}  