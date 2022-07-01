package com.example.education.mycourse;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.mcq.MCQInterface;
import com.example.education.mcq.MCQViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class MyCourseViewModelFactory implements ViewModelProvider.Factory {

    MyCourseInterface myCourseInterface;

    MyCourseViewModelFactory(MyCourseInterface myCourseInterface) {
        this.myCourseInterface = myCourseInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MyCourseViewModel.class)) {
            return (T) new MyCourseViewModel(myCourseInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}