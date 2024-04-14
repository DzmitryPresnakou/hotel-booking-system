package com.presnakov.hotelBookingSystem.datasourse;

import java.io.IOException;
import java.util.Properties;

public final class PropertiesUtil {

    private static final String APPLICATION_PROPERTIES = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private PropertiesUtil() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }

    private static void loadProperties() {
        try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES)) {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}