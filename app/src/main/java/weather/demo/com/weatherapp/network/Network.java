package weather.demo.com.weatherapp.network;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import weather.demo.com.weatherapp.model.Weather;

/**
 * Created by Shailesh on 15/12/17.
 */

public class Network {
    public static ApiInterface API;
    static{
        API =  ApiClient.getClient().create(ApiInterface.class);
    }

    public  interface ApiInterface {

        @GET("weather")
        Call<Weather> fetchWeatherReport(@Query("lat") double lattitude, @Query("lon") double longitude, @Query("appid") String appID,@Query("units") String units);
    }
}
