package com.obolonyk.dbclient.report;

import com.jakewharton.fliptables.FlipTableConverters;
import com.obolonyk.dbclient.entity.GeneralData;

public class ConsoleReporter implements Report {
    private String[] headers;
    private String[][] values;
    private GeneralData generalData;

    public ConsoleReporter(GeneralData generalData) {
        if (generalData.getHeaders().isEmpty()) {
            this.headers = null;
            this.values = null;
        } else {
            String firstHeader = generalData.getHeaders().get(0);
            int size = generalData.getValues().get(firstHeader).size();
            this.headers = new String[generalData.getHeaders().size()];
            this.values = new String[size][generalData.getHeaders().size()];
        }
        this.generalData = generalData;
    }

    @Override
    public void generate() {
        ConsoleReporter prepareData = this.prepare();
        System.out.println(FlipTableConverters.fromObjects(prepareData.getHeaders(), prepareData.getValues()));
    }

    public void show() {
        System.out.println(generalData.getUpdatedRows());
    }

    public ConsoleReporter prepare() {
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
