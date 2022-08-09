package com.example.education.examnames;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.set.SetInterface;
import com.example.education.set.SetViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ExamViewModelFactory implements ViewModelProvider.Factory {

    ExamInterface examInterface;

    ExamViewModelFactory(ExamInterface examInterface) {
        this.examInterface = examInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ExamViewModel.class)) {
            return (T) new ExamViewModel(examInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}