package weather.demo.com.weatherapp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import weather.demo.com.weatherapp.R;
import weather.demo.com.weatherapp.Utility;
import weather.demo.com.weatherapp.adapters.WeatherAdapter;
import weather.demo.com.weatherapp.model.Weather;
import weather.demo.com.weatherapp.network.Network;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Shailesh on 15/12/17.
 */

public class WeatherFragment extends Fragment implements LocationListener {
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 101 ;
    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private static final String TAG = WeatherFragment.class.getName() ;
    private RecyclerView mRecyclerView;
    private LocationManager locationManager;
    private FloatingActionButton mFloatingActionButton;
    private WeatherAdapter weatherAdapter;
    private List<Weather> weatherList;

    public static WeatherFragment getInstance(){
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather,container,false);
        init(view);
        fetchLocationPermission();
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openPlaceAutoCompleteActivity();
            }
        });


        return view;
    }


    private void openPlaceAutoCompleteActivity() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            // TODO: Handle the error.
        } catch (GooglePlayServicesNotAvailableException e) {
            // TODO: Handle the error.
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                Log.i(TAG, "Place: " + place.getName());
                LatLng latLng = place.getLatLng();
                fetchWeatherReportfromLocation(latLng.latitude,latLng.longitude);


            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }

    private void fetchLocationPermission() {
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                Log.v(TAG, "Grant......");

            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        } else {

            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 0, 0, this);
            Location location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
            if (location != null) {
                onLocationChanged(location);
            }
        }


    }

    private void fetchWeatherReportfromLocation(Double lattitude,Double longitude) {
        Call<Weather> responseCall = Network.API.fetchWeatherReport(lattitude,longitude, Utility.APP_ID,Utility.METRIC);
        responseCall.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, retrofit2.Response<Weather> response) {
                Log.v("TAG",response.toString());
                weatherList.add(response.body());
                weatherAdapter = new WeatherAdapter(getActivity(), weatherList, new WeatherAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Weather weather) {
                       DetailWeatherFragment detailWeatherFragment = DetailWeatherFragment.getInstance(weather);
                       detailWeatherFragment.show(getChildFragmentManager(),TAG);

                    }
                });
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(mLayoutManager);
                mRecyclerView.setAdapter(weatherAdapter);
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.v("TAG",t.getMessage().toString());
            }
        });

    }

    private void init(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerview);
        mFloatingActionButton = (FloatingActionButton)view.findViewById(R.id.fab);
        weatherList = new ArrayList<>();
    }

    @Override
    public void onLocationChanged(Location location) {
        fetchWeatherReportfromLocation(location.getLatitude(),location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}
