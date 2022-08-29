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
        List<String> headers = new ArrayList<>();
        headers.add(id);
        headers.add(name);
        headers.add(lastName);
        generalData.setHeaders(headers);

        List<String> data = generalData.getData();
        assertNull(data);

        Executor.extractedValues(generalData, mockRs);

        List<String> dataAfter = generalData.getData();
        assertFalse(dataAfter.isEmpty());
    }

    @Test
    @DisplayName("test Extracted Headers")
    void testExtractedHeaders() throws SQLException {
        List<String> headers = generalData.getHeaders();
        assertNull(headers);

        Executor.extractedHeaders(generalData, mockRs);

        List<String> headersAfter = generalData.getHeaders();
        assertFalse(headersAfter.isEmpty());
    }
}