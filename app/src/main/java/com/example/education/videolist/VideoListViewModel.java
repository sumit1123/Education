package com.example.education.videolist;


import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.otp.OTPActivity;
import com.example.education.register.RegisterActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.LoginRequest;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.CourseResponse;
import com.example.education.response.LoginResponse;
import com.example.education.response.VideoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideoListViewModel extends ViewModel {

    private VideoListInterface videoListInterface;


    VideoListViewModel(VideoListInterface videoListInterface) {
        this.videoListInterface = videoListInterface;
    }

    public void videoApi(Activity context, String subjectID , String chapter_id) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.catid = "1";
        subjectRequest.subjectid = subjectID;
        subjectRequest.topic_id = chapter_id;
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        Call<List<VideoResponse>> call = RetrofitClient.getInstance().getMyApi().doVideoApi(subjectRequest);
        call.enqueue(new Callback<List<VideoResponse>>() {
            @Override
            public void onResponse(Call<List<VideoResponse>> call, Response<List<VideoResponse>> response) {
                videoListInterface.dismissProgressbar();
                videoListInterface.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<VideoResponse>> call, Throwable t) {
                videoListInterface.dismissProgressbar();
            }
        });
    }

}