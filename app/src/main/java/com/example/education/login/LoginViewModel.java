package com.example.education.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.otp.OTPActivity;
import com.example.education.register.RegisterActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.LoginRequest;
import com.example.education.response.CommonResponse;
import com.example.education.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginViewModel extends ViewModel {

    private LoginInterface loginInterface;


    LoginViewModel(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
    }

    public void loginApi(Activity context, String mobile_number, String device_id) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.PhoneId = device_id;
        loginRequest.userid = mobile_number;
        Call<List<LoginResponse>> call = RetrofitClient.getInstance().getMyApi().doLoginApi(loginRequest);
        call.enqueue(new Callback<List<LoginResponse>>() {
            @Override
            public void onResponse(Call<List<LoginResponse>> call, Response<List<LoginResponse>> loginResponse) {
                loginInterface.dismissProgressbar();
                if (loginResponse.body() != null && loginResponse.body().size() > 0) {
                    EducationApplication.editor.putString("user_id", loginResponse.body().get(0).member_id).apply();
                    EducationApplication.editor.putString("sub_course_id", loginResponse.body().get(0).sub_course_id).apply();
                    EducationApplication.editor.putString("email_id", loginResponse.body().get(0).email_id).apply();
                    EducationApplication.editor.putString("name", loginResponse.body().get(0).name).apply();
                    EducationApplication.editor.putString("phone", loginResponse.body().get(0).phone).apply();
                    String device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                    if (loginResponse.body().get(0).phone_id.equalsIgnoreCase("") || loginResponse.body().get(0).phone_id.equalsIgnoreCase(device_id)) {
                        Intent signup = new Intent(context, OTPActivity.class);
                        signup.putExtra("password", loginResponse.body().get(0).password);
                        context.startActivity(signup);
                        context.finish();
                    } else {
                        Toast.makeText(context, "Please login with same device", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Intent signup = new Intent(context, RegisterActivity.class);
                    context.startActivity(signup);
                }

            }

            @Override
            public void onFailure(Call<List<LoginResponse>> call, Throwable t) {
                loginInterface.dismissProgressbar();
                Toast.makeText(context, "No Record Found" + "", Toast.LENGTH_SHORT).show();
                Intent signup = new Intent(context, RegisterActivity.class);
                context.startActivity(signup);
            }
        });
    }

}