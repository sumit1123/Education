package com.example.education.mcq;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityExamResultBinding;
import com.example.education.databinding.ActivityMcqResultBinding;
import com.example.education.repo.request.Exam;
import com.example.education.response.MCQResponse;

import java.util.ArrayList;
import java.util.List;

public class ExamResultActivity extends BaseActivity implements MCQInterface {

    ActivityExamResultBinding activityExamResultBinding;
    MCQViewModel mcqViewModel;
    public ArrayList<Exam> examArrayList = new ArrayList<Exam>();
    int correct_answer = 0;
    int wrong_answer = 0;
    int skipped = 0;
    String exam_id = "";
    String result = "";
    String courseid;
    ExamResultAdapter examResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityExamResultBinding = ActivityExamResultBinding.inflate(getLayoutInflater());
        setContentView(activityExamResultBinding.getRoot());
        mcqViewModel = new ViewModelProvider(this, new MCQViewModelFactory(this)).get(MCQViewModel.class);

        if (getIntent() != null) {
            examArrayList = (ArrayList<Exam>) getIntent().getSerializableExtra("examResult");
            exam_id = getIntent().getStringExtra("exam_id");
            courseid = getIntent().getStringExtra("courseid");
        }

        setData();
        activityExamResultBinding.btSolution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityExamResultBinding.solutionRecyclerview.setVisibility(View.VISIBLE);
            }
        });
        activityExamResultBinding.btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putInt("exam_attempted" , EducationApplication.sharedPreferences.getInt("exam_attempted" , 0) + 1).apply();
                finish();
            }
        });
    }

    private void setData() {
        for (int i = 0; i < examArrayList.size(); i++) {
            if (examArrayList.get(i).getSelect_answer().equalsIgnoreCase("0")) {
                skipped = skipped + 1;
            } else if (examArrayList.get(i).getCorrect_answer_val().equalsIgnoreCase(examArrayList.get(i).getSelect_answer())) {
                correct_answer = correct_answer + 1;
            } else {
                wrong_answer = wrong_answer + 1;
            }
        }
        if(correct_answer >45)
        {
            result = "pass";
        }
        else
        {
            result = "fail";
        }
        setSolutionRecylerView();
        activityExamResultBinding.correctAnsVal.setText(correct_answer+"");
        activityExamResultBinding.wrongAnsVal.setText(wrong_answer+"");
        activityExamResultBinding.skippedVal.setText(skipped+"");
    }

    private void setSolutionRecylerView() {
        examResultAdapter = new ExamResultAdapter(this ,examArrayList);
        activityExamResultBinding.solutionRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityExamResultBinding.solutionRecyclerview.setLayoutManager(layoutManager);
        activityExamResultBinding.solutionRecyclerview.setAdapter(examResultAdapter);
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setMCQ(List<MCQResponse> body) {
        showLoading(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mcqViewModel.examResultApi(this, exam_id , courseid , result,
                String.valueOf(correct_answer), String.valueOf(wrong_answer) ,String.valueOf(skipped));
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(ExamResultActivity.this, "Cant go back!", Toast.LENGTH_SHORT).show();
    }
}