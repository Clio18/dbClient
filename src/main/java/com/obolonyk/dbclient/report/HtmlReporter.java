package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;

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
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String createHTML(GeneralData generalData) {
        List<String> headers = generalData.getHeaders();
        Map<String, List<String>> values = generalData.getValues();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<table>");
        stringBuilder.append("<tr>");
        for (String header : headers) {
            stringBuilder.append("<th>");
            stringBuilder.append(header);
            stringBuilder.append("</th>");
        }
        stringBuilder.append("</tr>");
        stringBuilder.append("<tr>");
        Set<String> keySet = values.keySet();
        for (String header : keySet) {
            for (String value : values.get(header)) {
                stringBuilder.append("<td>");
                stringBuilder.append(value);
                stringBuilder.append("</td>");
            }
        }
        stringBuilder.append("</tr>");
        stringBuilder.append("</table>");
        return stringBuilder.toString();
    }
}
