package com.example.education.mycourse;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityMycourseBinding;
import com.example.education.response.CourseResponse;

import java.util.List;

public class MyCourseActivity extends BaseActivity implements MyCourseInterface {

    ActivityMycourseBinding activityMycourseBinding;
    MyCourseViewModel myCourseViewModel;
    MyCourseListSideDrawerAdapter courseListAdapter;
    String examid, examduration, examtype;
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView;
    String ForMyCourse ,fromcertificate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMycourseBinding = ActivityMycourseBinding.inflate(getLayoutInflater());
        setContentView(activityMycourseBinding.getRoot());
        myCourseViewModel = new ViewModelProvider(this, new MyCourseViewModelFactory(this)).get(MyCourseViewModel.class);
        if (getIntent() != null) {
            examid = getIntent().getStringExtra("examid");
            examduration = getIntent().getStringExtra("examduration");
            examtype = getIntent().getStringExtra("examtype");
            ForMyCourse = getIntent().getStringExtra("ForMyCourse");
            fromcertificate = getIntent().getStringExtra("fromcertificate");
        }
        if(ForMyCourse.equalsIgnoreCase("true"))
        {
            activityMycourseBinding.tvTitle.setText("My Courses");
        }
        else
        {
            activityMycourseBinding.tvTitle.setText("All Courses");
        }
        setSubjectRecylerView();
    }

    private void setSubjectRecylerView() {
        recyclerView = activityMycourseBinding.courseRecyclerview;
        courseListAdapter = new MyCourseListSideDrawerAdapter(this);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(courseListAdapter);
        showLoading(true);
        if (ForMyCourse.equalsIgnoreCase("true")) {
            myCourseViewModel.myCourseApi(this);
        } else {
            myCourseViewModel.courseApi(this);
        }

    }


    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<CourseResponse> body, boolean b) {
        showLoading(false);
        courseListAdapter.setData(body, b ,fromcertificate);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}