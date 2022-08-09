package com.example.education.register;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityRegisterBinding;
import com.example.education.home.DashBoardActivity;
import com.example.education.login.LoginViewModel;
import com.example.education.login.LoginViewModelFactory;

import java.lang.annotation.Native;


public class RegisterActivity extends BaseActivity implements RegisterInterface {

    ActivityRegisterBinding activityRegisterBinding;
    RegisterViewModel registerViewModel;
    String device_id = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory(this)).get(RegisterViewModel.class);
        activityRegisterBinding.phone.setText(EducationApplication.sharedPreferences.getString("phone", ""));
        if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1010);
        }
        else{
            device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        activityRegisterBinding.btRegister.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (!activityRegisterBinding.Name.getText().toString().equalsIgnoreCase("")) {
                    if (!activityRegisterBinding.phone.getText().toString().equalsIgnoreCase("")) {
                            if (!activityRegisterBinding.Password.getText().toString().equalsIgnoreCase("")) {
                                if (!activityRegisterBinding.confirmPassword.getText().toString().equalsIgnoreCase("")) {
                                    showLoading(true);
                                    registerViewModel.courseApi(RegisterActivity.this);
                                } else {
                                    showToast("Enter Confirm password");
                                }
                            } else {
                                showToast("Enter password");
                            }
                    } else {
                        showToast("Enter phone");
                    }
                } else {
                    showToast("Enter Name");
                }
            }
        });

    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setCourse() {
        activityRegisterBinding.course.setVisibility(View.VISIBLE);
        registerViewModel.courseApi(this);
    }

    @Override
    public void callResgister() {
        registerViewModel.registerApi(RegisterActivity.this, activityRegisterBinding.phone.getText().toString(), activityRegisterBinding.Name.getText().toString(), activityRegisterBinding.Password.getText().toString(), EducationApplication.sharedPreferences.getString("courseid", "") ,device_id);
    }
}