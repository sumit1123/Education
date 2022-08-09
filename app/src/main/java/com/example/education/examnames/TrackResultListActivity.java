package com.example.education.examnames;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityExamNameBinding;
import com.example.education.databinding.ActivityTrackResultBinding;
import com.example.education.response.SetResponse;

import java.util.List;

public class TrackResultListActivity extends BaseActivity implements ExamInterface {

    ActivityTrackResultBinding activityTrackResultBinding;
    ExamViewModel examViewModel;
    TrackResultAdapter trackResultAdapter;
    String exam_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityTrackResultBinding = ActivityTrackResultBinding.inflate(getLayoutInflater());
        setContentView(activityTrackResultBinding.getRoot());
        examViewModel = new ViewModelProvider(this, new ExamViewModelFactory(this)).get(ExamViewModel.class);
        try {
            exam_id = getIntent().getStringExtra("exam_id");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        setExamRecylerView();

    }

    private void setExamRecylerView() {
        RecyclerView recyclerView = activityTrackResultBinding.trackRecyclerview;
        trackResultAdapter = new TrackResultAdapter(this);
        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(trackResultAdapter);
        showLoading(true);
        examViewModel.downloadResultApi(exam_id);
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<SetResponse> body) {
        trackResultAdapter.setData(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}