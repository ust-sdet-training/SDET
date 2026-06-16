package week2.gate2.config;

import week2.gate2.config.DatabaseConfig;
import week2.gate2.support.DbSupport;
import org.junit.jupiter.api.Test;

class DbTest {

    @Test
    void shouldConnectToMysql() throws Exception {

        DatabaseConfig config = new DatabaseConfig(
                AuthConfig.DB_JDBC_URL,
                AuthConfig.DB_USER,
                AuthConfig.DB_PASSWORD
        );

        DbSupport dbSupport = new DbSupport(config);

        System.out.println(dbSupport.isReachable());
    }
}