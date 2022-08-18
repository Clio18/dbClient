package com.obolonyk.dbclient;

import com.obolonyk.dbclient.executor.Executor;
import com.obolonyk.dbclient.entity.GeneralData;
import com.obolonyk.dbclient.entity.Query;
import com.obolonyk.dbclient.loader.Loader;
import com.obolonyk.dbclient.report.ReportHandler;
import com.obolonyk.dbclient.util.Constants;
import com.obolonyk.dbclient.util.Helper;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class Starter {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        Properties properties = Loader.load("application.properties");
        Scanner scanner = new Scanner(System.in);
        Executor executor = new Executor(properties);
        Helper.introMessage();
        while (true) {
            Helper.executionMessage();
            String request = scanner.nextLine();
            Query query = new Query(request);
            GeneralData generalData = executor.getData(query);
            ReportHandler.handle(generalData, Constants.PATH);
        }
    }


}
