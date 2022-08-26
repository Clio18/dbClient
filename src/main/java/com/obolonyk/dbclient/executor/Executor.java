package com.obolonyk.dbclient.executor;

import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;
import com.obolonyk.dbclient.util.QueryAnalyzer;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class Executor {
    private DataSource dataSource;

    public Executor(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public GeneralData getData(Query query, QueryAnalyzer queryAnalyzer) throws SQLException {
        Connection connection = dataSource.getConnection();
        GeneralData generalData = new GeneralData();
        if (query.isUpdate()) {
            String queryForPreparedStatement = QueryAnalyzer.creationQueryForPreparedStatement(query.getQuery());
            try (PreparedStatement preparedStatement = connection.prepareStatement(queryForPreparedStatement)) {

                queryAnalyzer.analyze(query.getQuery());
                if (query.getQuery().contains("INSERT")) {
                    PreparedStatement statement = queryAnalyzer.constructPreparedStatement(preparedStatement);
                    int i = statement.executeUpdate();
                    generalData.setUpdatedRows(i);
                } else {
                    preparedStatement.execute();
                    //TODO
                    generalData.setUpdatedRows(1);
                }
            }
        } else {
            try (Statement statement = connection.createStatement()) {
                generalData.setSelect(true);
                ResultSet resultSet = statement.executeQuery(query.getQuery());
                ResultSetMetaData rsmd = resultSet.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                for (int i = 1; i <= columnsNumber; i++) {
                    String columnName = rsmd.getColumnName(i);
                    generalData.getHeaders().add(columnName);
                    generalData.getValues().put(columnName, new ArrayList<>());
                }

                while (resultSet.next()) {
                    for (String header : generalData.getHeaders()) {
                        String columnValue = resultSet.getString(header);
                        generalData.getData().add(columnValue);
                        generalData.getValues().get(header).add(columnValue);
                    }
                }
            }
        }
        return generalData;
    }
}
