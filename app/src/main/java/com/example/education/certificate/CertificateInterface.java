package com.example.education.certificate;

import com.example.education.response.CourseResponse;

import java.util.List;

public interface CertificateInterface {

    void dismissProgressbar();

    void setData(CourseResponse body);

    void setBuyData(List<BuyExamResponse> body);
}
