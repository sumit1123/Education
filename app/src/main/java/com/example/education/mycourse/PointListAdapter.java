package com.example.education.mycourse;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.buynow.PaymentActivity;
import com.example.education.databinding.AdapterPointBinding;
import com.example.education.response.CourseResponse;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;


public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.ViewHolder> {

    Activity context;
    CourseResponse courseResponse;
    ArrayList<String> stringArrayList = new ArrayList<>();

    public PointListAdapter(Activity context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdapterPointBinding adapterPointBinding = AdapterPointBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(adapterPointBinding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (stringArrayList.size() > 0) {
            holder.adapterPointBinding.tvPoint.setText(stringArrayList.get(position));
        }

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public void setData(CourseResponse courseResponse) {
        this.courseResponse = courseResponse;
        stringArrayList.clear();
        if (!courseResponse.point_one.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_one);
        }
        if (!courseResponse.point_two.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_two);
        }
        if (!courseResponse.point_three.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_three);
        }
        if (!courseResponse.point_four.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_four);
        }
        if (!courseResponse.point_five.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_five);
        }
        if (!courseResponse.point_six.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_six);
        }
        if (!courseResponse.point_seven.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_seven);
        }
        if (!courseResponse.point_eight.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_eight);
        }
        if (!courseResponse.point_nine.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_nine);
        }
        if (!courseResponse.point_ten.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_ten);
        }

        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AdapterPointBinding adapterPointBinding;

        public ViewHolder(AdapterPointBinding adapterPointBinding) {
            super(adapterPointBinding.getRoot());
            this.adapterPointBinding = adapterPointBinding;

        }
    }
}  