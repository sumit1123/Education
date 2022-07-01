package com.example.education.inbox;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.login.LoginInterface;
import com.example.education.otp.OTPActivity;
import com.example.education.register.RegisterActivity;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.LoginRequest;
import com.example.education.response.InvoiceResonse;
import com.example.education.response.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InvoiceViewModel extends ViewModel {

    private InoviceInterface inoviceInterface;


    InvoiceViewModel(InoviceInterface inoviceInterface) {
        this.inoviceInterface = inoviceInterface;
    }

    public void invoiceApi(Activity context) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.member_id = EducationApplication.sharedPreferences.getString("user_id", "");
        Call<List<InvoiceResonse>> call = RetrofitClient.getInstance().getMyApi().doInvoiceApi(loginRequest);
        call.enqueue(new Callback<List<InvoiceResonse>>() {
            @Override
            public void onResponse(Call<List<InvoiceResonse>> call, Response<List<InvoiceResonse>> loginResponse) {
                inoviceInterface.dismissProgressbar();
                inoviceInterface.setData(loginResponse.body());
            }

            @Override
            public void onFailure(Call<List<InvoiceResonse>> call, Throwable t) {
                inoviceInterface.dismissProgressbar();
            }
        });
    }

}