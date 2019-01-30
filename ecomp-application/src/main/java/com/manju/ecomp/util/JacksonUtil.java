package com.manju.ecomp.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manju.ecomp.model.ves.v5.EventV5;
import com.manju.ecomp.model.ves.v5.heartbeat.HeartBeatEvent;
import com.manju.ecomp.model.ves.v5.measurement.MeasurementEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JacksonUtil {
    static ObjectMapper objectMapper;
    static JsonFactory factory;
    static {
        factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        factory.enable(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
        objectMapper = new ObjectMapper(factory);
    }

    public static EventV5 getEvent(int release, String event){
        EventV5 eventObject = null;
        try {
            eventObject = objectMapper.readValue(event, EventV5.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return eventObject;
    }


    public static String convertFaultEventToJson(com.manju.ecomp.model.ves.v5.fault.FaultEvent event){
        String jsonInString = null;
        try {
            jsonInString = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

    public static String convertHeartBeatEventToJSON(HeartBeatEvent event){
        String jsonInString = null;
        try {
            jsonInString = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

    public static String convertMeasurementEventToJSON(MeasurementEvent event){
        String jsonInString = null;
        try {
            jsonInString = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonInString;
    }

}
