package com.example.education.set;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.chapter.ChapterInterface;
import com.example.education.chapter.ChapterViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class SetViewModelFactory implements ViewModelProvider.Factory {

    SetInterface setInterface;

    SetViewModelFactory(SetInterface setInterface) {
        this.setInterface = setInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SetViewModel.class)) {
            return (T) new SetViewModel(setInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}