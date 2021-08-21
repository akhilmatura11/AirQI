package com.airqi.socket;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.airqi.data.AppDatabase;
import com.airqi.data.AqiModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class CustomWebSocket extends WebSocketListener{

    private final AppDatabase appDatabase;

    public CustomWebSocket(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        Log.v("airqi", "inside open");
        super.onOpen(webSocket, response);
    }

    @Override
    public void onClosed(WebSocket webSocket, int code, String reason) {
        Log.v("airqi", "inside close");
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        super.onMessage(webSocket, text);
        Log.v("airqi", "inside message");
        parseResponse(text);
        //puts the socket on hold for 30 seconds
        try {
            synchronized (webSocket)
            {
                webSocket.wait(30000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes) {
        Log.v("airqi", "inside message");
        super.onMessage(webSocket, bytes);
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        Log.v("airqi", "inside close");
        super.onClosing(webSocket, code, reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
        Log.v("airqi", "inside failure");
        super.onFailure(webSocket, t, response);
    }


    private void parseResponse(String text)
    {
        List<AqiModel> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(text);
            for(int i = 0; i<array.length();i++)
            {
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
