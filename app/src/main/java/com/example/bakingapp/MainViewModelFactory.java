package com.example.bakingapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.test.espresso.idling.CountingIdlingResource;

public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private CountingIdlingResource mIdlingResource;

    public MainViewModelFactory(CountingIdlingResource mIdlingResource) {
        this.mIdlingResource = mIdlingResource;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainViewModel(mIdlingResource);
    }
}
