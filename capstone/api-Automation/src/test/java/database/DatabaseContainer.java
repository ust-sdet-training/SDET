package database;


import org.testcontainers.containers.MySQLContainer;


public class DatabaseContainer {


    public static MySQLContainer<?> mysql;


    static {


        mysql =
                new MySQLContainer<>("mysql:8.0")
                        .withDatabaseName("tripstack_test")
                        .withUsername("root")
                        .withPassword("root");


        mysql.start();


        DatabaseSetup.createTables();


    }


}