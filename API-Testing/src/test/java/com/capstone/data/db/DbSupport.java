package com.capstone.data.db;

import java.sql.Connection;
import java.sql.SQLException;

public class DbSupport {
    protected Connection connection() throws SQLException {
        return DbConfig.connection();
    }
}
