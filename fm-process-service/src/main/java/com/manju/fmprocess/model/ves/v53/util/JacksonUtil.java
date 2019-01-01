package com.manju.fmprocess.model.ves.v53.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.manju.fmprocess.model.ves.v53.model.fault.FaultEvent;
import com.manju.fmprocess.model.ves.v53.model.heartbeat.HeartBeatEvent;
import com.manju.fmprocess.model.ves.v53.model.measurement.MeasurementEvent;

public class JacksonUtil {
    static ObjectMapper objectMapper;
    static JsonFactory factory;
    static {
        factory = new JsonFactory();
        factory.enable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        factory.enable(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER);
        objectMapper = new ObjectMapper(factory);
    }

    public static String convertFaultEventToJson(FaultEvent event){
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
