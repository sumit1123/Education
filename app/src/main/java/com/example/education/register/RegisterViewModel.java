package com.example.education.register;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.course.CourseListAdapter;
import com.example.education.home.DashBoardActivity;
import com.example.education.login.LoginActivity;
import com.example.education.login.LoginInterface;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.RegisterRequest;
import com.example.education.response.CommonResponse;
import com.example.education.response.CourseResponse;
import com.example.education.response.RegisterResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterViewModel extends ViewModel {

    private RegisterInterface loginInterface;

    RegisterViewModel(RegisterInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public void registerApi(Activity context, String mobile_number, String name, String password, String course_id ,String device_id) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.mobile = mobile_number;
        registerRequest.name = name;
        registerRequest.PhoneId = device_id;
        registerRequest.password = password;
        registerRequest.sub_course_id = course_id;
        registerRequest.categoryid = "1";
        Call<RegisterResponse> call = RetrofitClient.getInstance().getMyApi().doRegisterApi(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> loginResponse) {
                loginInterface.dismissProgressbar();
                if (loginResponse.body().isStatus()) {
                    EducationApplication.editor.putString("user_id", loginResponse.body().getMemberId()).apply();
                    EducationApplication.editor.putString("sub_course_id" , loginResponse.body().getCourseId()).apply();
                    EducationApplication.editor.putString("email_id", loginResponse.body().getEmail_id()).apply();
                    EducationApplication.editor.putString("name", loginResponse.body().getUsername()).apply();
                    EducationApplication.editor.putBoolean("ISLOGGEDIN", true).apply();
                    Intent i = new Intent(context , DashBoardActivity.class);
                    context.startActivity(i);
                    context.finish();
                } else {
                    loginInterface.setCourse();
                }
            }
            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                loginInterface.dismissProgressbar();
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void courseApi(Activity context) {
        Call<List<CourseResponse>> call = RetrofitClient.getInstance().getMyApi().doCourseApi();
        call.enqueue(new Callback<List<CourseResponse>>() {
            @Override
            public void onResponse(Call<List<CourseResponse>> call, Response<List<CourseResponse>> response) {
                loginInterface.dismissProgressbar();
                showDialog(context ,response.body());
            }
            @Override
            public void onFailure(Call<List<CourseResponse>> call, Throwable t) {
                loginInterface.dismissProgressbar();
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showDialog(Activity context , List<CourseResponse> courseResponses)
    {
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
        CourseListAdapter courseListAdapter = new CourseListAdapter(context ,courseResponses, this);
        course_recyclerview.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
       // StaggeredGridLayoutManager mLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        course_recyclerview.setLayoutManager(linearLayoutManager);
        course_recyclerview.setAdapter(courseListAdapter);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                loginInterface.callResgister();
            }
        });
    }

}