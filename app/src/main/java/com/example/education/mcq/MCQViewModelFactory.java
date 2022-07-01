package com.example.education.mcq;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.set.SetInterface;
import com.example.education.set.SetViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class MCQViewModelFactory implements ViewModelProvider.Factory {

    MCQInterface mcqInterface;

    MCQViewModelFactory(MCQInterface mcqInterface) {
        this.mcqInterface = mcqInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MCQViewModel.class)) {
            return (T) new MCQViewModel(mcqInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}