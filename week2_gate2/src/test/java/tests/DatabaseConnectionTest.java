package tests;

import config.DatabaseConfig;
import org.junit.jupiter.api.Test;
import support.DbSupport;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseConnectionTest {

    @Test
    void databaseShouldBeReachable() throws Exception {

        DbSupport dbSupport =
                new DbSupport(
                        DatabaseConfig.fromEnvironment()
                );

        assertTrue(
                dbSupport.isReachable()
        );
    }
}