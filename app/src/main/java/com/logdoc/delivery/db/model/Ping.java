package com.logdoc.delivery.db.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;

@Entity(indices = @Index(value = "id", unique = true))
public class Ping {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int syncState;

    @NonNull
    private String foreignDeviceId;
    @NonNull
    private String foreignSubscriberId;

    @NonNull
    private String url;
    @NonNull
    private long timestamp;
    @Nullable
    private double pingAvg;
    @Nullable
    private double pingMin;
    @Nullable
    private double pingMax;
    @Nullable
    private double pingStdDev;
    @Nullable
    private Integer pingLoss;

    @Expose(deserialize = false, serialize = false)
    @NonNull
    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getForeignDeviceId() {
        return foreignDeviceId;
    }

    public void setForeignDeviceId(@NonNull String foreignDeviceId) {
        this.foreignDeviceId = foreignDeviceId;
    }

    @NonNull
    public String getForeignSubscriberId() {
        return foreignSubscriberId;
    }

    public void setForeignSubscriberId(@NonNull String foreignSubscriberId) {
        this.foreignSubscriberId = foreignSubscriberId;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    @NonNull
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(@NonNull long timestamp) {
        this.timestamp = timestamp;
    }

    @Nullable
    public double getPingAvg() {
        return pingAvg;
    }

    public void setPingAvg(@Nullable double pingAvg) {
        this.pingAvg = pingAvg;
    }

    @Nullable
    public double getPingMin() {
        return pingMin;
    }

    public void setPingMin(@Nullable double pingMin) {
        this.pingMin = pingMin;
    }

    @Nullable
    public double getPingMax() {
        return pingMax;
    }

    public void setPingMax(@Nullable double pingMax) {
        this.pingMax = pingMax;
    }

    @Nullable
    public double getPingStdDev() {
        return pingStdDev;
    }

    public void setPingStdDev(@Nullable double pingStdDev) {
        this.pingStdDev = pingStdDev;
    }

    @Nullable
    public Integer getPingLoss() {
        return pingLoss;
    }

    public void setPingLoss(@Nullable Integer pingLoss) {
        this.pingLoss = pingLoss;
    }

    public int getSyncState() {
        return syncState;
    }

    public void setSyncState(int syncState) {
        this.syncState = syncState;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public void setPingInfo(String info) {
        try {
            String pingLossString = info.split("% packet loss")[0];
            int pingLossIndex = pingLossString.lastIndexOf(" ") + 1;
            String pingLossValue = pingLossString.substring(pingLossIndex);

            this.setPingLoss(Integer.valueOf(pingLossValue));
            
            String pingCommonString = (info.split("min/avg/max/mdev = ")[1]).split(" ms")[0];
            String[] pingCommonArray = pingCommonString.split("/");

            this.setPingMin(Double.parseDouble(pingCommonArray[0]));
            this.setPingAvg(Double.parseDouble(pingCommonArray[1]));
            this.setPingMax(Double.parseDouble(pingCommonArray[2]));
            this.setPingStdDev(Double.parseDouble(pingCommonArray[3]));
        } catch (Exception ex) {

        }

    }
}