package com.obolonyk.dbclient.loader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class LoaderTest {

    @Test
    @DisplayName("test GetProperties From File")
    void testGetPropertiesFromFile () throws IOException {
        Properties properties = Loader.getProperties("application.properties");
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
        assertEquals("value", properties.getProperty("key"));
    }

    @Test
    @DisplayName("test GetProperties From Environment Variable")
    void testGetPropertiesFromEnvironmentVariable () {
        Properties properties = Loader.getProperties();
        assertNotNull(properties);
        assertFalse(properties.isEmpty());
        assertEquals("sa", properties.getProperty("user"));
    }

}