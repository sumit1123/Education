package com.example.education.set;

import android.os.Bundle;
import android.view.WindowManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivitySetBinding;
import com.example.education.response.SetResponse;
import java.util.List;

public class SetActivity extends BaseActivity implements SetInterface {

    ActivitySetBinding activitySetBinding;
    SetViewModel setViewModel;
    SetAdapter setAdapter;
    SetAdapterExam setAdapterExam;
    String subjectID, topicID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySetBinding = ActivitySetBinding.inflate(getLayoutInflater());
        setContentView(activitySetBinding.getRoot());
        setViewModel = new ViewModelProvider(this, new SetViewModelFactory(this)).get(SetViewModel.class);
        if (getIntent() != null) {
            subjectID = getIntent().getStringExtra("subjectID");
            topicID = getIntent().getStringExtra("topicID");
        }
        setSubjectRecylerView();

    }

    private void setSubjectRecylerView() {

        if(EducationApplication.sharedPreferences.getString("type", "").equalsIgnoreCase("exam"))
        {
            RecyclerView recyclerView = activitySetBinding.subjectRecyclerview;
            setAdapterExam = new SetAdapterExam(this);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(setAdapterExam);
            showLoading(true);
            setViewModel.setApi(this, subjectID,topicID);
        }
        else
        {
            RecyclerView recyclerView = activitySetBinding.subjectRecyclerview;
            setAdapter = new SetAdapter(this);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(setAdapter);
            showLoading(true);
            setViewModel.setApi(this, subjectID,topicID);
        }

    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<SetResponse> body) {
        if(EducationApplication.sharedPreferences.getString("type", "").equalsIgnoreCase("exam"))
        {
            setAdapterExam.setData(body);
        }
        else
        {
            setAdapter.setData(body);
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}