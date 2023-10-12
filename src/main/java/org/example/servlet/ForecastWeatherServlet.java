package org.example.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.example.model.Forecast;
import org.example.service.ObjectMapperService;
import org.example.service.WeatherService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/weather/forecast")
public class ForecastWeatherServlet extends HttpServlet {

    private final ObjectMapper mapper = ObjectMapperService.getInstance();
    private final WeatherService weatherService = WeatherService.getInstance();

    private Integer parser(String days){
        try{
            return Integer.valueOf(days);
        }catch (Exception ex){
            return null;
        }

    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String lat = req.getParameter("lat");
        String lon = req.getParameter("lon");
        Integer days = parser(req.getParameter("days"));

        PrintWriter writer = resp.getWriter();

        if(StringUtils.isBlank(lat) || StringUtils.isBlank(lon) || days == null){
            resp.setStatus(HttpStatus.SC_UNPROCESSABLE_ENTITY);
            writer.println("Wrong parameters: lat, lon , days");
            writer.close();
            return;
        }

        List<Forecast> result = weatherService.getForecastWeather(lat, lon, days);

        resp.setStatus(HttpStatus.SC_OK);
        writer.println(mapper.writeValueAsString(result));
        writer.close();

    }

}
