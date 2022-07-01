package com.example.education.chapter;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.notes.NotesActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.ChapterResponse;
import com.example.education.response.SubjectResponse;
import com.example.education.set.SetActivity;
import com.example.education.subjects.SubjectInterface;
import com.example.education.videolist.VideoListActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChapterViewModel extends ViewModel {

    private ChapterInterface chapterInterface;


    ChapterViewModel(ChapterInterface chapterInterface) {
        this.chapterInterface = chapterInterface;
    }

    public void chapterApi(Activity context, String subjectID) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.catid = "1";
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        subjectRequest.subjectid =subjectID;
        Call<List<ChapterResponse>> call = RetrofitClient.getInstance().getMyApi().doChapterApi(subjectRequest);
        call.enqueue(new Callback<List<ChapterResponse>>() {
            @Override
            public void onResponse(Call<List<ChapterResponse>> call, Response<List<ChapterResponse>> response) {
                chapterInterface.dismissProgressbar();
                chapterInterface.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<ChapterResponse>> call, Throwable t) {
                chapterInterface.dismissProgressbar();
                if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("video"))
                {
                    Intent i = new Intent(context, VideoListActivity.class);
                    i.putExtra("subjectID" ,subjectID);
                    context.startActivity(i);
                    context.finish();
                }
                else if(EducationApplication.sharedPreferences.getString("type","").equalsIgnoreCase("notes"))
                {
                    Intent i = new Intent(context, NotesActivity.class);
                    i.putExtra("subjectID" ,subjectID);
                    context.startActivity(i);
                    context.finish();
                }
                else
                {
                    Intent i = new Intent(context, SetActivity.class);
                    i.putExtra("subjectID" ,subjectID);
                    context.startActivity(i);
                    context.finish();
                }

            }
        });
    }

}