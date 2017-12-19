package weather.demo.com.weatherapp.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import weather.demo.com.weatherapp.R;
import weather.demo.com.weatherapp.Utility;
import weather.demo.com.weatherapp.model.Weather;

/**
 * Created by Shailesh on 15/12/17.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> {
    private Context context;
    private List<Weather> weatherList;
    private Typeface weatherFont;
    private OnItemClickListener onItemClickListener;

    public WeatherAdapter(Context context, List<Weather> weatherList,OnItemClickListener listener){
        this.weatherList = weatherList;
        this.context = context;
        this.onItemClickListener = listener;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.adapter_weather, parent, false);
        weatherFont = Typeface.createFromAsset(context.getAssets(), "fonts/weathericons-regular-webfont.ttf");

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Weather weather = weatherList.get(position);
        if(weather!=null) {
            holder.temperature.setText(String.format(weather.getWeatherComponents().getTemp()+ "°C"));
            holder.countryName.setText(weather.getSunSystem().getCountry());
            holder.cityName.setText(weather.getName());
            holder.description.setText(weather.getWeatherDescription().get(0).getDescription());
            holder.cloud.setText(Html.fromHtml(Utility.setWeatherIcon(weather.getWeatherDescription().get(0).getId(),weather.getSunSystem().getSunrise()*1000,weather.getSunSystem().getSunset()*1000)));
            holder.bind(weather,onItemClickListener);
        }
    }
    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView cityName, countryName, temperature,cloud,description;
        View view;

        public MyViewHolder(View view) {
            super(view);
            this.view = view;
            cityName = (TextView) view.findViewById(R.id.cityName);
            countryName = (TextView) view.findViewById(R.id.countryName);
            temperature = (TextView) view.findViewById(R.id.temperature);
            cloud = (TextView) view.findViewById(R.id.cloud);
            description = (TextView) view.findViewById(R.id.description);
            cloud.setTypeface(weatherFont);
        }

        public void bind(final Weather item, final OnItemClickListener listener) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void  onItemClick(Weather weather);
    }

}
