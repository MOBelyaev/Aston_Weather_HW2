package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.example.model.Weather;
import org.example.service.ObjectMapperService;
import org.example.service.WeatherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.Optional;

@WebServlet("/weather/fact")
public class FactWeatherServlet extends HttpServlet {

    private final ObjectMapper mapper = ObjectMapperService.getInstance();
    private final WeatherService weatherService = WeatherService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");

        PrintWriter writer = resp.getWriter();

        if(StringUtils.isBlank(lat) || StringUtils.isBlank(lon)){
            resp.setStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY);
            writer.println("Wrong parameters: lat, lon");
            writer.close();
            return;
        }

        Optional<Weather> weather = weatherService.getFactWeather(lat, lon);

        if (weather.isPresent()) {
            resp.setStatus(HttpStatus.SC_OK);
            writer.println(mapper.writeValueAsString(weather.get()));
        } else {
            resp.setStatus(HttpStatus.SC_NOT_FOUND);
            writer.println("Weather for lat: " + lat + " , lon: " + lon + " not found");
        }

    }

}
