package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Weather {

    private long temp;

    @JsonProperty("wind_speed")
    private long windSpeed;

    @JsonProperty("wind_dir")
    private String windDir;

    @JsonProperty("pressure_mm")
    private long pressure;




}
