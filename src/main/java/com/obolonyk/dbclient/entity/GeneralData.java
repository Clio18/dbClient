package com.obolonyk.dbclient.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneralData {
    private List<String> headers;
    private List<String> data;
    private Map<String, List<String>> values;
    private int updatedRows = -1;
    private boolean isSelect;

    public GeneralData(List<String> headers, Map<String, List<String>> values) {
        this.headers = headers;
        this.values = values;
    }

    public GeneralData() {
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
        this.values = new HashMap<>();

    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public Map<String, List<String>> getValues() {
        return values;
    }

    public List<String> getData() {
        return data;
    }

    public int getUpdatedRows() {
        return updatedRows;
    }

    public void setUpdatedRows(int i) {
        this.updatedRows = i;
    }
}
