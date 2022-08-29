package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.util.PageGenerator;
import lombok.AllArgsConstructor;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class HTMLFreeMarkerReporter {
    private GeneralData generalData;
    private static final String PATH = "src/main/resources/reports";

    public void generate() throws IOException {
        List<String> headers = generalData.getHeaders();
        List<String> data = generalData.getData();
        Map<String, Object> param = new HashMap<>();

        param.put("headers", headers);
        param.put("data", data);

        PageGenerator pageGenerator = PageGenerator.instance();
        String report = pageGenerator.getPage("record.html", param);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        File reportFile = new File(PATH, "report_" + formatter.format(date) + ".html");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile)))) {
            bufferedWriter.write(report);
        }
    }
}
