package com.example.education.inbox;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.education.EducationApplication;
import com.example.education.base.BaseActivity;
import com.example.education.databinding.ActivityInvoiceBinding;
import com.example.education.databinding.ActivityLoginBinding;
import com.example.education.home.DashBoardActivity;
import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;
import com.example.education.login.LoginViewModelFactory;
import com.example.education.mcq.ExamResultAdapter;
import com.example.education.response.InvoiceResonse;

import java.util.ArrayList;
import java.util.List;

public class InvoiceActivity extends BaseActivity implements InoviceInterface {

    ActivityInvoiceBinding activityInvoiceBinding;
    private InvoiceViewModel invoiceViewModel;
    InvoiceAdapter invoiceAdapter;
    List<InvoiceResonse> invoiceResonseArrayList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityInvoiceBinding = ActivityInvoiceBinding.inflate(getLayoutInflater());
        setContentView(activityInvoiceBinding.getRoot());
        invoiceViewModel = new ViewModelProvider(this, new InoviceViewModelFactory(this)).get(InvoiceViewModel.class);
        setData();
    }

    private void setData() {
        invoiceAdapter = new InvoiceAdapter(this);
        activityInvoiceBinding.invoiceRecyclerview.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        activityInvoiceBinding.invoiceRecyclerview.setLayoutManager(layoutManager);
        activityInvoiceBinding.invoiceRecyclerview.setAdapter(invoiceAdapter);
    }

    @Override
    public void dismissProgressbar() {
        showLoading(false);
    }

    @Override
    public void setData(List<InvoiceResonse> body) {
            this.invoiceResonseArrayList = body;
            invoiceAdapter.setData(body);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invoiceViewModel.invoiceApi(this);

    }
}