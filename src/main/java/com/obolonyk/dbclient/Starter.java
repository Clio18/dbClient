package com.obolonyk.dbclient;

import com.obolonyk.dbclient.executor.Executor;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;
import com.obolonyk.dbclient.report.ReportHandler;
import com.obolonyk.dbclient.util.Constants;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Starter {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        Scanner scanner = new Scanner(System.in);
        try (InputStream is = loader.getResourceAsStream("application.properties");) {
            properties.load(is);
        }
        Executor executor = new Executor(properties);
        while (true) {
            String request = scanner.nextLine();
            Query query = new Query(request);
            GeneralData generalData = executor.getData(query);
            ReportHandler.handle(generalData, properties.getProperty(Constants.PATH));
        }
    }
}
