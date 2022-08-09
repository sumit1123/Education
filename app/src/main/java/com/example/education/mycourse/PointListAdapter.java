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
import com.example.education.utils.Constant;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.ViewHolder> {

    Activity context;
    CourseResponse courseResponse;
    ArrayList<String> stringArrayList = new ArrayList<>();
    ArrayList<String> urlArrayList = new ArrayList<>();

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
        if(urlArrayList.size()>0)
        {
            if(!urlArrayList.get(position).equalsIgnoreCase(""))
            {
                Picasso.get().load(Constant.IMAGE_URL + urlArrayList.get(position)).into(holder.adapterPointBinding.imgPoint);
            }

        }

    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public void setData(CourseResponse courseResponse) {
        this.courseResponse = courseResponse;
        stringArrayList.clear();
        urlArrayList.clear();
        if (!courseResponse.point_one.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_one);
            urlArrayList.add(courseResponse.pointone_image);
        }
        if (!courseResponse.point_two.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_two);
            urlArrayList.add(courseResponse.pointtwo_image);
        }
        if (!courseResponse.point_three.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_three);
            urlArrayList.add(courseResponse.pointthree_image);
        }
        if (!courseResponse.point_four.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_four);
            urlArrayList.add(courseResponse.pointfour_image);
        }
        if (!courseResponse.point_five.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_five);
            urlArrayList.add(courseResponse.pointfive_image);
        }
        if (!courseResponse.point_six.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_six);
            urlArrayList.add(courseResponse.pointsix_image);
        }
        if (!courseResponse.point_seven.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_seven);
            urlArrayList.add(courseResponse.pointseven_image);
        }
        if (!courseResponse.point_eight.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_eight);
            urlArrayList.add(courseResponse.pointeight_image);
        }
        if (!courseResponse.point_nine.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_nine);
            urlArrayList.add(courseResponse.pointnine_image);
        }
        if (!courseResponse.point_ten.equalsIgnoreCase("")) {
            stringArrayList.add(courseResponse.point_ten);
            urlArrayList.add(courseResponse.pointten_image);
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