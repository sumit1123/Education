package com.example.education.otp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityLoginBinding;
import com.example.education.databinding.ActivityOtpBinding;
import com.example.education.home.DashBoardActivity;
import com.example.education.register.RegisterActivity;

public class OTPActivity extends BaseActivity {

    ActivityOtpBinding activityOtpBinding;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityOtpBinding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(activityOtpBinding.getRoot());

        if(getIntent() != null)
        {
            password = getIntent().getStringExtra("password");
        }

        activityOtpBinding.btVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!activityOtpBinding.password.getText().toString().equalsIgnoreCase("")) {
                    if(password.equalsIgnoreCase(activityOtpBinding.password.getText().toString()))
                    {
                        EducationApplication.editor.putBoolean("ISLOGGEDIN", true).apply();
                        Intent i = new Intent(OTPActivity.this, DashBoardActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else
                    {
                        showToast("Password is not valid");
                    }

                } else {
                    showToast("Enter password");
                }
            }
        });

    }
}