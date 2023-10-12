package org.example;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.model.Weather;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class JacksonTest {

    @Test
    @SneakyThrows
    void test(){
        Class<Weather> type = Weather.class;

        String responce = "{\"obs_time\":1697049764,\"uptime\":1697049764,\"temp\":1,\"feels_like\":-3,\"icon\":\"skc_n\",\"condition\":\"clear\",\"cloudness\":0,\"prec_type\":0,\"prec_prob\":0,\"prec_strength\":0,\"is_thunder\":false,\"wind_speed\":2.5,\"wind_dir\":\"s\",\"pressure_mm\":754,\"pressure_pa\":1005,\"humidity\":69,\"daytime\":\"n\",\"polar\":false,\"season\":\"autumn\",\"source\":\"station\",\"soil_moisture\":0.32,\"soil_temp\":4,\"uv_index\":0,\"wind_gust\":5.3}";

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(responce);

        for (Field field : type.getDeclaredFields()) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();
            JsonProperty annotation = field.getAnnotation(JsonProperty.class);
            JsonNode node;
            if (annotation != null){
                String JsonKey = annotation.value();
                node = root.findPath(JsonKey);
            } else {
                node = root.findPath(fieldName);
            }
            System.out.println(node);
        }

        Constructor<Weather> constructor = type.getConstructor();
        Weather weather = constructor.newInstance();


    }

}
