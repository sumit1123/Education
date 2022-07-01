package com.example.education.certificate;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityCertificateBinding;
import com.example.education.databinding.ActivityLoginBinding;
import com.example.education.home.DashBoardActivity;
import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;
import com.example.education.login.LoginViewModelFactory;
import com.example.education.mycourse.MyCourseActivity;
import com.example.education.response.CourseResponse;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.List;

public class CertificateActivity extends BaseActivity implements CertificateInterface {

    ActivityCertificateBinding activityCertificateBinding;
    private CertificateViewModel certificateViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityCertificateBinding = ActivityCertificateBinding.inflate(getLayoutInflater());
        setContentView(activityCertificateBinding.getRoot());
        certificateViewModel = new ViewModelProvider(this, new CertificateViewModelFactory(this)).get(CertificateViewModel.class);
        activityCertificateBinding.btApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CertificateActivity.this , MyCourseActivity.class);
                intent.putExtra("ForMyCourse", "true");
                intent.putExtra("fromcertificate", "true");
                startActivity(intent);
            }
        });
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(CourseResponse body) {
    }

    @Override
    public void setBuyData(List<BuyExamResponse> body) {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

}