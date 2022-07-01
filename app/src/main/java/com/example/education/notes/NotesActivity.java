package com.example.education.notes;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityNotesBinding;
import com.example.education.response.NotesResponse;

import java.util.List;

import retrofit2.Response;

public class NotesActivity extends BaseActivity implements NotesInterface {

    ActivityNotesBinding activityNotesBinding;
    private NotesViewModel notesViewModel;
    String subjectID ,chapter_id;
    NotesAdapter notesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityNotesBinding = ActivityNotesBinding.inflate(getLayoutInflater());
        setContentView(activityNotesBinding.getRoot());
        notesViewModel = new ViewModelProvider(this, new NotesViewModelFactory(this)).get(NotesViewModel.class);
        subjectID = getIntent().getStringExtra("subjectID");
        chapter_id = getIntent().getStringExtra("chapter_id");
        setNotesRecylerView();
        //activityNotesBinding.pdfView.fromAsset("pgdca.pdf").load();

    }

    private void setNotesRecylerView() {
        RecyclerView recyclerView = activityNotesBinding.notesRecyclerview;
        notesAdapter = new NotesAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(notesAdapter);
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setNotes(Response<List<NotesResponse>> response) {
        notesAdapter.setData(response.body());
    }

    @Override
    protected void onResume() {
        super.onResume();
        notesViewModel.notesApi(this ,subjectID ,chapter_id);
    }
}