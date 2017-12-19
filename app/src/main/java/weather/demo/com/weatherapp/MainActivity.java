package weather.demo.com.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import weather.demo.com.weatherapp.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity  {

    private String TAG = WeatherFragment.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpFragment();

    }



    private void setUpFragment() {
        getSupportFragmentManager().beginTransaction().add(R.id.frame,WeatherFragment.getInstance(),TAG).commit();
    }


}
