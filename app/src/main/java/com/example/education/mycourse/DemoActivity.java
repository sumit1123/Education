package com.example.education.mycourse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityDemoBinding;
import com.example.education.response.CourseResponse;
import com.example.education.subjects.SubjectActivity;

import java.util.List;

public class DemoActivity extends BaseActivity implements MyCourseInterface {

    ActivityDemoBinding activityDemoBinding;
    MyCourseViewModel myCourseViewModel;
    int position = 0;
    PointListAdapter pointListAdapter;
    String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityDemoBinding = ActivityDemoBinding.inflate(getLayoutInflater());
        setContentView(activityDemoBinding.getRoot());
        myCourseViewModel = new ViewModelProvider(this, new MyCourseViewModelFactory(this)).get(MyCourseViewModel.class);

        if (getIntent() != null) {
            position = getIntent().getIntExtra("position", 0);
            from = getIntent().getStringExtra("from");
        }
        activityDemoBinding.btExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("examtype", "2").apply();
                EducationApplication.editor.putString("type", "exam").apply();
                Intent i = new Intent(DemoActivity.this, SubjectActivity.class);
                startActivity(i);
            }
        });


        activityDemoBinding.btMcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("examtype", "1").apply();
                EducationApplication.editor.putString("type", "mcq").apply();
                Intent i = new Intent(DemoActivity.this, SubjectActivity.class);
                startActivity(i);
            }
        });

        activityDemoBinding.btNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("type", "notes").apply();
                Intent i = new Intent(DemoActivity.this, SubjectActivity.class);
                startActivity(i);
            }
        });

        activityDemoBinding.btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("type", "video").apply();
                Intent i = new Intent(DemoActivity.this, SubjectActivity.class);
                startActivity(i);
            }
        });

        setRecyclerView();

    }

    private void setRecyclerView() {
        RecyclerView recyclerView = activityDemoBinding.pointRecyclerview;
        pointListAdapter = new PointListAdapter(this);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this ,2);
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL ,false);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pointListAdapter);
    }


    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<CourseResponse> body, boolean b) {
        pointListAdapter.setData(body.get(position));
    }

    @Override
    protected void onResume() {
        super.onResume();
        myCourseViewModel.courseApi(this);
    }
}