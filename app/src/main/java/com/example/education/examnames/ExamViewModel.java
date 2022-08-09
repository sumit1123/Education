package com.example.education.examnames;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.SetResponse;
import com.example.education.set.SetInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExamViewModel extends ViewModel {

    private ExamInterface examInterface;

    ExamViewModel(ExamInterface examInterface) {
        this.examInterface = examInterface;
    }

    public void examApi() {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.member_id = EducationApplication.sharedPreferences.getString("user_id", "");
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        Call<List<SetResponse>> call = RetrofitClient.getInstance().getMyApi().doExamNameApi(subjectRequest);
        call.enqueue(new Callback<List<SetResponse>>() {
            @Override
            public void onResponse(Call<List<SetResponse>> call, Response<List<SetResponse>> response) {
                examInterface.dismissProgressbar();
                examInterface.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<SetResponse>> call, Throwable t) {
                examInterface.dismissProgressbar();
            }
        });
    }


    public void downloadResultApi(String exam_id) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.member_id = EducationApplication.sharedPreferences.getString("user_id", "");
        subjectRequest.exam_id = exam_id;
        Call<List<SetResponse>> call = RetrofitClient.getInstance().getMyApi().doTrackResultApi(subjectRequest);
        call.enqueue(new Callback<List<SetResponse>>() {
            @Override
            public void onResponse(Call<List<SetResponse>> call, Response<List<SetResponse>> response) {
                examInterface.dismissProgressbar();
                examInterface.setData(response.body());
            }

            @Override
            public void onFailure(Call<List<SetResponse>> call, Throwable t) {
                examInterface.dismissProgressbar();
            }
        });
    }
}