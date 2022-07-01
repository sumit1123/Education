package com.example.education.mcq;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityMcqBinding;
import com.example.education.databinding.ActivityMcqResultBinding;
import com.example.education.repo.request.Exam;
import com.example.education.response.MCQResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MCQResultActivity extends BaseActivity implements MCQInterface {

    ActivityMcqResultBinding activityMcqResultBinding;
    MCQViewModel mcqViewModel;
    String correct_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityMcqResultBinding = ActivityMcqResultBinding.inflate(getLayoutInflater());
        setContentView(activityMcqResultBinding.getRoot());
        mcqViewModel = new ViewModelProvider(this, new MCQViewModelFactory(this)).get(MCQViewModel.class);

        correct_value = EducationApplication.sharedPreferences.getInt("total_mcq_correct_answer" , 0)+"/"+EducationApplication.sharedPreferences.getInt("total_mcq_questions", 0);
        activityMcqResultBinding.correctAnswersValue.setText(correct_value);
        activityMcqResultBinding.btDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setMCQ(List<MCQResponse> body) {
        showLoading(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(MCQResultActivity.this, "Cant go back!" ,Toast.LENGTH_SHORT).show();
    }
}