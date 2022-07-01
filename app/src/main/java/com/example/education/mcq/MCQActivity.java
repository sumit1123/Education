package com.example.education.mcq;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityMcqBinding;
import com.example.education.repo.request.Exam;
import com.example.education.response.MCQResponse;
import com.example.education.response.SetResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.krtkush.lineartimer.LinearTimer;

public class MCQActivity extends BaseActivity implements MCQInterface {

    ActivityMcqBinding activityMcqBinding;
    MCQViewModel mcqViewModel;
    MCQAdapter mcqAdapter;
    String examid ,examduration ,examtype, title;
    LinearLayoutManager mLayoutManager;
    RecyclerView recyclerView ,pallet_recyclerview;
    PalletAdapter palletAdapter;
    String courseid;
    public ArrayList<Exam> examArrayList = new ArrayList<Exam>();
    int sizeOfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMcqBinding = ActivityMcqBinding.inflate(getLayoutInflater());
        setContentView(activityMcqBinding.getRoot());
        mcqViewModel = new ViewModelProvider(this, new MCQViewModelFactory(this)).get(MCQViewModel.class);
        if (getIntent() != null) {
            examid = getIntent().getStringExtra("examid");
            examduration =getIntent().getStringExtra("examduration");
            examtype =getIntent().getStringExtra("examtype");
            title =getIntent().getStringExtra("title");
            courseid = getIntent().getStringExtra("courseid");
        }

        EducationApplication.editor.putInt("current_position", 0).apply();
        activityMcqBinding.tvTitle.setText(title);
        setSubjectRecylerView();

        if(examtype.equalsIgnoreCase("2"))
        {
            activityMcqBinding.tvAttemptedTimes.setVisibility(View.VISIBLE);
            activityMcqBinding.progressBar.setVisibility(View.VISIBLE);
            activityMcqBinding.tvTime.setVisibility(View.VISIBLE);
            activityMcqBinding.imgSideDrawer.setVisibility(View.VISIBLE);
            activityMcqBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            activityMcqBinding.tvAttemptedTimes.setText("");
            setCountDownTimer(examduration);
        }
        else
        {
            activityMcqBinding.tvAttemptedTimes.setVisibility(View.GONE);
            activityMcqBinding.progressBar.setVisibility(View.GONE);
            activityMcqBinding.tvTime.setVisibility(View.GONE);
            activityMcqBinding.imgSideDrawer.setVisibility(View.GONE);
            activityMcqBinding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }

        activityMcqBinding.imgSideDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMcqBinding.drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        activityMcqBinding.navView.getHeaderView(0).findViewById(R.id.bt_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MCQActivity.this, ExamResultActivity.class);
                i.putExtra("examResult" , examArrayList);
                i.putExtra("courseid" ,  courseid);
                i.putExtra("exam_id" ,  examid);
                startActivity(i);
                finish();
            }
        });

        activityMcqBinding.drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                palletAdapter.setData(examArrayList);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    private void setCountDownTimer(String duration) {
        if(!duration.equalsIgnoreCase("0.00"))
        {
            int time = (int)Float.parseFloat(duration);
            long covertToSec = Long.parseLong(String.valueOf(time)) * 60 * 1000;
            new CountDownTimer(covertToSec ,1000)
            {

                @Override
                public void onTick(long millisUntilFinished) {
                    activityMcqBinding.tvTime.setText(""+String.format("%d min, %d sec",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                }
                @Override
                public void onFinish() {
                    activityMcqBinding.tvTime.setText("Time Over!!");
                }
            }.start();
        }

    }

    private void setSubjectRecylerView() {
        examArrayList.clear();
        recyclerView = activityMcqBinding.mcqRecyclerview;
        mcqAdapter = new MCQAdapter(this ,examArrayList, activityMcqBinding.progressBar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mcqAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                setBackNextButtonVisibility();
            }

        });

        setButtonClicks();
        showLoading(true);
        mcqViewModel.mcqApi(this, examid);
    }

    private void setPalletRecylerView(List<MCQResponse> mcqResponses) {
        pallet_recyclerview = activityMcqBinding.navView.getHeaderView(0).findViewById(R.id.pallet_recyclerview);
        palletAdapter = new PalletAdapter(this ,mcqResponses, recyclerView);
        pallet_recyclerview.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 5);
        pallet_recyclerview.setLayoutManager(layoutManager);
        pallet_recyclerview.setAdapter(palletAdapter);
    }

    private void setButtonClicks() {
        activityMcqBinding.imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int currentPosition = EducationApplication.sharedPreferences.getInt("current_position", 0);
                if(currentPosition  == sizeOfList - 1)
                {
                    if(examtype.equalsIgnoreCase("1"))
                    {
                        Intent i = new Intent(MCQActivity.this, MCQResultActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        Intent i = new Intent(MCQActivity.this, ExamResultActivity.class);
                        i.putExtra("examResult" , examArrayList);
                        i.putExtra("exam_id" , examid);
                        i.putExtra("courseid" , courseid);
                        startActivity(i);
                        finish();
                    }
                }
                else
                {
                    int position = EducationApplication.sharedPreferences.getInt("current_position", 0);
                    EducationApplication.editor.putInt("current_position", position+1).apply();
                    if(examArrayList.get(position).getSelect_position() == -1)
                    {
                        examArrayList.get(position).setSkipped("1");
                    }
                    recyclerView.scrollToPosition(position+1);
                    mcqAdapter.notifyItemChanged(position+1);
                }


            }
        });

        activityMcqBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = EducationApplication.sharedPreferences.getInt("current_position", 0);
                EducationApplication.editor.putInt("current_position", position-1).apply();
                recyclerView.scrollToPosition(position-1);
                mcqAdapter.notifyItemChanged(position-1);

            }
        });
    }

    private void setBackNextButtonVisibility() {
        int currentPosition = EducationApplication.sharedPreferences.getInt("current_position", 0);
        if (currentPosition  == 0) {
            activityMcqBinding.imgBack.setVisibility(View.GONE);
            activityMcqBinding.imgNext.setVisibility(View.VISIBLE);
        } else if (currentPosition  == sizeOfList - 1) {
            activityMcqBinding.imgBack.setVisibility(View.VISIBLE);
            activityMcqBinding.imgNext.setVisibility(View.VISIBLE);
        } else {
            activityMcqBinding.imgBack.setVisibility(View.VISIBLE);
            activityMcqBinding.imgNext.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setMCQ(List<MCQResponse> body) {
        showLoading(false);
        sizeOfList = body.size();
        mcqAdapter.setData(body, recyclerView ,examtype);
        for (int i = 0; i <body.size() ; i++) {
            Exam exam = new Exam();
            exam.setSelect_position(-1);
            exam.setQuesiton(body.get(i).question);
            exam.setSelect_answer("0");
            if(body.get(i).correct_option.equalsIgnoreCase("1"))
            {
                exam.setCorrect_answer_val(body.get(i).option1);
            }
            else if(body.get(i).correct_option.equalsIgnoreCase("2"))
            {
                exam.setCorrect_answer_val(body.get(i).option2);
            }
            else if(body.get(i).correct_option.equalsIgnoreCase("3"))
            {
                exam.setCorrect_answer_val(body.get(i).option3);
            }
            else if(body.get(i).correct_option.equalsIgnoreCase("4"))
            {
                exam.setCorrect_answer_val(body.get(i).option4);
            }

            exam.setCorrect_answer(body.get(i).correct_option);
            examArrayList.add(exam);
        }
        setBackNextButtonVisibility();
        setPalletRecylerView(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}