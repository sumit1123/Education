package com.example.education.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityLoginBinding;
import com.example.education.home.DashBoardActivity;
import com.example.education.otp.OTPActivity;
import com.example.education.register.RegisterActivity;

public class LoginActivity extends BaseActivity implements LoginInterface {

    ActivityLoginBinding activityLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);
        activityLoginBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("phone" , activityLoginBinding.mobileNumber.getText().toString()).apply();
                if (!activityLoginBinding.mobileNumber.getText().toString().equalsIgnoreCase("")) {
                   showLoading(true);
                    loginViewModel.loginApi(LoginActivity.this ,activityLoginBinding.mobileNumber.getText().toString());
                } else {
                    showToast("Enter Mobile Number");
                }
            }
        });
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(EducationApplication.sharedPreferences.getBoolean("ISLOGGEDIN", false))
        {
            Intent dash = new Intent(LoginActivity.this, DashBoardActivity.class);
            startActivity(dash);
            finish();
        }
    }
}