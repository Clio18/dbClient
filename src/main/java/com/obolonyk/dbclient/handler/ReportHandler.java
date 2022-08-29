package com.obolonyk.dbclient.handler;

import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.report.ConsoleReporter;
import com.obolonyk.dbclient.report.HTMLFreeMarkerReporter;

import java.io.IOException;

public class ReportHandler {

    public static void handle(GeneralData generalData) {
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        consoleReporter.generate();
        if (generalData.isSelect()) {
            HTMLFreeMarkerReporter htmlReporter = new HTMLFreeMarkerReporter(generalData);
            try {
                htmlReporter.generate();
            } catch (IOException e) {
                throw new RuntimeException("Report was not been written" + e.getMessage());
            }
        }
    }
}
