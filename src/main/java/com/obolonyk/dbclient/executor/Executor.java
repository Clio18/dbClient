package com.obolonyk.dbclient.executor;


import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;
import com.obolonyk.dbclient.util.Constants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

public class Executor {
    private Properties properties;
    private Connection connection;

    public Executor(Properties properties) throws SQLException, ClassNotFoundException {
        this.properties = properties;
        this.connection = config();
    }

    public GeneralData getData(Query query) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            GeneralData generalData = new GeneralData();
            if (query.isUpdate()) {
                int i = statement.executeUpdate(query.getQuery());
                generalData.setUpdatedRows(i);
            } else {
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
                        generalData.getValues().get(header).add(columnValue);
                    }
                }
            }
            return generalData;
        }
    }

    Connection config() throws ClassNotFoundException, SQLException {
        Class.forName(properties.getProperty(Constants.DRIVER));
        return DriverManager.getConnection(properties.getProperty(Constants.DB_URL),
                properties.getProperty(Constants.USER_NAME),
                properties.getProperty(Constants.PASSWORD));
    }
}
