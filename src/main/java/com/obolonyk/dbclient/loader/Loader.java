package com.obolonyk.dbclient.loader;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class Loader {

    private static final String DB_URL = "url";
    private static final String USER_NAME = "user";
    private static final String PASSWORD = "password";

    private static final String PATH_TO_PROPS = "application.properties";

    private static final Map<String, Properties> cachedProps = new ConcurrentHashMap<>();

    public static DataSource getDataSource() throws IOException {
        Properties props = getProps();
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUsername(props.getProperty(USER_NAME));
        dataSource.setPassword(props.getProperty(PASSWORD));
        dataSource.setUrl(props.getProperty(DB_URL));
        return dataSource;
    }

    private static Properties getProps() throws IOException {
        Properties properties = getPropertiesFromEnv();
        if (properties.isEmpty()) {
            if (!cachedProps.containsKey(PATH_TO_PROPS)) {
                properties = getPropertiesFromFile();
                cachedProps.put(PATH_TO_PROPS, properties);
                return properties;
            } else {
                return cachedProps.get(PATH_TO_PROPS);
            }
        }
        return properties;
    }

    static Properties getPropertiesFromFile() throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = loader.getResourceAsStream(PATH_TO_PROPS)) {
            properties.load(inputStream);
        }
        log.info("The properties were loaded from from application properties file");
        return properties;
    }

    static Properties getPropertiesFromEnv() {
        Properties properties = new Properties();
        try {
            properties.setProperty(USER_NAME, System.getenv(USER_NAME));
            properties.setProperty(PASSWORD, System.getenv(PASSWORD));
            properties.setProperty(DB_URL, System.getenv(DB_URL));
        } catch (NullPointerException e) {
            log.info("Some of the properties is missing, they will be loaded from properties file");
            return properties;
        }
        log.info("The properties were loaded from from environment variable");
        return properties;
    }
}
