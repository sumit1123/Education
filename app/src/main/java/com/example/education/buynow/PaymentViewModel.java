package com.example.education.buynow;

import android.app.Activity;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.chapter.ChapterInterface;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.PaymentRequest;
import com.example.education.repo.request.SubjectRequest;
import com.example.education.response.ChapterResponse;
import com.example.education.response.CommonResponse;
import com.example.education.response.CoupanResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentViewModel extends ViewModel {

    private PaymentInterface paymentInterface;


    PaymentViewModel(PaymentInterface paymentInterface) {
        this.paymentInterface = paymentInterface;
    }

    public void copanApi(Activity context, String course_id, String date) {
        SubjectRequest subjectRequest = new SubjectRequest();
        subjectRequest.courseid = course_id;
        subjectRequest.date = date;
        subjectRequest.member_id =EducationApplication.sharedPreferences.getString("user_id", "");
        Call<List<CoupanResponse>> call = RetrofitClient.getInstance().getMyApi().doCoupanApi(subjectRequest);
        call.enqueue(new Callback<List<CoupanResponse>>() {
            @Override
            public void onResponse(Call<List<CoupanResponse>> call, Response<List<CoupanResponse>> response) {
                paymentInterface.setCoupanData(response.body());
            }

            @Override
            public void onFailure(Call<List<CoupanResponse>> call, Throwable t) {

            }
        });
    }

    public void purchaseApi(Activity context, String payment_id, String coupan_id , String total_amount , String course_id , String course_price , String course_gst , String course_duration, String course_name) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.payment_id = payment_id;
        paymentRequest.coupan_id = coupan_id;
        paymentRequest.total_amount = total_amount;
        paymentRequest.course_id  =course_id;
        paymentRequest.course_price  =course_price;
        paymentRequest.course_gst  =course_gst;
        paymentRequest.course_duration  =course_duration;
        paymentRequest.course_name  =course_name;
        paymentRequest.name = EducationApplication.sharedPreferences.getString("name", "");
        paymentRequest.phone = EducationApplication.sharedPreferences.getString("phone", "");
        paymentRequest.member_id = EducationApplication.sharedPreferences.getString("user_id", "");
        Call<CommonResponse> call = RetrofitClient.getInstance().getMyApi().doPurchaseApi(paymentRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
               context.finish();
               EducationApplication.editor.putString("purchased_course_id" , course_id).apply();
               paymentInterface.finishResult();
            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("respnse", t.getLocalizedMessage()+"");
            }
        });
    }

}