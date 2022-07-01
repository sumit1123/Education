package com.example.education.notes;

import com.example.education.response.NotesResponse;

import java.util.List;

import retrofit2.Response;

public interface NotesInterface {

    void dismissProgressbar();

    void setNotes(Response<List<NotesResponse>> response);
}
