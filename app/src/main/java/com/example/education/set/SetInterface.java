package com.example.education.set;

import com.example.education.response.SetResponse;

import java.util.List;

public interface SetInterface {

    void dismissProgressbar();


    void setData(List<SetResponse> body);
}
