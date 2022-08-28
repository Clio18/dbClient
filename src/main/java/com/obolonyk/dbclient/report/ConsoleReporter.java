package com.obolonyk.dbclient.report;

import com.jakewharton.fliptables.FlipTableConverters;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.util.ConsoleOutput;

public class ConsoleReporter implements Reporter {
    private String[] headers;
    private String[][] values;
    private GeneralData generalData;

    public ConsoleReporter(GeneralData generalData) {
        if (generalData.getHeaders().isEmpty()) {
            this.headers = null;
            this.values = null;
        } else {
            String firstHeader = generalData.getHeaders().get(0);
            int listOfValuesSize = generalData.getValues().get(firstHeader).size();

            int headerSize = generalData.getHeaders().size();
            this.headers = new String[headerSize];
            this.values = new String[listOfValuesSize][headerSize];
        }
        this.generalData = generalData;
    }

    @Override
    public void generate() {
        if (generalData.isSelect()) {
            ConsoleReporter prepareData = this.prepare();
            ConsoleOutput.showTableMessage(FlipTableConverters.fromObjects(prepareData.getHeaders(), prepareData.getValues()));
        } else {
            ConsoleOutput.showUpdatedRowsMessage(generalData.getUpdatedRows());
        }
    }

    ConsoleReporter prepare() {
        for (int i = 0; i < generalData.getHeaders().size(); i++) {
            String header = generalData.getHeaders().get(i);
            headers[i] = header;
        }

        int length = generalData.getValues().get(headers[0]).size();
        for (int i = 0; i < length; i++) {
            for (String header : generalData.getHeaders()) {
                int index = generalData.getHeaders().indexOf(header);
                String value = generalData.getValues().get(header).get(i);
                values[i][index] = value;
            }
        }

        return this;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[][] getValues() {
        return values;
    }

}
