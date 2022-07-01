package com.example.education.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityRegisterBinding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(activityRegisterBinding.getRoot());
        registerViewModel = new ViewModelProvider(this, new RegisterViewModelFactory(this)).get(RegisterViewModel.class);
        activityRegisterBinding.phone.setText(EducationApplication.sharedPreferences.getString("phone", ""));
        activityRegisterBinding.btRegister.setOnClickListener(new View.OnClickListener() {
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
        registerViewModel.registerApi(RegisterActivity.this, activityRegisterBinding.phone.getText().toString(), activityRegisterBinding.Name.getText().toString(), activityRegisterBinding.Password.getText().toString(), EducationApplication.sharedPreferences.getString("courseid", ""));
    }
}