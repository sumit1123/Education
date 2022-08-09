package com.example.education.examnames;

import com.example.education.response.SetResponse;

import java.util.List;

public interface ExamInterface {

    void dismissProgressbar();
    void setData(List<SetResponse> body);
}
