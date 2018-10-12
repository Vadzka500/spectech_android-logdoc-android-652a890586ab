package com.logdoc.delivery.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.logdoc.delivery.db.model.Ping;

import java.util.List;


/**
 * Created by Dmitry Ushkevich on 4/11/2018.
 */

@Dao
public interface PingDataDao {

    @Query("SELECT * FROM Ping WHERE syncState = 1 AND foreignDeviceId=:deviceId AND foreignSubscriberId=:subscriberId")
    List<Ping> getProgressPing(String deviceId, String subscriberId);

    @Insert
    void insertPingData(Ping ping);

    @Query("UPDATE Ping SET syncState = :stateNew WHERE syncState = :stateOld AND foreignDeviceId=:deviceId AND foreignSubscriberId=:subscriberId")
    void updatePing(int stateOld, int stateNew, String deviceId, String subscriberId);

    @Query("UPDATE Ping SET syncState = :stateNew WHERE syncState = :stateOld")
    void updatePing(int stateOld, int stateNew);

    @Query("DELETE FROM Ping WHERE syncState = 2")
    int deleteSyncedPing();

    @Query("SELECT * FROM Ping ORDER BY timestamp DESC LIMIT 1")
    LiveData<Ping> getLatestPing();
}
