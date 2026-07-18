package org.sdet.Database.database;

import org.sdet.API_Framework.config.Secrets;

public final class DBConfig {

    private DBConfig() {
    }

    public static final String URL =
            Secrets.DB_URL;

    public static final String USERNAME =
            Secrets.DB_USERNAME;

    public static final String PASSWORD =
            Secrets.DB_PASSWORD;
}