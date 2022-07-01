package com.example.education.chapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.subjects.SubjectInterface;
import com.example.education.subjects.SubjectViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ChapterViewModelFactory implements ViewModelProvider.Factory {

    ChapterInterface chapterInterface;

    ChapterViewModelFactory(ChapterInterface chapterInterface) {
        this.chapterInterface = chapterInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ChapterViewModel.class)) {
            return (T) new ChapterViewModel(chapterInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}