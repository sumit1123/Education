package com.example.education.certificate;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.education.EducationApplication;
import com.example.education.repo.remote.RetrofitClient;
import com.example.education.repo.request.PaymentRequest;
import com.example.education.response.CommonResponse;
import com.example.education.response.CourseResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CertificateViewModel extends ViewModel {

    private CertificateInterface certificateInterface;

    CertificateViewModel(CertificateInterface certificateInterface) {
        this.certificateInterface = certificateInterface;
    }

    public void examformApi(Activity context , String phone, String father_name , String address ,String landmark, String pincode , String state , String city,
                            String dob ,String courseid , String name , String exam_id) {
        ExamForm examForm = new ExamForm();
        examForm.address = address;
        examForm.name = name;
        examForm.exam_id = exam_id;
        examForm.landmark = landmark;
        examForm.courses_id = courseid;
        examForm.city = city;
        examForm.dob = dob;
        examForm.father_name = father_name;
        examForm.state = state;
        examForm.pincode = pincode;
        examForm.members_id = EducationApplication.sharedPreferences.getString("user_id" , "");
        examForm.mobile = phone;

        Call<CourseResponse> call = RetrofitClient.getInstance().getMyApi().doExamFormApi(examForm);
        call.enqueue(new Callback<CourseResponse>() {
            @Override
            public void onResponse(Call<CourseResponse> call, Response<CourseResponse> response) {
                certificateInterface.dismissProgressbar();
                certificateInterface.setData(response.body());
            }
            @Override
            public void onFailure(Call<CourseResponse> call, Throwable t) {
                certificateInterface.dismissProgressbar();
                Toast.makeText(context, t.getLocalizedMessage() + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void dobuyExamName(Activity context, String course_id) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.course_id = course_id;
        paymentRequest.members_id = EducationApplication.sharedPreferences.getString("user_id" , "");
        Call<List<BuyExamResponse>> call = RetrofitClient.getInstance().getMyApi().doBuyExamNameeApi(paymentRequest);
        call.enqueue(new Callback<List<BuyExamResponse>>() {
            @Override
            public void onResponse(Call<List<BuyExamResponse>> call, Response<List<BuyExamResponse>> response) {
                certificateInterface.setBuyData(response.body());
            }
            @Override
            public void onFailure(Call<List<BuyExamResponse>> call, Throwable t) {
                Log.e("respnse", t.getLocalizedMessage()+"");
            }
        });
    }

    public void dobuyExamStart(Activity context, String exam_id) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.exam_id = exam_id;
        paymentRequest.members_id = EducationApplication.sharedPreferences.getString("user_id" , "");
        Call<List<BuyExamResponse>> call = RetrofitClient.getInstance().getMyApi().doBuyExamStartApi(paymentRequest);
        call.enqueue(new Callback<List<BuyExamResponse>>() {
            @Override
            public void onResponse(Call<List<BuyExamResponse>> call, Response<List<BuyExamResponse>> response) {

            }
            @Override
            public void onFailure(Call<List<BuyExamResponse>> call, Throwable t) {
                Log.e("respnse", t.getLocalizedMessage()+"");
            }
        });
    }

    public void exampurchaseApi(Activity context, String course_id, String payment_id, BuyExamResponse buyExamResponse) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.course_id = course_id;
        paymentRequest.member_id = EducationApplication.sharedPreferences.getString("user_id" , "");
        paymentRequest.payment_id = payment_id;
        paymentRequest.name =  EducationApplication.sharedPreferences.getString("name" , "");
        paymentRequest.phone = EducationApplication.sharedPreferences.getString("phone" , "");
        paymentRequest.exam_price = buyExamResponse.exam_price;
        paymentRequest.reexam_price = buyExamResponse.reexam_price;
        paymentRequest.exam_gst = buyExamResponse.exam_gst;
        paymentRequest.exam_name = buyExamResponse.exam_name;
        Call<CommonResponse> call = RetrofitClient.getInstance().getMyApi().doPurchaseExamNameeApi(paymentRequest);
        call.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {

            }
            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                Log.e("respnse", t.getLocalizedMessage()+"");
            }
        });
    }

}