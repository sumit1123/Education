package com.example.education.mcq;

import com.example.education.response.MCQResponse;

import java.util.List;

public interface MCQInterface {

    void dismissProgressbar();
    void setMCQ(List<MCQResponse> body);
}
