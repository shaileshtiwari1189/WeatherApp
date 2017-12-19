package weather.demo.com.weatherapp.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import weather.demo.com.weatherapp.R;
import weather.demo.com.weatherapp.Utility;
import weather.demo.com.weatherapp.model.Weather;

/**
 * Created by Shailesh on 18/12/17.
 */

public class DetailWeatherFragment extends DialogFragment {
    private Weather weather;
    public static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    private TextView city_field,updated_field,weather_icon,current_temperature_field,details_field,humidity_field,pressure_field;
    private Typeface weatherFont;
    public static DetailWeatherFragment getInstance(Weather weather){
        DetailWeatherFragment detailWeatherFragment = new DetailWeatherFragment();
        detailWeatherFragment.weather = weather;
        return detailWeatherFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_details,container,false);
        init(view);
        getDialog().getWindow().setTitle(getString(R.string.weather_details));
        weatherFont = Typeface.createFromAsset(getActivity().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        weather_icon.setTypeface(weatherFont);
        if(weather!=null) {
            city_field.setText(weather.getName().toLowerCase(Locale.ENGLISH)+" , "+weather.getSunSystem().getCountry());
            String updatedOn = df.format(new Date(weather.getDt()*1000));
            updated_field.setText(updatedOn);
            current_temperature_field.setText(String.format(weather.getWeatherComponents().getTemp()+ "°C"));
            details_field.setText(weather.getWeatherDescription().get(0).getDescription());
            humidity_field.setText(weather.getWeatherComponents().getHumidity()+" %");
            pressure_field.setText(weather.getWeatherComponents().getPressure()+"  hPa");
            weather_icon.setText(Html.fromHtml(Utility.setWeatherIcon(weather.getWeatherDescription().get(0).getId(),weather.getSunSystem().getSunrise()*1000,weather.getSunSystem().getSunset()*1000)));
        }

        return view;
    }

    private void init(View view) {
        city_field = view.findViewById(R.id.city_field);
        updated_field = view.findViewById(R.id.updated_field);
        weather_icon = view.findViewById(R.id.weather_icon);
        current_temperature_field = view.findViewById(R.id.current_temperature_field);
        details_field = view.findViewById(R.id.details_field);
        humidity_field = view.findViewById(R.id.humidity_field);
        pressure_field = view.findViewById(R.id.pressure_field);
    }
}
