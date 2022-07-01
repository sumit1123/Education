package com.example.education.chapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityChapterBinding;
import com.example.education.databinding.ActivitySubjectBinding;
import com.example.education.mcq.MCQActivity;
import com.example.education.notes.NotesActivity;
import com.example.education.response.ChapterResponse;
import com.example.education.response.SubjectResponse;
import com.example.education.subjects.SubjectInterface;
import com.example.education.subjects.SubjectViewModel;
import com.example.education.subjects.SubjectViewModelFactory;
import com.example.education.subjects.SubjectsAdapter;
import com.example.education.videolist.VideoListActivity;

import java.util.List;

public class ChapterActivity extends BaseActivity implements ChapterInterface {

    ActivityChapterBinding activityChapterBinding;
    ChapterViewModel chapterViewModel;
    ChapterAdapter chapterAdapter;
    String subjectID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityChapterBinding = ActivityChapterBinding.inflate(getLayoutInflater());
        setContentView(activityChapterBinding.getRoot());
        chapterViewModel = new ViewModelProvider(this, new ChapterViewModelFactory(this)).get(ChapterViewModel.class);
        if (getIntent() != null) {
            subjectID = getIntent().getStringExtra("subjectID");
        }
        setSubjectRecylerView();

    }

    private void setSubjectRecylerView() {
        RecyclerView recyclerView = activityChapterBinding.subjectRecyclerview;
        chapterAdapter = new ChapterAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(chapterAdapter);
        showLoading(true);
        chapterViewModel.chapterApi(this, subjectID);
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<ChapterResponse> body) {
        if (body.size() <= 0) {
            if (EducationApplication.sharedPreferences.getString("type", "").equalsIgnoreCase("video")) {
                Intent i = new Intent(this, VideoListActivity.class);
                i.putExtra("subjectID" ,subjectID);
                startActivity(i);
            } else if (EducationApplication.sharedPreferences.getString("type", "").equalsIgnoreCase("notes")) {
                Intent i = new Intent(this, NotesActivity.class);
                startActivity(i);
            }
        } else {
            chapterAdapter.setData(body , subjectID);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}