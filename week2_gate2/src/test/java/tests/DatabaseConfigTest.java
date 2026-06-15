package tests;

import config.DatabaseConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import support.DbSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseConfigTest {
    @Test
    @DisplayName("Uses default MySQL port when URL omits port")
    void usesDefaultMySqlPortWhenUrlOmitsPort() {

        DatabaseConfig config =
                DatabaseConfig.fromDatabaseUrl(
                        "mysql://root:root123@localhost/sdet_retail"
                );

        assertEquals(
                "jdbc:mysql://localhost:3306/sdet_retail",
                config.jdbcUrl()
        );

        assertEquals(
                "root",
                config.username()
        );

        assertEquals(
                "root123",
                config.password()
        );
    }

    @Test
    @DisplayName("Preserves explicit MySQL port")
    void preservesExplicitPort() {

        DatabaseConfig config =
                DatabaseConfig.fromDatabaseUrl(
                        "mysql://admin:secret@localhost:3307/sdet_retail"
                );

        assertEquals(
                "jdbc:mysql://localhost:3307/sdet_retail",
                config.jdbcUrl()
        );

        assertEquals(
                "admin",
                config.username()
        );

        assertEquals(
                "secret",
                config.password()
        );
    }

    @Test
    @DisplayName("Preserves query parameters")
    void preservesQueryParameters() {

        DatabaseConfig config =
                DatabaseConfig.fromDatabaseUrl(
                        "mysql://root:root123@localhost:3306/sdet_retail?useSSL=false"
                );

        assertEquals(
                "jdbc:mysql://localhost:3306/sdet_retail?useSSL=false",
                config.jdbcUrl()
        );
    }

//    @Test
//    void databaseShouldBeReachable() throws Exception {
//
//        assertTrue(
//                DbSupport.isReachable()
//        );
//    }

}
