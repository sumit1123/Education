package com.example.education.mycourse;

import com.example.education.response.CourseResponse;

import java.util.List;

public interface MyCourseInterface {

    void dismissProgressbar();

    void setData(List<CourseResponse> body, boolean b);
}
