package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;

public class ReportHandler {

    public static void handle(GeneralData generalData) {
        Reporter consoleReporter = new ConsoleReporter(generalData);
        consoleReporter.generate();
        if (generalData.getUpdatedRows() == -1) {
            Reporter htmlReporter = new HTMLFreeMarkerReporter(generalData);
            htmlReporter.generate();
        }
    }
}
