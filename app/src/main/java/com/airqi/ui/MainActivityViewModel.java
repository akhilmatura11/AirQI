package com.airqi.ui;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.airqi.adapter.AqiDataAdapter;
import com.airqi.data.AqiModel;
import com.airqi.data.Repository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {
    public LiveData<List<AqiModel>> getList() {
        return list;
    }

    LiveData<List<AqiModel>> list;

    public AqiDataAdapter getAdapter() {
        return adapter;
    }

    AqiDataAdapter adapter;
    Repository repo;
    private MutableLiveData<AqiModel> onClickCity = new MutableLiveData<>();

    public MainActivityViewModel(Application application)
    {
        super(application);
        adapter = new AqiDataAdapter(this);
        repo = Repository.create(application);
        list = repo.getList();
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

    public void handleOnCityClick(AqiModel aqiModel){
        onClickCity.setValue(aqiModel);
    }
}
