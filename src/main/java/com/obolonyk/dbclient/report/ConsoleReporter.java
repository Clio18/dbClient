package com.obolonyk.dbclient.report;

import com.jakewharton.fliptables.FlipTableConverters;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.util.ConsoleOutput;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConsoleReporter {
    private GeneralData generalData;

    public void generate() {
        if (generalData.isSelect()) {
            List<String> headersList = generalData.getHeaders();
            String[] headers = new String[headersList.size()];
            headersList.toArray(headers);

            String[][] values = prepare(generalData);
            String table = FlipTableConverters.fromObjects(headers, values);
            ConsoleOutput.showTableMessage(table);
        } else {
            int updatedRows = generalData.getUpdatedRows();
            ConsoleOutput.showUpdatedRowsMessage(updatedRows);
        }
    }


    static String[][] prepare(GeneralData generalData) {
        List<String> headers = generalData.getHeaders();
        int length = generalData.getData().size() / headers.size();

        String[][] values = new String[length][headers.size()];

        int counter = 0;
        for (int i = 0; i < length; i++) {
            for (String header : headers) {
                int index = headers.indexOf(header);
                String value = generalData.getData().get(counter);
                counter++;
                values[i][index] = value;
            }
        }
        return values;
    }

}
