package com.example.education.register;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    RegisterInterface registerInterface;

    RegisterViewModelFactory(RegisterInterface loginInterface) {
        this.registerInterface = loginInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(registerInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}