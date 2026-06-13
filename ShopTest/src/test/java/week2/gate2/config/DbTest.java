package week2.gate2.config;

import org.example.dbframework.config.DatabaseConfig;
import org.example.dbframework.support.DbSupport;
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