package com.example.education.buynow;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.education.chapter.ChapterInterface;
import com.example.education.chapter.ChapterViewModel;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
public class PaymentViewModelFactory implements ViewModelProvider.Factory {

    PaymentInterface paymentInterface;

    PaymentViewModelFactory(PaymentInterface paymentInterface) {
        this.paymentInterface = paymentInterface;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PaymentViewModel.class)) {
            return (T) new PaymentViewModel(paymentInterface);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}