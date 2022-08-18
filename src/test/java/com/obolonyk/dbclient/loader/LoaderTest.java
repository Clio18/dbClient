package com.obolonyk.dbclient.loader;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {

    @Test
    void testGetPropertiesFromFile () throws IOException {
        Properties properties = Loader.getProperties("application.properties");
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
        assertEquals("value", properties.getProperty("key"));
    }

    @Test
    void testGetPropertiesFromEnvironmentVariable () {
        Properties properties = Loader.getProperties();
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
        assertEquals("user", properties.getProperty("user"));
    }

}