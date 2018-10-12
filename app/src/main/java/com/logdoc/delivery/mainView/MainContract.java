package com.logdoc.delivery.mainView;

import android.location.Location;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.logdoc.delivery.mainView.weather.WeatherDay;

import retrofit2.Response;

public interface MainContract {

    interface Presenter{

        void getWeather(Location location);

        boolean isNetworkOnline();

        boolean isServiceOk();
    }

    interface View{

        void setTemp(WeatherDay weatherDay);

        void setPresenter(MainContract.Presenter presenter);

        void hideFrameWeather();

        void initViews(android.view.View view);

        void initListeners();

        void setBackground();

        void onMapReady(GoogleMap googleMap);

        void moveCamera(LatLng latLng, float zoom);

        void initMap();

    }
}
