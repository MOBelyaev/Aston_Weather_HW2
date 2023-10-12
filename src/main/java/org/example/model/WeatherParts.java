package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data

public class WeatherParts {

//    @JsonProperty("day_short")
    private Weather weather;

    @JsonProperty("weather")
    public Weather getWeather() {
        return weather;
    }

    @JsonProperty("day_short")
    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
