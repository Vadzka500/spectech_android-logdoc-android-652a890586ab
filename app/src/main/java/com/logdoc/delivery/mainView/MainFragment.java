package com.logdoc.delivery.mainView;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.logdoc.delivery.R;
import com.logdoc.delivery.mainView.weather.WeatherDay;

import java.util.List;

import retrofit2.Response;

import static com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY;

public class MainFragment extends Fragment implements MainContract.View, OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MLogs";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;

    private Boolean mLocationPermissionsGranted = false;

    private MainContract.Presenter presenter;

    private GoogleMap mMap;

    private FusedLocationProviderClient mFusedLocationProviderClient;

    LocationRequest mLocationRequest;

    private Marker _myLocation;

    private Circle mCircle;

    private static final float DEFAULT_ZOOM = 14f;

    private FrameLayout mFrameWeather;

    private ProgressBar mProgressBarWeather;

    private TextView mTextTemperature;

    private ImageView mImageWeather;

    private NavigationView navigationView;

    private DrawerLayout drawer;

    private FloatingActionButton fab;

    public static MainFragment getInstance(){
        MainFragment mainFragment = new MainFragment();
        return  mainFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.main_frag, container, false);

        setBackground();

        if(presenter.isServiceOk()){
            initViews(root);
            initListeners();
            getLocationPermission();
        }

        return root;
    }





    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "READY MAPS");
        mMap = googleMap;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(1500);
        mLocationRequest.setPriority(PRIORITY_HIGH_ACCURACY);

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                return;
            }

            //mMap.setMyLocationEnabled(true);

            //Hide setMyLocation
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mMap.getUiSettings().setCompassEnabled(false);

            LocationCallback mLocationCallback = new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {

                    for (Location location : locationResult.getLocations()) {
                        List<Location> locationList = locationResult.getLocations();

                        if (locationList.size() > 0) {
                            Location locationUpdate = locationList.get(locationList.size() - 1);
                            _myLocation.setPosition(new LatLng(locationUpdate.getLatitude(), locationUpdate.getLongitude()));
                            mCircle.setCenter(new LatLng(locationUpdate.getLatitude(), locationUpdate.getLongitude()));
                            mCircle.setRadius(locationUpdate.getAccuracy());
                        }
                    }
                };
            };

            mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,mLocationCallback, Looper.myLooper());

        }
    }

    @Override
    public void initMap(){
        Log.d(TAG, "Init MapS");
        SupportMapFragment mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MainFragment.this);
    }

    private void getDeviceLocation(){

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        try{
            if(mLocationPermissionsGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();

                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {

                        if(task.isSuccessful()){
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()), DEFAULT_ZOOM);

                            _myLocation = mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                    .title("My Location")
                                    .anchor(0.5f,0.5f)
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icn_4)));

                            mCircle = mMap.addCircle(new CircleOptions()
                                    .center(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()))
                                    .radius(currentLocation.getAccuracy())

                                    .strokeWidth(2)

                                    .strokeColor(Color.parseColor("#A5D6A7"))
                                    .fillColor(Color.argb(50,102,187,106)));

                            if(presenter.isNetworkOnline()) {
                                mFrameWeather.setVisibility(View.VISIBLE);
                                mProgressBarWeather.setVisibility(View.VISIBLE);
                                presenter.getWeather(currentLocation);
                            }else{
                                mFrameWeather.setVisibility(View.GONE);
                            }

                        }else{
                            Log.d(TAG,"Location NULL");
                        }
                    }
                });
            }

        }catch (SecurityException e){
            Log.e(TAG,"Security Exception: "+e.getMessage());
        }
    }

    @Override
    public void moveCamera(LatLng latLng, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity().getApplicationContext(), COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                requestPermissions(permissions,LOCATION_PERMISSION_REQUEST_CODE);

            }
        }else{
            requestPermissions(permissions,LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;
        switch (requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i > grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                    initMap();
                }
            }
        }
    }

    @Override
    public void setTemp(WeatherDay weatherDay){

        mProgressBarWeather.setVisibility(View.GONE);
        mTextTemperature.setVisibility(View.VISIBLE);
        mImageWeather.setVisibility(View.VISIBLE);
        mTextTemperature.setText(weatherDay.getTempWithDegree());
        Glide.with(getActivity()).load(weatherDay.getIconUrl()).into(mImageWeather);

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void hideFrameWeather() {
        mFrameWeather.setVisibility(View.GONE);
    }

    @Override
    public void initViews(View root) {
        mFrameWeather = root.findViewById(R.id.frameWeather);
        mProgressBarWeather =root.findViewById(R.id.progressBarWeather);
        mTextTemperature = root.findViewById(R.id.tempWeather);
        mImageWeather = root.findViewById(R.id.imageWeather);
        drawer = (DrawerLayout) root.findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) root.findViewById(R.id.nav_view);
        fab = (FloatingActionButton) root.findViewById(R.id.fab);

        navigationView.setNavigationItemSelectedListener(this);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void initListeners() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawer.setDrawerLockMode(0);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    @Override
    public void setBackground() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = getActivity().getDrawable(R.drawable.back8);
            drawable.setTintMode(PorterDuff.Mode.ADD);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                drawable.setTint(getActivity().getColor(R.color.tint));
            }
            getActivity().getWindow().setBackgroundDrawable(drawable);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        }

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
