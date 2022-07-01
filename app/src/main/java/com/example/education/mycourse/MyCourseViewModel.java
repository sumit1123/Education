package com.example.education.mycourse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.mcq.MCQInterface;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.CourseResponse;
import com.example.education.response.MCQResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyCourseViewModel extends ViewModel {

    private MyCourseInterface myCourseInterface;

    MyCourseViewModel(MyCourseInterface mcqInterface) {
        this.myCourseInterface = mcqInterface;
    }

    public void courseApi(Activity context) {
        Call<List<CourseResponse>> call = RetrofitClient.getInstance().getMyApi().doCourseApi();
        call.enqueue(new Callback<List<CourseResponse>>() {
            @Override
            public void onResponse(Call<List<CourseResponse>> call, Response<List<CourseResponse>> response) {
                myCourseInterface.dismissProgressbar();
                myCourseInterface.setData(response.body() ,false);

            }
            @Override
            public void onFailure(Call<List<CourseResponse>> call, Throwable t) {
                myCourseInterface.dismissProgressbar();
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void myCourseApi(Activity context) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.member_id = EducationApplication.sharedPreferences.getString("user_id", "");
        Call<List<CourseResponse>> call = RetrofitClient.getInstance().getMyApi().doMyCourseApi(subjectRequest);
        call.enqueue(new Callback<List<CourseResponse>>() {
            @Override
            public void onResponse(Call<List<CourseResponse>> call, Response<List<CourseResponse>> response) {
                myCourseInterface.dismissProgressbar();
                myCourseInterface.setData(response.body(), true);
            }
            @Override
            public void onFailure(Call<List<CourseResponse>> call, Throwable t) {
                myCourseInterface.dismissProgressbar();
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
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