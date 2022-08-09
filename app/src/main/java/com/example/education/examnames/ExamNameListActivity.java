package com.example.education.examnames;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityExamNameBinding;
import com.example.education.databinding.ActivitySetBinding;
import com.example.education.response.SetResponse;
import com.example.education.set.SetAdapter;
import com.example.education.set.SetAdapterExam;
import com.example.education.set.SetInterface;
import com.example.education.set.SetViewModel;
import com.example.education.set.SetViewModelFactory;

import java.util.List;

public class ExamNameListActivity extends BaseActivity implements ExamInterface {

    ActivityExamNameBinding activityExamNameBinding;
    ExamViewModel examViewModel;
    ExamNameAdapter examNameAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityExamNameBinding = ActivityExamNameBinding.inflate(getLayoutInflater());
        setContentView(activityExamNameBinding.getRoot());
        examViewModel = new ViewModelProvider(this, new ExamViewModelFactory(this)).get(ExamViewModel.class);
        setExamRecylerView();

    }

    private void setExamRecylerView() {
        RecyclerView recyclerView = activityExamNameBinding.examRecyclerview;
        examNameAdapter = new ExamNameAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(examNameAdapter);
        showLoading(true);
        examViewModel.examApi();
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<SetResponse> body) {
        examNameAdapter.setData(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}