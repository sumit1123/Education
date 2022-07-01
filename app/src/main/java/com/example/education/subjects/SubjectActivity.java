package com.example.education.subjects;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivitySubjectBinding;
import com.example.education.response.SubjectResponse;
import java.util.List;

public class SubjectActivity extends BaseActivity implements SubjectInterface {

    ActivitySubjectBinding activitySubjectBinding;
    private SubjectViewModel subjectViewModel;
    SubjectsAdapter subjectsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySubjectBinding = ActivitySubjectBinding.inflate(getLayoutInflater());
        setContentView(activitySubjectBinding.getRoot());
        subjectViewModel = new ViewModelProvider(this, new SubjectViewModelFactory(this)).get(SubjectViewModel.class);
        setSubjectRecylerView();
    }

    private void setSubjectRecylerView() {
        RecyclerView recyclerView = activitySubjectBinding.subjectRecyclerview;
        subjectsAdapter = new SubjectsAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(subjectsAdapter);
        showLoading(true);
        subjectViewModel.subjectApi(this);
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setSubjects(List<SubjectResponse> body) {
        subjectsAdapter.setData(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}