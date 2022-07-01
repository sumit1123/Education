package com.example.education.base;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.education.R;
import com.example.education.databinding.ActivityBaseBinding;


public class BaseActivity extends AppCompatActivity {

    private ActivityBaseBinding binding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityBaseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new Dialog(this);
        setSupportActionBar(binding.toolbar);

    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }

    public void showLoading(boolean showOrNot) {
        try {
            dialog.setContentView(R.layout.loading_layout);
            ImageView img_loading = dialog.findViewById(R.id.img_loading);
            Glide.with(this).load(R.drawable.laodingeduction).into(img_loading);
            if (showOrNot) {
                dialog.show();
            } else {
                dialog.dismiss();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void showToast(String message)
    {
        Toast.makeText(this,  message, Toast.LENGTH_SHORT).show();
    }

}