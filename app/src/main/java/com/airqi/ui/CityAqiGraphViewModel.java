package com.airqi.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.airqi.data.AqiModel;
import com.airqi.data.Repository;

import java.util.List;

public class CityAqiGraphViewModel extends AndroidViewModel {
    private final Repository repo;
    private final LiveData<List<AqiModel>> mCityData;
    private final LiveData<AqiModel> mCurrentData;

    public CityAqiGraphViewModel(@NonNull Application application, String cityName) {
        super(application);
        repo = Repository.create(application);
        mCityData = repo.getCitData(cityName);
        mCurrentData = repo.getCurrent(cityName);
    }

    public LiveData<List<AqiModel>> getCityData() {
        return mCityData;
    }

    public LiveData<AqiModel> getCurrentData() {
        return mCurrentData;
    }
}
