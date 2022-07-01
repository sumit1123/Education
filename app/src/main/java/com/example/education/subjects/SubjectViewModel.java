package com.example.education.subjects;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.login.LoginInterface;
import com.example.education.otp.OTPActivity;
import com.example.education.register.RegisterActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.LoginRequest;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.LoginResponse;
import com.example.education.response.SubjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubjectViewModel extends ViewModel {

    private SubjectInterface subjectInterface;


    SubjectViewModel(SubjectInterface loginInterface) {
        this.subjectInterface = loginInterface;
    }

    public void subjectApi(Activity context) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.catid = "1";
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        Call<List<SubjectResponse>> call = RetrofitClient.getInstance().getMyApi().doSubjectApi(subjectRequest);
        call.enqueue(new Callback<List<SubjectResponse>>() {
            @Override
            public void onResponse(Call<List<SubjectResponse>> call, Response<List<SubjectResponse>> response) {
                subjectInterface.dismissProgressbar();
                subjectInterface.setSubjects(response.body());
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {
                subjectInterface.dismissProgressbar();
            }
        });
    }

}