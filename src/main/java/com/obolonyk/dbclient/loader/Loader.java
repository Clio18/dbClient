package com.obolonyk.dbclient.loader;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class Loader {

    private static final String DB_URL = "url";
    private static final String USER_NAME = "user";
    private static final String PASSWORD = "password";
    private static final String DRIVER = "driver";

    public static Properties load (String propertiesFileName) throws IOException {
        Properties properties = getProperties();
        if(properties.isEmpty()){
            properties = getProperties(propertiesFileName);
        }
        log.info("The properties were loaded from from application properties file");
        return properties;
    }

   static Properties getProperties(String propertiesFileName) throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(propertiesFileName)) {
            properties.load(inputStream);
        }
        return properties;
    }

    static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.setProperty(USER_NAME, System.getenv(USER_NAME));
            properties.setProperty(PASSWORD, System.getenv(PASSWORD));
            properties.setProperty(DRIVER, System.getenv(DRIVER));
            properties.setProperty(DB_URL, System.getenv(DB_URL));
        }catch (NullPointerException e){
            return properties;
        }
        log.info("The properties were loaded from from environment variable");
      return properties;
    }
}
