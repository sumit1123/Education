package com.example.education.login;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    String device_id = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);
        if(ContextCompat.checkSelfPermission(this , android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.READ_PHONE_STATE}, 1010);
        }
        else{
            device_id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        }
        activityLoginBinding.btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("phone" , activityLoginBinding.mobileNumber.getText().toString()).apply();
                if (!activityLoginBinding.mobileNumber.getText().toString().equalsIgnoreCase("")) {
                   showLoading(true);
                    loginViewModel.loginApi(LoginActivity.this ,activityLoginBinding.mobileNumber.getText().toString() ,device_id);
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