package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HtmlManualReporterTest {

    @Test
    @DisplayName("test Create HTML")
    void testCreateHTML() {
        String expected = """
                <table>
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
                """;
        List<String> headers = List.of("ID", "NAME", "LAST_NAME");
        Map<String, List<String>> values = new HashMap<>();
        values.put("ID", List.of("1", "2", "3", "4"));
        values.put("NAME", List.of("Ram", "And", "Mes", "Tak"));
        values.put("LAST_NAME", List.of("Ahmed", "Babad", "Medab", "Nuno"));
        GeneralData generalData = new GeneralData(headers, values);
        String html = HtmlManualReporter.createHTML(generalData);
        assertEquals(expected, html);
    }

}