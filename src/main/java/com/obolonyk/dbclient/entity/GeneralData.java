package com.obolonyk.dbclient.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralData {
    private List<String> headers;
    private Map<String, List<String>> values;
    private int updatedRows = -1;

    public GeneralData() {
        this.headers = new ArrayList<>();
        this.values = new HashMap<>();
    }

    public GeneralData(List<String> headers, Map<String, List<String>> values) {
        this.headers = headers;
        this.values = values;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public Map<String, List<String>> getValues() {
        return values;
    }

    public int getUpdatedRows() {
        return updatedRows;
    }

    public void setUpdatedRows(int i) {
        this.updatedRows = i;
    }
}
