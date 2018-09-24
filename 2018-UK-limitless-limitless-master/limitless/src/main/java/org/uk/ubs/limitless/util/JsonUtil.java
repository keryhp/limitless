package org.uk.ubs.limitless.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.uk.ubs.limitless.domain.MessageContent;

import java.io.File;
import java.io.FileReader;

public class JsonUtil {

    private static Logger LOG = Logger.getLogger(JsonUtil.class);

    public static String convertToJson(MessageContent messageContent) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(messageContent);
        } catch (Exception e) {
            LOG.error("Error occurred during Json conversion " + e.getMessage());
            LOG.error(e.getStackTrace());
        }
        return null;
    }

    public static Object convertFromJson(String source, Class clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(new File(source), clazz);
            return obj;
        } catch (Exception e) {
            LOG.error("Error occurred during Json conversion " + e.getMessage());
            LOG.error(e.getStackTrace());
        }
        return null;
    }

    public static Object convertFromJsonObject(JSONObject source, Class clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object obj = mapper.readValue(source.toString(), clazz);
            return obj;
        } catch (Exception e) {
            LOG.error("Error occurred during Json conversion " + e.getMessage());
            LOG.error(e.getStackTrace());
        }
        return null;
    }

    public static JSONObject readFromJson(String source) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
        try {
            Object obj = (Object) parser.parse(new FileReader(source));
            jsonObject = (JSONObject) obj;
            LOG.info(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
