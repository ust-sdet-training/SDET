package com.ust.sdet.api.tests;

import com.ust.sdet.api.dbframework.config.DatabaseConfig;
import com.ust.sdet.api.dbframework.support.DbSupport;
import org.junit.jupiter.api.Test;
class DbTest {
    @Test
    void shouldConnectToMysql() throws Exception {
        DatabaseConfig config = new DatabaseConfig(
                "jdbc:mysql://localhost:3306/orders_db",
                "root",
                "Dassbhai@2003"
        );
        DbSupport dbSupport = new DbSupport(config);
        System.out.println(dbSupport.isReachable());
    }
}
