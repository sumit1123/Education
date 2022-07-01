package com.example.education.subjects;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class SubjectViewModelFactory implements ViewModelProvider.Factory {

    SubjectInterface subjectInterface;

    SubjectViewModelFactory(SubjectInterface subjectInterface) {
        this.subjectInterface = subjectInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SubjectViewModel.class)) {
            return (T) new SubjectViewModel(subjectInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}