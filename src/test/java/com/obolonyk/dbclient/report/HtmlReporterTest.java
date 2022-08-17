package com.obolonyk.dbclient.report;

import com.obolonyk.dbclient.entity.GeneralData;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class HtmlReporterTest {

    @Test
    void test (){
        String expected = """
                <table>
                    <tr>
                        <th>one</th>
                        <th>two</th>
                        <th>three</th>
                    </tr>
                    <tr>
                        <td>1</td>
                        <td>2</td>
                        <td>3</td>
                        <td>11</td>
                    </tr>
                    <tr>
                        <td>4</td>
                        <td>5</td>
                        <td>6</td>
                        <td>44</td>
                    </tr>
                    <tr>
                        <td>7</td>
                        <td>8</td>
                        <td>9</td>
                        <td>77</td>
                    </tr>
                </table>
                """;
        List<String> headers = List.of("one", "two", "three");
        Map<String, List<String>> values = new HashMap<>();
        values.put("one", List.of("1", "2", "3", "11"));
        values.put("two", List.of("4", "5", "6", "44"));
        values.put("three", List.of("7", "8", "9", "77"));
        GeneralData generalData = new GeneralData(headers, values);
        String html = HtmlReporter.createHTML(generalData);
        assertEquals(expected, html);
    }

}