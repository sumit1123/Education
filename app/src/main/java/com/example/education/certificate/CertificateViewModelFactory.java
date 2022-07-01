package com.example.education.certificate;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class CertificateViewModelFactory implements ViewModelProvider.Factory {

    CertificateInterface certificateInterface;

    CertificateViewModelFactory(CertificateInterface loginInterface) {
        this.certificateInterface = loginInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CertificateViewModel.class)) {
            return (T) new CertificateViewModel(certificateInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}