package com.example.education.profile;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityProfileBinding;
import com.example.education.response.CourseResponse;
import com.example.education.response.LoginResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class ProfileActivity extends BaseActivity implements ProfileInterface {

    ActivityProfileBinding activityPaymentBinding;
    private ProfileViewModel profileViewModel;
    List<LoginResponse> loginResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityPaymentBinding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(activityPaymentBinding.getRoot());
        profileViewModel = new ViewModelProvider(this, new ProfileViewModelFactory(this)).get(ProfileViewModel.class);
        activityPaymentBinding.btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPaymentBinding.btSave.setVisibility(View.VISIBLE);
                activityPaymentBinding.course.setEnabled(true);
                activityPaymentBinding.password.setEnabled(true);
                activityPaymentBinding.Name.setEnabled(true);
                activityPaymentBinding.Email.setEnabled(true);
                activityPaymentBinding.phone.setEnabled(true);
            }
        });

        activityPaymentBinding.btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading(true);
                profileViewModel.updateProfileApi(ProfileActivity.this, activityPaymentBinding.phone.getText().toString(), loginResponses.get(0).member_id,
                        activityPaymentBinding.Name.getText().toString(), activityPaymentBinding.Email.getText().toString(), activityPaymentBinding.password.getText().toString(),
                        EducationApplication.sharedPreferences.getString("courseid", ""));
            }
        });

        activityPaymentBinding.course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.courseApi(ProfileActivity.this, true);
            }
        });

    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
        finish();
    }

    @Override
    public void setData(List<LoginResponse> body) {
        this.loginResponses = body;
        activityPaymentBinding.userid.setText("Member_id:-" + body.get(0).member_id);
        activityPaymentBinding.course.setText(body.get(0).course_id);
        activityPaymentBinding.phone.setText(body.get(0).phone);
        activityPaymentBinding.Name.setText(body.get(0).name);
        activityPaymentBinding.Email.setText(body.get(0).email_id);
        activityPaymentBinding.password.setText(body.get(0).password);
        profileViewModel.courseApi(ProfileActivity.this, false);
    }

    @Override
    public void setSelectedCourse(Response<List<CourseResponse>> response) {
        String selected_course_id = EducationApplication.sharedPreferences.getString("courseid", "");
        for (CourseResponse course : response.body()) {
            if (course.course_id.equalsIgnoreCase(selected_course_id)) {
                activityPaymentBinding.course.setText(course.course_name);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityPaymentBinding.btSave.setVisibility(View.GONE);
        profileViewModel.profileApi(ProfileActivity.this, EducationApplication.sharedPreferences.getString("user_id", ""));
    }
}