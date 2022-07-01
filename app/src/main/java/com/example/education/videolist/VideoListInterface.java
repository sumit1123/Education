package com.example.education.videolist;

import com.example.education.response.VideoResponse;

import java.util.List;

public interface VideoListInterface {

    void dismissProgressbar();
    void setData(List<VideoResponse> body);
}
