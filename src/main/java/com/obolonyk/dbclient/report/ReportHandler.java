package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;

public class ReportHandler {

    public static void handle(GeneralData generalData, String path) {
        ConsoleReporter consoleReporter = new ConsoleReporter(generalData);
        if (generalData.getUpdatedRows() != -1) {
            consoleReporter.show();
        } else {
            consoleReporter.generate();
            HtmlReporter htmlReporter = new HtmlReporter(generalData, path);
            htmlReporter.generate();
        }
    }
}
