package com.kharevich.ratingservice.util.mapper.desializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class DurationDeserializer extends JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String timeString = p.getText();
        LocalTime time = LocalTime.parse(timeString); // Парсим "00:03" в LocalTime
        return Duration.ofHours(time.getHour()).plusMinutes(time.getMinute());
    }
}
