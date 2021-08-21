package com.airqi.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CityAqiGraphViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final String cityName;

    public CityAqiGraphViewModelFactory(Application application, String cityName) {
        this.application = application;
        this.cityName = cityName;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CityAqiGraphViewModel.class)){
            return (T) new CityAqiGraphViewModel(application, cityName);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
