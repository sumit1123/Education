package com.example.education.mcq;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.MCQResponse;
import com.example.education.response.SetResponse;
import com.example.education.set.SetInterface;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MCQViewModel extends ViewModel {

    private MCQInterface mcqInterface;


    MCQViewModel(MCQInterface mcqInterface) {
        this.mcqInterface = mcqInterface;
    }

    public void mcqApi(Activity context, String examID) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.examid = examID;
      //  subjectRequest.examtype = EducationApplication.sharedPreferences.getString("examtype", "");
        Call<List<MCQResponse>> call = RetrofitClient.getInstance().getMyApi().doMCqApi(subjectRequest);
        call.enqueue(new Callback<List<MCQResponse>>() {
            @Override
            public void onResponse(Call<List<MCQResponse>> call, Response<List<MCQResponse>> response) {
               mcqInterface.dismissProgressbar();
                mcqInterface.setMCQ(response.body());
            }

            @Override
            public void onFailure(Call<List<MCQResponse>> call, Throwable t) {
                mcqInterface.dismissProgressbar();
            }
        });
    }

    public void examResultApi(Activity context, String examID, String course_id , String result , String correct_ans,
                              String incorrect_ans , String skip_que) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.exam_id = examID;
        subjectRequest.member_id = EducationApplication.sharedPreferences.getString("user_id" , "");
        subjectRequest.course_id = course_id;
        subjectRequest.result = result;
        subjectRequest.correct_ans =correct_ans;
        subjectRequest.incorrect_ans =incorrect_ans;
        subjectRequest.skip_que =skip_que;
        Call<List<MCQResponse>> call = RetrofitClient.getInstance().getMyApi().doExamResultApi(subjectRequest);
        call.enqueue(new Callback<List<MCQResponse>>() {
            @Override
            public void onResponse(Call<List<MCQResponse>> call, Response<List<MCQResponse>> response) {
                mcqInterface.dismissProgressbar();
            }

            @Override
            public void onFailure(Call<List<MCQResponse>> call, Throwable t) {
                mcqInterface.dismissProgressbar();
            }
        });
    }

    public void showDismissTimer(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("You time is done now");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                context.finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }
}