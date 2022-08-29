package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.h2.server.web.PageParser.escapeHtml;

public class HtmlManualReporter implements Reporter {
    private GeneralData generalData;

    //TODO
    private static final String PATH = "src/main/resources/reports";

    private static final String OPEN_TABLE_TAG = "<table class=\"table table-warning\">\n";
    private static final String CLOSE_TABLE_TAG = "</table>\n";
    private static final String OPEN_TR_TAG = "    <tr>\n";
    private static final String CLOSE_TR_TAG = "    </tr>\n";
    private static final String OPEN_TH_TAG = "        <th>";
    private static final String CLOSE_TH_TAG = "</th>\n";
    private static final String OPEN_TD_TAG = "        <td>";
    private static final String CLOSE_TD_TAG = "</td>\n";

    private static final String DOC_TYPE = "<!DOCTYPE html>\n";
    private static final String HTML_LANG = "<html lang=\"en\">\n";
    private static final String HEAD = """
            <head>
                <meta charset="utf-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <title>Products</title>
                <link
                        href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/css/bootstrap.min.css"
                        rel="stylesheet"
                        integrity="sha384-gH2yIJqKdNHPEq0n4Mqa/HGKIhSkIHeL5AyhkYV8i59U5AR6csBvApHHNl/vI1Bx"
                        crossorigin="anonymous"
                />
            </head>
            \n""";

    private static final String STYLE = """
            <style>
                h1 {
              font-family: verdana;
              font-size: 20px;
              font-weight: bold;
              margin-left: 50px;
              margin-top: 20px;
            }
            div {
              width: 600px;
              margin-left: 50px;
              text-align: center;
            }
            </style>
            \n""";

    private static final String OPEN_BODY = "<body>\n";
    private static final String CLOSE_BODY = "</body>\n";

    private static final String HEADER_H1 = "<h1>Report:</h1>\n";

    private static final String SCRIPT = """
            <script
                    src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
                    integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
                    crossorigin="anonymous"
            ></script>
            \n""";

    private static final String OPEN_DIV = "<div>\n";
    private static final String CLOSE_DIV = "</div>\n";
    private static final String CLOSE_HTML = "</html>\n";

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
        //TODO: test
        File reportFile = new File(PATH, "report_" + formatter.format(date) + ".html");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(reportFile)))) {
            bufferedWriter.write(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String createHTML(GeneralData generalData) {
        List<String> headers = generalData.getHeaders();
        List<String> data = generalData.getData();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DOC_TYPE);
        stringBuilder.append(HTML_LANG);
        stringBuilder.append(HEAD);
        stringBuilder.append(STYLE);
        stringBuilder.append(OPEN_BODY);
        stringBuilder.append(HEADER_H1);
        stringBuilder.append(SCRIPT);
        stringBuilder.append(OPEN_DIV);
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
        int sizeOfListValues = data.size()/headers.size();

        int counter = 0;
        for (int i = 0; i < sizeOfListValues; i++) {
            stringBuilder.append(OPEN_TR_TAG);
            for (String header : headers) {

                stringBuilder.append(OPEN_TD_TAG);
                String value = data.get(counter);
                counter++;

                String escapedValue = escapeHtml(value);
                stringBuilder.append(escapedValue);
                stringBuilder.append(CLOSE_TD_TAG);
            }
            stringBuilder.append(CLOSE_TR_TAG);
        }

        stringBuilder.append(CLOSE_TABLE_TAG);
        stringBuilder.append(CLOSE_DIV);
        stringBuilder.append(CLOSE_BODY);
        stringBuilder.append(CLOSE_HTML);
        return stringBuilder.toString();
    }
}
