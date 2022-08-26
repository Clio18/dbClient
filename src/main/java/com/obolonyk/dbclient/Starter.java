package com.obolonyk.dbclient;

import com.obolonyk.dbclient.executor.Executor;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;
import com.obolonyk.dbclient.loader.Loader;
import com.obolonyk.dbclient.report.ReportHandler;
import com.obolonyk.dbclient.util.ConsoleOutput;
import com.obolonyk.dbclient.util.QueryAnalyzer;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Starter {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = Loader.getDataSource();
        Scanner scanner = new Scanner(System.in);
        QueryAnalyzer queryAnalyzer = new QueryAnalyzer();
        Executor executor = new Executor(dataSource);
        ConsoleOutput.introMessage();
        while (true) {
            ConsoleOutput.executionMessage();
            String request = scanner.nextLine();
            Query query = new Query(request.trim());
            GeneralData generalData = executor.getData(query, queryAnalyzer);
            ReportHandler.handle(generalData);
        }
    }


}
