package com.obolonyk.dbclient.entity;

public class Query {
    private String query;
    private boolean isUpdate;

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";
    private static final String CREATE = "CREATE";
    private static final String DROP = "DROP";

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
        return query.startsWith(INSERT) ||
                query.startsWith(UPDATE) ||
                query.startsWith(CREATE) ||
                query.startsWith(DROP) ||
                query.startsWith(DELETE);
    }


}
