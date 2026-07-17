package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseManager {

    private DatabaseManager() {
        // Prevent object creation
    }


    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(
                    DatabaseConfig.url(),
                    DatabaseConfig.username(),
                    DatabaseConfig.password()
            );

        }
        catch (SQLException exception) {

            throw new RuntimeException(
                    "Unable to establish database connection",
                    exception
            );

        }

    }

}