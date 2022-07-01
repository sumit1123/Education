package com.example.education.videolist;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ct7ct7ct7.androidvimeoplayer.view.VimeoPlayerActivity;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityVideolistBinding;
import com.example.education.response.VideoResponse;

import java.util.ArrayList;
import java.util.List;


public class VideoListActivity extends BaseActivity implements VideoListInterface {

    ActivityVideolistBinding activityVideolistBinding;
    private VideoListViewModel videoListViewModel;
    VideoListAdapter videoListAdapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    String subjectID ,chapter_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityVideolistBinding = ActivityVideolistBinding.inflate(getLayoutInflater());
        setContentView(activityVideolistBinding.getRoot());
        videoListViewModel = new ViewModelProvider(this, new VideoListViewModelFactory(this)).get(VideoListViewModel.class);
        if(getIntent() != null)
        {
            subjectID = getIntent().getStringExtra("subjectID");
            chapter_id = getIntent().getStringExtra("chapter_id");
        }
        setSubjectRecylerView();
        getLifecycle().addObserver(activityVideolistBinding.vimeoPlayer);
        stringArrayList.add("Wordpress Introduction");
        stringArrayList.add("Install Xampp & Wamp");
        stringArrayList.add("Install Wordpress");
        stringArrayList.add("Overview Of Wordpress Dashboard");

        activityVideolistBinding.btVimeoFullsceen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(VimeoPlayerActivity.createIntent(VideoListActivity.this, VimeoPlayerActivity.REQUEST_ORIENTATION_LANDSCAPE, activityVideolistBinding.vimeoPlayer), 10000);
            }
        });

    }

    private void setSubjectRecylerView() {
        RecyclerView recyclerView = activityVideolistBinding.videoRecyclerview;
        videoListAdapter = new VideoListAdapter(this ,stringArrayList ,activityVideolistBinding.vimeoPlayer, activityVideolistBinding.btVimeoFullsceen);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(videoListAdapter);
    }

    @Override
    public void dismissProgressbar() {
        activityVideolistBinding.tvRecord.setVisibility(View.VISIBLE);
        showLoading(false);
    }

    @Override
    public void setData(List<VideoResponse> body) {
        activityVideolistBinding.tvRecord.setVisibility(View.GONE);
        videoListAdapter.setData(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoListViewModel.videoApi(this ,subjectID ,chapter_id);
    }
}