package com.example.education.chapter;

import com.example.education.response.ChapterResponse;
import com.example.education.response.SubjectResponse;

import java.util.List;

public interface ChapterInterface {

    void dismissProgressbar();


    void setData(List<ChapterResponse> body);
}
