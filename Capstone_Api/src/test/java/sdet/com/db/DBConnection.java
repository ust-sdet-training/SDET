package sdet.com.db;
import java.sql.*;

public class DBConnection {

    public static Connection getConnection() throws Exception {

        return DriverManager.getConnection(

                System.getenv("DB_URL"),

                System.getenv("DB_USERNAME"),

                System.getenv("DB_PASSWORD")

        );

    }

}
