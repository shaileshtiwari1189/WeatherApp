package weather.demo.com.weatherapp.model;

/**
 * Created by Shailesh on 15/12/17.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {
    @SerializedName("coord")
    private Coordinate coordinate;

    @SerializedName("weather")
    private List<WeatherDescription> weatherDescription;

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private WeatherComponents weatherComponents;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;
    private Long dt;

    @SerializedName("sys")
    private SunSystem sunSystem;

    private int id;
    private String name;

    public SunSystem getSunSystem() {
        return sunSystem;
    }

    public void setSunSystem(SunSystem sunSystem) {
        this.sunSystem = sunSystem;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }



    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<WeatherDescription> getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(List<WeatherDescription> weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public WeatherComponents getWeatherComponents() {
        return weatherComponents;
    }

    public void setWeatherComponents(WeatherComponents weatherComponents) {
        this.weatherComponents = weatherComponents;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
