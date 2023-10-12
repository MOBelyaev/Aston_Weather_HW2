package org.example.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.example.model.Forecast;
import org.example.model.ForecastResponse;
import org.example.model.Weather;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WeatherService {

    private static WeatherService instance;

    public static synchronized WeatherService getInstance(){
        if(instance == null){
            instance = new WeatherService();
        }
        return instance;
    }
    private static final String API_KEY = "c75806f9-0a71-447c-affc-8a6efb65821d";
    private final ObjectMapper mapper = ObjectMapperService.getInstance();



    public Optional<Weather> getFactWeather(String lat, String lon) {

        try {
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.weather.yandex.ru")
                    .setPath("v2/fact")
                    .addParameter("lat", lat)
                    .addParameter("lon", lon)
                    .build();

            HttpGet request = new HttpGet(uri);

            request.addHeader("X-Yandex-API-Key", API_KEY);

            CloseableHttpClient client = HttpClientBuilder.create().build();

            String response = new String(
                    client.execute(request).getEntity().getContent().readAllBytes(),
                    StandardCharsets.UTF_8
            );
            client.close();

            System.out.println();
            System.out.println(response);
            System.out.println();

            return Optional.of(mapper.readValue(response, Weather.class));

        } catch (Exception ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Forecast> getForecastWeather(String lat, String lon, int days){

        try{
            URI uri = new URIBuilder()
                    .setScheme("https")
                    .setHost("api.weather.yandex.ru")
                    .setPath("v2/forecast")
                    .addParameter("lat", lat)
                    .addParameter("lon", lon)
                    .addParameter("limit", Integer.toString(days))
                    .build();

            HttpGet request = new HttpGet(uri);

            request.addHeader("X-Yandex-API-Key", API_KEY);

            CloseableHttpClient client = HttpClientBuilder.create().build();

            String response = new String(client.execute(request).getEntity().getContent().readAllBytes(),
                    StandardCharsets.UTF_8
            );
            client.close();


            return mapper.readValue(response, ForecastResponse.class).getForecasts();

        }
        catch (Exception ex){
            ex.printStackTrace();
            return Collections.emptyList();
        }


    }

}
