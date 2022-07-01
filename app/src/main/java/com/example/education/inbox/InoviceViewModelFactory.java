package com.example.education.inbox;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.login.LoginInterface;
import com.example.education.login.LoginViewModel;

public class InoviceViewModelFactory implements ViewModelProvider.Factory {

    InoviceInterface inoviceInterface;

    InoviceViewModelFactory(InoviceInterface inoviceInterface) {
        this.inoviceInterface = inoviceInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(InvoiceViewModel.class)) {
            return (T) new InvoiceViewModel(inoviceInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}