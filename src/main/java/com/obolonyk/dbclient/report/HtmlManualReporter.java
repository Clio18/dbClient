package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.h2.server.web.PageParser.escapeHtml;

public class HtmlManualReporter implements Reporter {
    private GeneralData generalData;

    private static final String PATH = "src/main/resources/reports";

    private static final String OPEN_TABLE_TAG = "<table>\n";
    private static final String CLOSE_TABLE_TAG = "</table>\n";
    private static final String OPEN_TR_TAG = "    <tr>\n";
    private static final String CLOSE_TR_TAG = "    </tr>\n";
    private static final String OPEN_TH_TAG = "        <th>";
    private static final String CLOSE_TH_TAG = "</th>\n";
    private static final String OPEN_TD_TAG = "        <td>";
    private static final String CLOSE_TD_TAG = "</td>\n";

    private SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public HtmlManualReporter(GeneralData generalData) {
        this.generalData = generalData;
    }

    @Override
    public void generate() {
        String html = createHTML(generalData);
        Date date = new Date();
        File dir = new File(PATH);
        if (!dir.exists()){
            dir.mkdir();
        }
        File reportFile = new File(PATH, "report_" + formatter.format(date) + ".html");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile)))) {
            bufferedWriter.write(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String createHTML(GeneralData generalData) {
        List<String> headers = generalData.getHeaders();
        Map<String, List<String>> values = generalData.getValues();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(OPEN_TABLE_TAG);
        stringBuilder.append(OPEN_TR_TAG);
        for (String header : headers) {
            stringBuilder.append(OPEN_TH_TAG);

            String escapedHeader = escapeHtml(header);
            stringBuilder.append(escapedHeader);

            stringBuilder.append(CLOSE_TH_TAG);
        }
        stringBuilder.append(CLOSE_TR_TAG);

        //to get the size of the list of values
        Set<String> keySet = values.keySet();
        String firstHeader = keySet.iterator().next();
        int sizeOfListValues = values.get(firstHeader).size();

        for (int i = 0; i < sizeOfListValues; i++) {
            stringBuilder.append(OPEN_TR_TAG);
            for (String header : headers) {
                stringBuilder.append(OPEN_TD_TAG);
                String value = values.get(header).get(i);

                String escapedValue = escapeHtml(value);
                stringBuilder.append(escapedValue);
                stringBuilder.append(CLOSE_TD_TAG);
            }
            stringBuilder.append(CLOSE_TR_TAG);
        }
        stringBuilder.append(CLOSE_TABLE_TAG);
        return stringBuilder.toString();
    }
}
