package com.example.education.notes;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class NotesViewModelFactory implements ViewModelProvider.Factory {

    NotesInterface notesInterface;

    NotesViewModelFactory(NotesInterface notesInterface) {
        this.notesInterface = notesInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NotesViewModel.class)) {
            return (T) new NotesViewModel(notesInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}