package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class HtmlManualReporterTest {

    @Test
    @DisplayName("test Create HTML")
    void testCreateHTML() {
        String expected = """
                <!DOCTYPE html>
                <html lang="en">
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
                                
                <body>
                <h1>Report:</h1>
                <script
                        src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0/dist/js/bootstrap.bundle.min.js"
                        integrity="sha384-A3rJD856KowSb7dwlZdYEkO39Gagi7vIsF0jrRAoQmDKKtQBHUuLZ9AsSv4jD4Xa"
                        crossorigin="anonymous"
                ></script>   
                            
                <div>
                <table class="table table-warning">
                    <tr>
                        <th>ID</th>
                        <th>NAME</th>
                        <th>LAST_NAME</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>Ram</td>
                        <td>Ahmed</td>
                    </tr>
                    <tr>
                        <td>2</td>
                        <td>And</td>
                        <td>Babad</td>
                    </tr>
                    <tr>
                        <td>3</td>
                        <td>Mes</td>
                        <td>Medab</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>Tak</td>
                        <td>Nuno</td>         
                    </tr>
                </table>
                </div>
                </body>
                </html>
                """;
        List<String> headers = List.of("ID", "NAME", "LAST_NAME");
        List<String> data = List.of("1","Ram","Ahmed", "2","And","Babad", "3","Mes","Medab", "4", "Tak", "Nuno");
        GeneralData generalData = new GeneralData();
        generalData.setHeaders(headers);
        generalData.setData(data);
        String html = HtmlManualReporter.createHTML(generalData);
        assertEquals(expected, html);
    }

}