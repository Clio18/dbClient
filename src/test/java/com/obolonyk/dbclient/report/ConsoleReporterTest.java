package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReporterTest {

    @Test
    @DisplayName("test Console Data Show Method")
    void testConsoleDataShowMethod() {
        List<String> headers = List.of("one", "two", "three");
        Map<String, List<String>> values = new HashMap<>();
        values.put("one", List.of("1", "2", "3", "11"));
        values.put("two", List.of("4", "5", "6", "44"));
        values.put("three", List.of("7", "8", "9", "77"));
        GeneralData generalData = new GeneralData(headers, values);
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        consoleReporter.generate();
    }

    @Test
    @DisplayName("test Console Data Prepare Method")
    void testConsoleDataPrepareMethod() {
        List<String> headers = List.of("one", "two", "three");
        Map<String, List<String>> values = new HashMap<>();
        values.put("one", List.of("1", "2", "3"));
        values.put("two", List.of("4", "5", "6"));
        values.put("three", List.of("7", "8", "9"));
        GeneralData generalData = new GeneralData(headers, values);
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        ConsoleReporter prepare = consoleReporter.prepare();
        assertEquals(3, prepare.getHeaders().length);
        assertEquals(3, prepare.getValues().length);
    }

}