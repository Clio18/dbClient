package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.util.Constants;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HtmlReporter implements Report {
    private GeneralData generalData;
    private String path;

    public HtmlReporter(GeneralData generalData, String path) {
        this.generalData = generalData;
        this.path = path;
    }

    @Override
    public void generate() {
        String html = createHTML(generalData);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        File reportFile = new File(path, "report" + formatter.format(date) + ".html");
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
        stringBuilder.append(Constants.OPEN_TABLE_TAG);
        stringBuilder.append(Constants.OPEN_TR_TAG);
        for (String header : headers) {
            stringBuilder.append(Constants.OPEN_TH_TAG);
            stringBuilder.append(header);
            stringBuilder.append(Constants.CLOSE_TH_TAG);
        }
        stringBuilder.append(Constants.CLOSE_TR_TAG);
        Set<String> keySet = values.keySet();
        for (String header : keySet) {
            stringBuilder.append(Constants.OPEN_TR_TAG);
            for (String value : values.get(header)) {
                stringBuilder.append(Constants.OPEN_TD_TAG);
                stringBuilder.append(value);
                stringBuilder.append(Constants.CLOSE_TD_TAG);
            }
            stringBuilder.append(Constants.CLOSE_TR_TAG);
        }
        stringBuilder.append(Constants.CLOSE_TABLE_TAG);
        return stringBuilder.toString();
    }
}
