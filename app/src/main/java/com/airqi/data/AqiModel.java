package com.airqi.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.airqi.utils.AppUtility;

import java.util.Locale;

@Entity(tableName = "AqiData")
public class AqiModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String cityName;
    private String aqiValue;
    private long timeStamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAqiValue() {
        return String.format(Locale.getDefault(),"%.2f", Double.valueOf(aqiValue));
    }

    public void setAqiValue(String aqiValue) {
        this.aqiValue = aqiValue;
    }

    public long getTimeStamp() { return timeStamp;}

    public String getFormattedTimeStamp() {
        return AppUtility.getFormattedTimestamp(timeStamp);
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTime() {
        return AppUtility.getTime(timeStamp);
    }
}
