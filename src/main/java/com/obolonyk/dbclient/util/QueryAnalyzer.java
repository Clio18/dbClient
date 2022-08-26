package com.obolonyk.dbclient.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QueryAnalyzer {
    private static final String VALUES = "VALUES";
    public Map<String, String> fieldsAndTypes;
    public Map<String, String> fieldsAndValues;

    public Map<String, String> getFieldsAndTypes() {
        return fieldsAndTypes;
    }

    public void setFieldsAndTypes(Map<String, String> fieldsAndTypes) {
        this.fieldsAndTypes = fieldsAndTypes;
    }

    public Map<String, String> getFieldsAndValues() {
        return fieldsAndValues;
    }

    public void setFieldsAndValues(Map<String, String> fieldsAndValues) {
        this.fieldsAndValues = fieldsAndValues;
    }

    public static Map<String, String> retrieveFieldsAndTypes (String str){
        Matcher m = Pattern.compile("\\((.*?)\\);").matcher(str);
        List<String> collect = m.results()
                .map(x -> x.group(1))
                .collect(Collectors.toList());
        String tableData = collect.get(0);

        List<String> data = Arrays.asList(tableData.split(","));

        Map<String, String> mapFieldsAndTypes = new HashMap<>();
        for (String datum : data) {
            List<String> list = new ArrayList<>();
            String[] split = datum.trim().split(" ");
            String [] array = new String[2];
            System.arraycopy(split, 0, array, 0, 2);
            Collections.addAll(list, array);
            mapFieldsAndTypes.put(list.get(0), list.get(1));
        }
        return mapFieldsAndTypes;
    }

    public static Map<String, String> retrieveFieldsAndValues (String str){
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(str);

        List<String> collect = m.results()
                .map(x -> x.group(1))
                .collect(Collectors.toList());

        String keysMatcher = collect.get(0);
        String valuesMatcher = collect.get(1);

        List<String> listOfKeys = Arrays.asList(keysMatcher.split(","));
        List<String> listOfValues = Arrays.asList(valuesMatcher.split(","));

        Map<String, String> params = new HashMap<>();
        for (int i = 0; i < listOfKeys.size(); i++) {
            params.put(listOfKeys.get(i).trim(), listOfValues.get(i).trim());
        }
        return params;
    }

    public static String creationQueryForPreparedStatement(String str){
        if (str.contains("INSERT")) {
            String[] source = str.split(VALUES);

            String values = source[1];
            int valueCounter = values.split(",").length;

            StringJoiner stringJoiner = new StringJoiner(", ", "(", ")");
            for (int i = 0; i < valueCounter; i++) {
                stringJoiner.add("?");
            }

            String replace = stringJoiner.toString();
            return str.replace(values, replace);
        } else {
            return str;
        }
    }

    public void analyze (String str){
        if(str.contains("CREATE")){
            this.setFieldsAndTypes(retrieveFieldsAndTypes(str));
        }else if (str.contains("INSERT")){
            this.setFieldsAndValues(retrieveFieldsAndValues(str));
        }
    }

    public PreparedStatement constructPreparedStatement(PreparedStatement preparedStatement) throws SQLException {
        Map<String, String> fieldsAndTypes = this.getFieldsAndTypes();
        Map<String, String> fieldsAndValues = this.getFieldsAndValues();
        int count = 1;
        for (String field : fieldsAndTypes.keySet()) {
            String type = fieldsAndTypes.get(field);
            switch(type){
                case ("INT"):
                    String valueInt = fieldsAndValues.get(field);
                    int intValue = Integer.parseInt(valueInt);
                    preparedStatement.setInt(count, intValue);
                    count++;
                    break;
                case ("VARCHAR(255)"):
                    String valueStr = fieldsAndValues.get(field);
                    preparedStatement.setString(count, valueStr);
                    count++;
                    break;
            }
        }
        return preparedStatement;
    }
}
