package com.example.education.buynow;

import com.example.education.response.ChapterResponse;
import com.example.education.response.CoupanResponse;
import com.example.education.response.CourseResponse;

import java.util.List;

import retrofit2.Response;

public interface PaymentInterface {

    void dismissProgressbar();
    void setData(List<CourseResponse> body);
    void setCoupanData(List<CoupanResponse> response);

    void finishResult();
}
