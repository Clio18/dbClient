package com.obolonyk.dbclient.executor;

import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class Executor {
    private DataSource dataSource;

    public Executor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public GeneralData getData(Query query) throws SQLException {
        Connection connection = dataSource.getConnection();
        try (Statement statement = connection.createStatement()) {
            GeneralData generalData = new GeneralData();
            if (query.isUpdate()) {
                int i = statement.executeUpdate(query.getQuery());
                generalData.setSelect(false);
                generalData.setUpdatedRows(i);
            } else {
                generalData.setSelect(true);
                ResultSet resultSet = statement.executeQuery(query.getQuery());

                extractedHeaders(generalData, resultSet);

                extractedValues(generalData, resultSet);
            }
            return generalData;
        }
    }

    static void extractedValues(GeneralData generalData, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            for (String header : generalData.getHeaders()) {
                String columnValueFromDB = resultSet.getString(header);
                String columnValue = columnValueFromDB.replace("'", "");
                generalData.getData().add(columnValue);
                generalData.getValues().get(header).add(columnValue);
            }
        }
    }

    static void extractedHeaders(GeneralData generalData, ResultSet resultSet) throws SQLException {
        ResultSetMetaData rsmd = resultSet.getMetaData();
        int columnsNumber = rsmd.getColumnCount();

        for (int i = 1; i <= columnsNumber; i++) {
            String columnName = rsmd.getColumnName(i);
            generalData.getHeaders().add(columnName);
            generalData.getValues().put(columnName, new ArrayList<>());
        }
    }
}
