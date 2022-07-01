package com.example.education.videolist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.subjects.SubjectInterface;
import com.example.education.subjects.SubjectViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class VideoListViewModelFactory implements ViewModelProvider.Factory {

    VideoListInterface videoListInterface;

    VideoListViewModelFactory(VideoListInterface videoListInterface) {
        this.videoListInterface = videoListInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(VideoListViewModel.class)) {
            return (T) new VideoListViewModel(videoListInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}