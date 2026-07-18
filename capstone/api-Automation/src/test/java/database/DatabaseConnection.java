package database;


import java.sql.Connection;
import java.sql.DriverManager;


public class DatabaseConnection {


    public static Connection getConnection(){

        try {

            return DriverManager.getConnection(

                    DatabaseContainer.mysql.getJdbcUrl(),

                    DatabaseContainer.mysql.getUsername(),

                    DatabaseContainer.mysql.getPassword()

            );


        }catch(Exception e){

            throw new RuntimeException(
                    "Database connection failed",
                    e
            );

        }

    }

}