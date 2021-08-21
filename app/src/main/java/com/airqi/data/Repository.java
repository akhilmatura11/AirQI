package com.airqi.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.airqi.socket.CustomWebSocket;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class Repository {

    private final AppDatabase appDatabase;
    WebSocket socket;

    public LiveData<List<AqiModel>> getList() {
        return appDatabase.aqiDao().getData();
    }

    LiveData<List<AqiModel>> list;


    private Repository(AppDatabase appDatabase)
    {
        this.appDatabase = appDatabase;
    }

    public static Repository create(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);


        return new Repository(appDatabase);
    }

    public void stopSocket() {
       socket.close(1000, null);
    }

    public void startSocket() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("wss://city-ws.herokuapp.com/").build();
        CustomWebSocket customWebSocket = new CustomWebSocket(appDatabase);
        socket = client.newWebSocket(request, customWebSocket);
    }

    public LiveData<List<AqiModel>> getCitData(String cityName) {
        return appDatabase.aqiDao().getCityInfo(cityName);
    }

    public LiveData<AqiModel> getCurrent(String cityName) {
        return appDatabase.aqiDao().getCurrent(cityName);
    }
}
