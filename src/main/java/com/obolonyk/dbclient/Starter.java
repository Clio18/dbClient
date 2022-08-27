package com.obolonyk.dbclient;

import com.obolonyk.dbclient.executor.Executor;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;
import com.obolonyk.dbclient.loader.Loader;
import com.obolonyk.dbclient.report.ReportHandler;
import com.obolonyk.dbclient.util.ConsoleOutput;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Starter {
    public static void main(String[] args) throws SQLException, IOException {
        DataSource dataSource = Loader.getDataSource();
        Scanner scanner = new Scanner(System.in);
        Executor executor = new Executor(dataSource);
        ConsoleOutput.introMessage();
        while (true) {
            ConsoleOutput.executionMessage();
            String request = scanner.nextLine();
            Query query = new Query(request);
            GeneralData generalData = executor.getData(query);
            ReportHandler.handle(generalData);
        }
    }


}
