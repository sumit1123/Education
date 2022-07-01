package com.example.education.notes;

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
import com.example.education.response.NotesResponse;
import com.example.education.response.SubjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotesViewModel extends ViewModel {

    private NotesInterface notesInterface;

    NotesViewModel(NotesInterface notesInterface) {
        this.notesInterface = notesInterface;
    }

    public void notesApi(Activity context, String subjectID ,String chapter_id) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.catid = "1";
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        subjectRequest.subjectid = subjectID;
        subjectRequest.topic_id =chapter_id;
        Call<List<NotesResponse>> call = RetrofitClient.getInstance().getMyApi().doNotesApi(subjectRequest);
        call.enqueue(new Callback<List<NotesResponse>>() {
            @Override
            public void onResponse(Call<List<NotesResponse>> call, Response<List<NotesResponse>> response) {
                notesInterface.dismissProgressbar();
                notesInterface.setNotes(response);
            }

            @Override
            public void onFailure(Call<List<NotesResponse>> call, Throwable t) {
                notesInterface.dismissProgressbar();
            }
        });
    }

}