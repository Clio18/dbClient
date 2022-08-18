package com.obolonyk.dbclient.loader;

import com.obolonyk.dbclient.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Loader {

    public static Properties load (String propertiesFileName) throws IOException {
        Properties properties = getProperties();
        if(properties.isEmpty()){
            properties = getProperties(propertiesFileName);
        }
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
        properties.setProperty(Constants.USER_NAME, System.getenv(Constants.USER_NAME));
        properties.setProperty(Constants.PASSWORD, System.getenv(Constants.PASSWORD));
        properties.setProperty(Constants.DRIVER, System.getenv(Constants.DRIVER));
        properties.setProperty(Constants.DB_URL, System.getenv(Constants.DB_URL));
        return properties;
    }
}
