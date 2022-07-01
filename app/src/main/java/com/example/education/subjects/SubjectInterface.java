package com.example.education.subjects;

import com.example.education.response.SubjectResponse;

import java.util.List;

public interface SubjectInterface {

    void dismissProgressbar();

    void setSubjects(List<SubjectResponse> body);
}
