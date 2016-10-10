package com.danger.study.tools.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by danger on 2016/6/21.
 */
public class JsonUtils {

    public static String getJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        StringWriter sw = new StringWriter();
        try {
            JsonGenerator gen = mapper.getFactory().createGenerator(sw);
            mapper.writeValue(gen, object);
            gen.flush();
            gen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static <T>T getObject(String json,Class<T> clazz){
        T obj = null;
        try {
            obj = new ObjectMapper().readValue(json,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
