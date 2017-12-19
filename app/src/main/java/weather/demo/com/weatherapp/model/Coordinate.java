package weather.demo.com.weatherapp.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Shailesh on 15/12/17.
 */

public class Coordinate {
    @SerializedName("lat")
    private String latitude;
    @SerializedName("lon")
    private String longitude;

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
