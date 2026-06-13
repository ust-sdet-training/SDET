package com.ust.sdet.api.config;


import com.ust.sdet.api.support.DbSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class DatabaseConfigTest {


    @Test
    @DisplayName("To check connection is created or not")
    void shouldConnectToMysql() throws Exception {
        DatabaseConfig config =  DatabaseConfig.fromEnvironmentCredential();
        DbSupport dbSupport = new DbSupport(config);
        System.out.println(dbSupport.isReachable());
        System.out.println(config.username().compareTo(System.getenv("DB_USER")));


    }
}
