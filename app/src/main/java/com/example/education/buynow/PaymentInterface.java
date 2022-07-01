package com.example.education.buynow;

import com.example.education.response.ChapterResponse;
import com.example.education.response.CoupanResponse;

import java.util.List;

import retrofit2.Response;

public interface PaymentInterface {

    void dismissProgressbar();
    void setData(List<ChapterResponse> body);
    void setCoupanData(List<CoupanResponse> response);

    void finishResult();
}
