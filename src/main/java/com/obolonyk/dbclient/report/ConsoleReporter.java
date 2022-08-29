package com.obolonyk.dbclient.report;

import com.jakewharton.fliptables.FlipTableConverters;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.util.ConsoleOutput;

import java.util.List;

public class ConsoleReporter implements Reporter {
    private GeneralData generalData;

    public ConsoleReporter(GeneralData generalData) {
        this.generalData = generalData;
    }

    @Override
    public void generate() {
        if (generalData.isSelect()) {

            List<String> headersList = generalData.getHeaders();
            String [] headers = new String[headersList.size()];
            headersList.toArray(headers);

            String[][] values = prepare(generalData);
            ConsoleOutput.showTableMessage(FlipTableConverters.fromObjects(headers, values));
        } else {
            ConsoleOutput.showUpdatedRowsMessage(generalData.getUpdatedRows());
        }
    }

    //TODO: iterator
    static String[][] prepare(GeneralData generalData) {
        List<String> headers = generalData.getHeaders();
        //int length = generalData.getValues().get(headers.get(0)).size();
        int length = generalData.getData().size()/headers.size();

        String[][] values = new String[length][headers.size()];

        int counter = 0;
        for (int i = 0; i < length; i++) {
            for (String header : headers) {
                int index = headers.indexOf(header);

                //String value = generalData.getValues().get(header).get(i);
                String value = generalData.getData().get(counter);
                counter++;

                values[i][index] = value;
            }
        }
        return values;
    }

}
