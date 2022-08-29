package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleReporterTest {

    @Test
    @DisplayName("test Console Data Show Method")
    void testConsoleDataShowMethodIsNotSelect() {
        List<String> headers = List.of("one", "two", "three");
        List<String> data = List.of("1", "11", "111", "2", "22", "222", "3", "33", "333");
        GeneralData generalData = new GeneralData();
        generalData.setHeaders(headers);
        generalData.setData(data);
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        consoleReporter.generate();
    }

    @Test
    @DisplayName("test Console Data Show Method")
    void testConsoleDataShowMethodSelect() {
        List<String> headers = List.of("one", "two", "three");
        List<String> data = List.of("1", "11", "111", "2", "22", "222", "3", "33", "333");
        GeneralData generalData = new GeneralData();
        generalData.setHeaders(headers);
        generalData.setData(data);
        generalData.setSelect(true);
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        consoleReporter.generate();
    }

    @Test
    @DisplayName("test Console Data Prepare Method")
    void testConsoleDataPrepareMethod() {
        List<String> headers = List.of("one", "two", "three");
        List<String> data = List.of("1", "11", "111", "2", "22", "222", "3", "33", "333");
        GeneralData generalData = new GeneralData();
        generalData.setHeaders(headers);
        generalData.setData(data);
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        String[][] prepare = consoleReporter.prepare(generalData);
        assertEquals(3, prepare.length);
    }

}