package com.example.education.profile;

import com.example.education.response.CourseResponse;
import com.example.education.response.LoginResponse;

import java.util.List;

import retrofit2.Response;

public interface ProfileInterface {

    void dismissProgressbar();
    void setData(List<LoginResponse> body);

    void setSelectedCourse(Response<List<CourseResponse>> response);
}
