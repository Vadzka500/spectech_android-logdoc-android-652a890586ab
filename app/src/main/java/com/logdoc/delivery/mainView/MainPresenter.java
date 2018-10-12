package com.logdoc.delivery.mainView;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.logdoc.delivery.mainView.weather.WeatherAPI;
import com.logdoc.delivery.mainView.weather.WeatherDay;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View view;

    private Activity activity;

    private WeatherAPI.ApiInterface api;

    private static final String TAG = "MLogs";

    public MainPresenter(MainContract.View view, Activity activity){
        this.view = view;
        this.activity = activity;
        this.view.setPresenter(this);
    }

    @Override
    public void  getWeather(Location location) {
        api = WeatherAPI.getClient().create(WeatherAPI.ApiInterface.class);
        Call<WeatherDay> callToday = api.getToday(location.getLatitude(), location.getLongitude(), "metric", WeatherAPI.KEY);

        callToday.enqueue(new Callback<WeatherDay>() {
            @Override
            public void onResponse(Call<WeatherDay> call, Response<WeatherDay> response) {
                WeatherDay data = response.body();

                if (response.isSuccessful()) {
                    view.setTemp(data);
                }
            }

            @Override
            public void onFailure(Call<WeatherDay> call, Throwable t) {

                Log.e(TAG, "onFailure");
                Log.e(TAG, t.toString());
                view.hideFrameWeather();
            }
        });
    }

    @Override
    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getNetworkInfo(0);
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getNetworkInfo(1);
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }

    @Override
    public boolean isServiceOk(){
        Log.d(TAG, "Check Google Services");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG,"Service OK!");
            return true;
        }else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "SERVICE ERROR");
        }else{
            Log.d(TAG,"SERVICE ANY ERROR");
        }
        return false;
    }
}
