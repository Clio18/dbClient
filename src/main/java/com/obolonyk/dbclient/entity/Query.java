package com.obolonyk.dbclient.entity;

import com.obolonyk.dbclient.util.Constants;

public class Query {
    private String query;
    private boolean isUpdate;

    public Query(String query) {
        this.query = query;
        this.isUpdate = isUpdate(query);
    }

    public String getQuery() {
        return query;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    private boolean isUpdate(String query) {
        return query.startsWith(Constants.INSERT) ||
                query.startsWith(Constants.UPDATE) ||
                query.startsWith(Constants.CREATE) ||
                query.startsWith(Constants.DROP) ||
                query.startsWith(Constants.DELETE);
    }
}
