package com.example.education.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class ProfileViewModelFactory implements ViewModelProvider.Factory {

    ProfileInterface profileInterface;

    ProfileViewModelFactory(ProfileInterface profileInterface) {
        this.profileInterface = profileInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ProfileViewModel.class)) {
            return (T) new ProfileViewModel(profileInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}