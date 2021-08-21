package com.airqi.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.airqi.adapter.AqiDataAdapter;
import com.airqi.data.AqiModel;
import com.airqi.data.Repository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    private final LiveData<List<AqiModel>> list;
    private final AqiDataAdapter adapter;
    private final Repository repo;
    private final MutableLiveData<AqiModel> onClickCity = new MutableLiveData<>();

    public MainActivityViewModel(Application application) {
        super(application);
        adapter = new AqiDataAdapter(this);
        repo = Repository.create(application);
        list = repo.getList();
    }

    public LiveData<List<AqiModel>> getList() {
        return list;
    }

    public AqiDataAdapter getAdapter() {
        return adapter;
    }

    public LiveData<AqiModel> getOnClickCity() {
        return onClickCity;
    }

    public void setDataInAdapter(List<AqiModel> aqiModel) {
        adapter.setData(aqiModel);
    }

    public void startSocket() {
        repo.startSocket();
    }

    public void stopSocket() {
        repo.stopSocket();
    }

    public void handleOnCityClick(AqiModel aqiModel) {
        onClickCity.setValue(aqiModel);
    }
}
