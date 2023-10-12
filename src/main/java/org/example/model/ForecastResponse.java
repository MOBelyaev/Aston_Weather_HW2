package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class ForecastResponse {

    private List<Forecast> forecasts;

}
