package com.example.education.home;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.education.EducationApplication;
import com.example.education.R;
import com.example.education.buynow.PaymentActivity;
import com.example.education.certificate.CertificateActivity;
import com.example.education.chat.ChatAcitivity;
import com.example.education.databinding.ActivityDashBoardBinding;
import com.example.education.inbox.InvoiceActivity;
import com.example.education.login.LoginActivity;
import com.example.education.mycourse.MyCourseActivity;
import com.example.education.mycourse.MyCourseListAdapter;
import com.example.education.mycourse.PointListAdapter;
import com.example.education.profile.ProfileActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.CourseResponse;
import com.example.education.response.SubjectResponse;
import com.example.education.subjects.SubjectActivity;
import com.example.education.support.SupportActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    ActivityDashBoardBinding  activityDashBoardBinding;
    ActionBarDrawerToggle toggle;
    List<CourseResponse> courseResponse = new ArrayList<>();
    int course_position = -1;
    PointListAdapter pointListAdapter;
    RecyclerView recyclerView;
    MyCourseListAdapter courseListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityDashBoardBinding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(activityDashBoardBinding.getRoot());
        setNavigation();
        setSupportActionBar(activityDashBoardBinding.toolbar);
        setListeners();
        bottomNavigation();


    }

    private void setPointsRecyclerView() {
        RecyclerView recyclerView = activityDashBoardBinding.appBarMain.pointRecyclerview;
        pointListAdapter = new PointListAdapter(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this ,2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(pointListAdapter);
        try {
            for (int i = 0; i <courseResponse.size() ; i++) {
                if(EducationApplication.sharedPreferences.getString("courseid", "").equalsIgnoreCase(courseResponse.get(i).course_id))
                {
                    course_position = i;
                    break;
                }
            }
            pointListAdapter.setData(courseResponse.get(course_position));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        activityDashBoardBinding.appBarMain.btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent support = new Intent(DashBoardActivity.this , SupportActivity.class);
                startActivity(support);
            }
        });

    }

    private void bottomNavigation() {
        activityDashBoardBinding.navigation.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        EducationApplication.editor.putString("examtype", "2").apply();
                        EducationApplication.editor.putString("type", "video").apply();
                        Intent i = new Intent(DashBoardActivity.this , SubjectActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_certificate:
                        Intent exam = new Intent(DashBoardActivity.this , CertificateActivity.class);
                        startActivity(exam);
                        return true;
                    case R.id.navigation_explore:
                        EducationApplication.editor.putString("ForMyCourse", "false").apply();
                        EducationApplication.editor.putString("type", "course").apply();
                        Intent course = new Intent(DashBoardActivity.this , MyCourseActivity.class);
                        course.putExtra("ForMyCourse", "false");
                        course.putExtra("track", "false");
                        startActivity(course);
                        return true;
//                    case R.id.navigation_notes:
//                        EducationApplication.editor.putString("type", "notes").apply();
//                        Intent notes = new Intent(DashBoardActivity.this , SubjectActivity.class);
//                        startActivity(notes);
//                        return true;

                    case R.id.navigation_chat:
                        EducationApplication.editor.putString("examtype", "1").apply();
                        EducationApplication.editor.putString("type", "mcq").apply();
                        Intent mcq = new Intent(DashBoardActivity.this , ChatAcitivity.class);
                        startActivity(mcq);
                        return true;

                    case R.id.navigation_support:
                        Intent support = new Intent(DashBoardActivity.this , SupportActivity.class);
                        startActivity(support);
                        return true;
                }
                return false;
            }
        });
    }

    private void setListeners() {
        activityDashBoardBinding.appBarMain.btVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("examtype", "2").apply();
                EducationApplication.editor.putString("type", "video").apply();
                Intent i = new Intent(DashBoardActivity.this , SubjectActivity.class);
                startActivity(i);
            }
        });

        activityDashBoardBinding.appBarMain.btBuynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    for (int i = 0; i <courseResponse.size() ; i++) {
                        if(EducationApplication.sharedPreferences.getString("courseid", "").equalsIgnoreCase(courseResponse.get(i).course_id))
                        {
                            course_position = i;
                            break;
                        }
                    }
                    Intent demo = new Intent(DashBoardActivity.this, PaymentActivity.class);
                    demo.putExtra("courseDetail" , courseResponse.get(course_position));
                    demo.putExtra("position" ,    course_position);
                    startActivity(demo);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        activityDashBoardBinding.appBarMain.btMcq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("examtype", "1").apply();
                EducationApplication.editor.putString("type", "mcq").apply();
                Intent i = new Intent(DashBoardActivity.this , SubjectActivity.class);
                startActivity(i);
            }
        });

        activityDashBoardBinding.appBarMain.btExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("examtype", "2").apply();
                EducationApplication.editor.putString("type", "exam").apply();
                Intent i = new Intent(DashBoardActivity.this , SubjectActivity.class);
                startActivity(i);
            }
        });
        activityDashBoardBinding.appBarMain.btNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EducationApplication.editor.putString("type", "notes").apply();
                Intent i = new Intent(DashBoardActivity.this , SubjectActivity.class);
                startActivity(i);
            }
        });

    }

    private void setBanner(Response<List<SubjectResponse>> response) {
        SliderView sliderView = activityDashBoardBinding.appBarMain.imageSlider;
        SliderAdapterExample sliderAdapterExampleBanner = new SliderAdapterExample(this ,response);
        sliderView.setSliderAdapter(sliderAdapterExampleBanner);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setScrollTimeInMillis(2000);
        sliderView.startAutoCycle();
    }

    private void setNavigation() {
        toggle = new ActionBarDrawerToggle(
                this,  activityDashBoardBinding.drawerLayout, activityDashBoardBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityDashBoardBinding.drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_hamburgar_2);
        activityDashBoardBinding.navView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== R.id.mycourse)
        {
            EducationApplication.editor.putString("type", "course").apply();
            EducationApplication.editor.putString("ForMyCourse", "true").apply();
            Intent course = new Intent(DashBoardActivity.this , MyCourseActivity.class);
            course.putExtra("ForMyCourse", "true");
            course.putExtra("track", "false");
            startActivity(course);
            return true;
        }
        if(item.getItemId()== R.id.profile)
        {
            Intent course = new Intent(DashBoardActivity.this , ProfileActivity.class);
            startActivity(course);
            return true;
        }
        if(item.getItemId()== R.id.allcourse)
        {
            EducationApplication.editor.putString("ForMyCourse", "false").apply();
            EducationApplication.editor.putString("type", "course").apply();
            Intent course = new Intent(DashBoardActivity.this , MyCourseActivity.class);
            course.putExtra("ForMyCourse", "false");
            course.putExtra("track", "false");
            startActivity(course);
            return true;
        }
        if(item.getItemId()== R.id.invoice)
        {
            Intent course = new Intent(DashBoardActivity.this , InvoiceActivity.class);
            startActivity(course);
            return true;
        }
        if(item.getItemId()== R.id.signout)
        {
            EducationApplication.editor.putBoolean("ISLOGGEDIN" ,false).apply();
            showLogout();
            return true;
        }
        return false;
    }

    public void bannerApi(Activity context) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.courseid = EducationApplication.sharedPreferences.getString("courseid", "");
        Call<List<SubjectResponse>> call = RetrofitClient.getInstance().getMyApi().doBannerApi(subjectRequest);
        call.enqueue(new Callback<List<SubjectResponse>>() {
            @Override
            public void onResponse(Call<List<SubjectResponse>> call, Response<List<SubjectResponse>> response) {
                    setBanner(response);
            }

            @Override
            public void onFailure(Call<List<SubjectResponse>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){ // use android.R.id
            activityDashBoardBinding.drawerLayout.openDrawer(Gravity.LEFT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getFirebaseToken();
        setTopicToFirebase();
        EducationApplication.editor.putString("ForMyCourse", "false").apply();
        EducationApplication.editor.putString("courseid", EducationApplication.sharedPreferences.getString("sub_course_id", "")).apply();
        bannerApi(this);
        courseApi(this);
        activityDashBoardBinding.tvName.setText(EducationApplication.sharedPreferences.getString("name", ""));
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if(hour>16)
        {
            activityDashBoardBinding.tvWish.setText("Good Evening");
        }
        else if(hour>12)
        {
            activityDashBoardBinding.tvWish.setText("Good Afternoon");
        }
        else
        {
            activityDashBoardBinding.tvWish.setText("Good Morning");
        }
    }

    public void showLogout()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure want to Signout?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(DashBoardActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();

    }

    public void courseApi(Activity context) {
        Call<List<CourseResponse>> call = RetrofitClient.getInstance().getMyApi().doCourseApi();
        call.enqueue(new Callback<List<CourseResponse>>() {
            @Override
            public void onResponse(Call<List<CourseResponse>> call, Response<List<CourseResponse>> response) {
                courseResponse = response.body();
                setPointsRecyclerView();
                setAllCourseRecylerView(response.body());

            }
            @Override
            public void onFailure(Call<List<CourseResponse>> call, Throwable t) {
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAllCourseRecylerView(List<CourseResponse> courseResponse) {
        recyclerView = activityDashBoardBinding.appBarMain.courseRecyclerview;
        courseListAdapter = new MyCourseListAdapter(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        {
            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(DashBoardActivity.this) {
                    private static final float SPEED = 25f;// Change this value (default=25f)
                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }
                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }
        };
        autoScroll(recyclerView ,mLayoutManager);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(courseListAdapter);
        courseListAdapter.setData(courseResponse ,false);
    }

    public void autoScroll(RecyclerView recyclerView, LinearLayoutManager mLayoutManager){
        final int speedScroll = 0;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count == courseListAdapter.getItemCount())
                {
                    count = 0;
                }
                if(count < courseListAdapter.getItemCount()){
                    DashBoardActivity.this.recyclerView.smoothScrollToPosition(++count);
                    handler.postDelayed(this, speedScroll);
                }
            }
        };
        handler.postDelayed(runnable,speedScroll);
    }

    private void getFirebaseToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d("token", token);
                    }
                });
    }

    private void setTopicToFirebase() {
        FirebaseMessaging.getInstance().subscribeToTopic("public")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}