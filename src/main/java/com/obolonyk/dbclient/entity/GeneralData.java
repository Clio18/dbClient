package com.obolonyk.dbclient.entity;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;


public class GeneralData {
    private List<String> headers;
    private List<String> data;
    private boolean isSelect;
    private int updatedRows;


    public GeneralData() {
//        this.headers = new ArrayList<>();
//        this.data = new ArrayList<>();
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public GeneralData(List<String> headers) {
        //this.headers = headers;
    }

    public List<String> getHeaders() {
        return headers;
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

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
