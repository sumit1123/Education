package com.example.education.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.course.CourseListAdapter;
import com.example.education.login.LoginInterface;
import com.example.education.otp.OTPActivity;
import com.example.education.register.RegisterActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.LoginRequest;
import com.example.education.response.CourseResponse;
import com.example.education.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileViewModel extends ViewModel {

    private ProfileInterface profileInterface;


    ProfileViewModel(ProfileInterface profileInterface) {
        this.profileInterface = profileInterface;
    }

    public void profileApi(Activity context, String member_id) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.member_id = member_id;
        Call<List<LoginResponse>> call = RetrofitClient.getInstance().getMyApi().doProfileApi(loginRequest);
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> loginResponse) {
                profileInterface.setData(loginResponse.body());

            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                Toast.makeText(context, "No Record Found" + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void updateProfileApi(Activity context, String mobile, String member_id, String name, String email, String password , String sub_course_id) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.member_id = member_id;
        loginRequest.mobile = mobile;
        loginRequest.email = email;
        loginRequest.name = name;
        loginRequest.password = password;
        loginRequest.userType = "2";
        loginRequest.sub_course_id = sub_course_id;
        Call<LoginResponse> call = RetrofitClient.getInstance().getMyApi().doUpdateProfileApi(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> loginResponse) {
                profileInterface.dismissProgressbar();
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                profileInterface.dismissProgressbar();
            }
        });
    }

    public void courseApi(Activity context, boolean isShowDialog) {
        Call<List<CourseResponse>> call = RetrofitClient.getInstance().getMyApi().doCourseApi();
        call.enqueue(new Callback<List<CourseResponse>>() {
            @Override
            public void onResponse(Call<List<CourseResponse>> call, Response<List<CourseResponse>> response) {
                profileInterface.setSelectedCourse(response);
                if (isShowDialog) {
                    showDialog(context, response.body());
                }
            }
            @Override
            public void onFailure(Call<List<CourseResponse>> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showDialog(Activity context, List<CourseResponse> courseResponses) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.course_list);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
        RecyclerView course_recyclerview = dialog.findViewById(R.id.course_recyclerview);
        Button bt_submit = dialog.findViewById(R.id.bt_submit);
        CourseListAdapter courseListAdapter = new CourseListAdapter(context, courseResponses, null);
        course_recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        course_recyclerview.setLayoutManager(linearLayoutManager);
        course_recyclerview.setAdapter(courseListAdapter);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                courseApi(context ,false);
                dialog.dismiss();
            }
        });
    }

}