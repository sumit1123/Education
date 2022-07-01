package com.example.education.set;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.chapter.ChapterInterface;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.ChapterResponse;
import com.example.education.response.SetResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SetViewModel extends ViewModel {

    private SetInterface setInterface;

    SetViewModel(SetInterface setInterface) {
        this.setInterface = setInterface;
    }

    public void setApi(Activity context, String subjectID, String topicId) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.catid = "1";
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        subjectRequest.subid =subjectID;
        subjectRequest.topic_id = topicId;
        subjectRequest.examtype= EducationApplication.sharedPreferences.getString("examtype", "");
        Call<List<SetResponse>> call = RetrofitClient.getInstance().getMyApi().doSetApi(subjectRequest);
        call.enqueue(new Callback<List<SetResponse>>() {
            @Override
            public void onResponse(Call<List<SetResponse>> call, Response<List<SetResponse>> response) {
                setInterface.dismissProgressbar();
                setInterface.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<SetResponse>> call, Throwable t) {
                setInterface.dismissProgressbar();
            }
        });
    }

}