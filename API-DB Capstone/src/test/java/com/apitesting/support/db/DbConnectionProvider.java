package com.apitesting.support.db;

import com.apitesting.repository.BookingRepository;
import org.flywaydb.core.Flyway;

public class DbConnectionProvider {

    private DbConnectionProvider() {
    }

    public static BookingRepository getRepository() {

        Flyway.configure()
                .dataSource(
                        DataBase.mysql.getJdbcUrl(),
                        DataBase.mysql.getUsername(),
                        DataBase.mysql.getPassword()
                )
                .locations("classpath:db/migration")
                .load()
                .migrate();

        return new BookingRepository(
                DataBase.mysql.getJdbcUrl(),
                DataBase.mysql.getUsername(),
                DataBase.mysql.getPassword()
        );
    }
}