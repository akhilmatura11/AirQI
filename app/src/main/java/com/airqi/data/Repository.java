package com.airqi.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.airqi.socket.CustomWebSocket;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class Repository {

    private final AppDatabase appDatabase;
    private WebSocket socket;

    private Repository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    public static Repository create(Application application) {
        AppDatabase appDatabase = AppDatabase.getDatabase(application);
        return new Repository(appDatabase);
    }

    public LiveData<List<AqiModel>> getList() {
        return appDatabase.aqiDao().getData();
    }

    public LiveData<List<AqiModel>> getCitData(String cityName) {
        return appDatabase.aqiDao().getCityInfo(cityName);
    }

    public LiveData<AqiModel> getCurrent(String cityName) {
        return appDatabase.aqiDao().getCurrent(cityName);
    }

    public void startSocket() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url("wss://city-ws.herokuapp.com/").build();
        CustomWebSocket customWebSocket = new CustomWebSocket(appDatabase);
        socket = client.newWebSocket(request, customWebSocket);
    }

    public void stopSocket() {
        socket.close(1000, null);
    }
}
