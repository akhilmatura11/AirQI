package com.airqi.socket;

import androidx.annotation.NonNull;

import com.airqi.data.AppDatabase;
import com.airqi.data.AqiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class CustomWebSocket extends WebSocketListener {

    private final AppDatabase appDatabase;

    public CustomWebSocket(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }

    @Override
    public void onMessage(@NonNull WebSocket webSocket, @NonNull String text) {
        super.onMessage(webSocket, text);
        parseResponse(text);
        //puts the socket on hold for 30 seconds
        try {
            synchronized (webSocket) {
                webSocket.wait(30000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void parseResponse(String text) {
        List<AqiModel> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(text);
            for (int i = 0; i < array.length(); i++) {
                AqiModel model = new AqiModel();
                JSONObject obj = array.getJSONObject(i);
                model.setCityName(obj.getString("city"));
                model.setAqiValue(obj.getString("aqi"));
                model.setTimeStamp(Calendar.getInstance().getTimeInMillis());
                list.add(model);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        appDatabase.aqiDao().insertAll(list);
    }
}
