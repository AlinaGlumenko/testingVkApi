package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

public class DataHandler {

    private static ObjectMapper mapper = new ObjectMapper();

    public static <T> T asObject(final String str, Class<T> tClass) {
        try {
            return mapper.readValue(str, tClass);
        } catch (Exception e) {
            Logger.getRootLogger().error(e);
            throw new RuntimeException(e);
        }
    }
}
