package com.obolonyk.dbclient.executor;

import com.obolonyk.dbclient.entity.GeneralData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExecutorTest {
    private final String id = "ID";
    private final String name = "NAME";
    private final String lastName = "LAST_NAME";
    private GeneralData generalData;
    private  ResultSet mockRs;

    @BeforeEach
    void init() throws SQLException {
        generalData = new GeneralData();
        mockRs = mock(ResultSet.class);
        ResultSetMetaData rsmd = mock(ResultSetMetaData.class);

        when(mockRs.getString(id)).thenReturn("1");
        when(mockRs.getString(name)).thenReturn("A");
        when(mockRs.getString(lastName)).thenReturn("AA");
        doReturn(true).doReturn(true).doReturn(true).doReturn(false).when(mockRs).next();

        when(rsmd.getColumnCount()).thenReturn(3);
        when(rsmd.getColumnName(1)).thenReturn(id);
        when(rsmd.getColumnName(2)).thenReturn(name);
        when(rsmd.getColumnName(3)).thenReturn(lastName);
        when(mockRs.getMetaData()).thenReturn(rsmd);
    }

    @Test
    @DisplayName("test Extracted Values")
    void testExtractedValues() throws SQLException {
        generalData.getHeaders().add(id);
        generalData.getHeaders().add(name);
        generalData.getHeaders().add(lastName);

        generalData.getValues().put(id, new ArrayList<>());
        generalData.getValues().put(name, new ArrayList<>());
        generalData.getValues().put(lastName, new ArrayList<>());

        Map<String, List<String>> values = generalData.getValues();
        List<String> data = generalData.getData();

        assertTrue(values.get(id).isEmpty());
        assertTrue(values.get(name).isEmpty());
        assertTrue(values.get(lastName).isEmpty());

        assertTrue(data.isEmpty());

        Executor.extractedValues(generalData, mockRs);

        Map<String, List<String>> valuesAfter = generalData.getValues();
        List<String> dataAfter = generalData.getData();

        assertFalse(valuesAfter.get(id).isEmpty());
        assertFalse(valuesAfter.get(name).isEmpty());
        assertFalse(valuesAfter.get(lastName).isEmpty());

        assertFalse(dataAfter.isEmpty());
    }

    @Test
    @DisplayName("test Extracted Headers(")
    void testExtractedHeaders() throws SQLException {
        List<String> headers = generalData.getHeaders();
        assertTrue(headers.isEmpty());

        List<String> ids = generalData.getValues().get(id);
        List<String> names = generalData.getValues().get(name);
        List<String> lastNames = generalData.getValues().get(lastName);

        assertNull(ids);
        assertNull(names);
        assertNull(lastNames);

        Executor.extractedHeaders(generalData, mockRs);

        List<String> headersAfter = generalData.getHeaders();
        assertFalse(headersAfter.isEmpty());

        List<String> idsAfter = generalData.getValues().get(id);
        List<String> namesAfter = generalData.getValues().get(name);
        List<String> lastNamesAfter = generalData.getValues().get(lastName);

        assertNotNull(idsAfter);
        assertNotNull(namesAfter);
        assertNotNull(lastNamesAfter);
    }
}