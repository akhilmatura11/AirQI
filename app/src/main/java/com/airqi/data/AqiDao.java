package com.airqi.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.DatabaseView;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AqiDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AqiModel> aqiModelList);

    @Query("SELECT * FROM (SELECT * FROM aqidata ORDER BY timeStamp desc) GROUP BY cityName ORDER BY cityName asc")
    LiveData<List<AqiModel>> getData();

    @Query("SELECT * FROM AqiData WHERE cityName = :city")
    LiveData<List<AqiModel>> getCityInfo(String city);

    @Query("SELECT * FROM AqiData WHERE cityName = :city order by timestamp desc limit 1")
    LiveData<AqiModel> getCurrent(String city);
}
