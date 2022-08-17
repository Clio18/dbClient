package com.obolonyk.dbclient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class StarterITest {

    Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
    Statement statement = conn.createStatement();

    StarterITest() throws SQLException {
    }


    @BeforeEach
    void init () throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS USERS(ID INT PRIMARY KEY NOT NULL UNIQUE, NAME VARCHAR(255), LAST_NAME VARCHAR(255));");
        statement.executeUpdate("INSERT INTO USERS VALUES (1, 'Ramesh', 'Ahmed');");
        statement.executeUpdate("INSERT INTO USERS VALUES (2, 'Andrew', 'Babad');");
        statement.executeUpdate("INSERT INTO USERS VALUES (3, 'Ramesh', 'Medab');");
    }

    @AfterEach
    void endedUp() throws SQLException {
        statement.execute("DROP TABLE users;");
    }

    @Test
    void testReadFromApplicationPropertiesFile() throws IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream is = loader.getResourceAsStream("application.properties")) {
            properties.load(is);
        }
        assertNotNull(properties);
        assertEquals("value", properties.getProperty("key"));
    }

    @Test
    void testPrintDbTableAfterSelect() throws SQLException {
        ResultSet resultSet = statement.executeQuery("SELECT * from users");
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();
        while (resultSet.next()) {
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = resultSet.getString(i);
                System.out.print(columnValue + " " + rsmd.getColumnName(i));
            }
            System.out.println("");
        }
    }

    @Test
    void testCheckResultAfterInsert() throws SQLException {
        int i = statement.executeUpdate("INSERT INTO Users VALUES (4, 'Simpson', 'Springfield');");
        assertEquals(1, i);
    }

}