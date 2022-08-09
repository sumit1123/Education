package com.example.education;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class EducationApplication extends Application implements Application.ActivityLifecycleCallbacks {

    public static SharedPreferences sharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = getSharedPreferences("Education", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {
      //  activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    //    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        //activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
       // activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
