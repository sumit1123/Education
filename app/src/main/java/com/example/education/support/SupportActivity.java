package com.example.education.support;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityOtpBinding;
import com.example.education.databinding.ActivitySupportBinding;
import com.example.education.home.DashBoardActivity;

import java.net.URLEncoder;

public class SupportActivity extends BaseActivity implements View.OnClickListener {

    ActivitySupportBinding activitySupportBinding;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activitySupportBinding = ActivitySupportBinding.inflate(getLayoutInflater());
        setContentView(activitySupportBinding.getRoot());
        activitySupportBinding.imgCourseCall.setOnClickListener(this);
        activitySupportBinding.imgCertificateCall.setOnClickListener(this);
        activitySupportBinding.imgDoubtCall.setOnClickListener(this);
        activitySupportBinding.imgWhatsapp.setOnClickListener(this);
        activitySupportBinding.imgWhatsappCertificate.setOnClickListener(this);
        activitySupportBinding.imgWhatsappDoubt.setOnClickListener(this);

        activitySupportBinding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.img_course_call: {
                if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE} ,1001);
                }
                else
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ "919348918979"));//change the number
                    startActivity(callIntent);
                }
                break;
            }
            case R.id.img_whatsapp: {
                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ "++919348918979" +"&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(SupportActivity.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            case R.id.img_doubt_call: {
                if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE} ,1001);
                }
                else
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ "919348918979"));//change the number
                    startActivity(callIntent);
                }
                break;

            }
            case R.id.img_whatsapp_doubt: {
                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ "+919348918979" +"&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(SupportActivity.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
            case R.id.img_certificate_call: {
                if(checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE} ,1001);
                }
                else
                {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+ "919348918979"));//change the number
                    startActivity(callIntent);
                }
                break;

            }
            case R.id.img_whatsapp_certificate: {
                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {
                    String url = "https://api.whatsapp.com/send?phone="+ "+919348918979" +"&text=" + URLEncoder.encode("", "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(SupportActivity.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }

        }
    }
}