package com.logdoc.delivery;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.logdoc.delivery.api.API;
import com.logdoc.delivery.db.LOGDOCDatabase;
import com.logdoc.delivery.utils.Prefs;
import com.logdoc.delivery.utils.ThreadExecutors;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by dmitri.artsukevich on 3/5/2018.
 */


public class App extends Application {

    private Prefs mPreferences;
    private ThreadExecutors mThreadExecutors;
    private LOGDOCDatabase mLOGDOCDatabase;
    private Retrofit mRetrofit;

    private static final int CONNECTION_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;
    private static final int WRITE_TIMEOUT = 30;

    @Override
    public void onCreate() {

        super.onCreate();
//		Fabric.with(this, new Crashlytics());

        mLOGDOCDatabase = Room.databaseBuilder(this, LOGDOCDatabase.class, "LOGDOCDatabase.db").build();
        mPreferences = new Prefs(this);
        mThreadExecutors = new ThreadExecutors();

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public LOGDOCDatabase getLOGDOCDatabase() {
        return mLOGDOCDatabase;
    }

    public ThreadExecutors getThreadExecutors() {
        return mThreadExecutors;
    }

    public Prefs getPreferences() {
        return mPreferences;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
