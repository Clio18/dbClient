package com.obolonyk.dbclient.util;

public class ConsoleOutput {

    public static void introMessage() {
        System.out.println("""
                \u001B[33m QUERY EXAMPLES:
                                
                - \u001B[33m to create db table: 
                    \u001B[32m CREATE TABLE IF NOT EXISTS USERS(ID INT PRIMARY KEY NOT NULL UNIQUE, NAME VARCHAR(255), LAST_NAME VARCHAR(255));\u001B[0m
                - \u001B[33m to insert record: 
                    \u001B[32m INSERT INTO USERS (ID, NAME, LAST_NAME) VALUES (1, 'Ramesh', 'Ahmed');\u001B[0m
                - \u001B[33m to select all: 
                    \u001B[32m SELECT * from users;\u001B[0m
                - \u001B[33m to drop db table: 
                    \u001B[32m DROP TABLE users;\u001B[0m
                \u001B[0m""");
    }

    public static void executionMessage() {
        System.out.println("\u001B[34m Please enter the query below: \u001B[0m");
    }

    public static void showUpdatedRowsMessage(int rows) {
        System.out.println("\u001B[35m The number of updated record/s is/are:\u001B[0m " + rows);
    }

    public static void showTableMessage(String table) {
        System.out.println("\u001B[35m The current state of db table:\u001B[0m \n" + table);
    }
}
