package com.ust.sdet.config;

import com.ust.sdet.dbframework.support.TestEnvironment;

public final class TestConfig {

    public static final String BASE_URL = TestEnvironment.required("BASE_URL");
    public static final String EMAIL = TestEnvironment.required("EMAIL");
    public static final String PASSWORD = TestEnvironment.required("PASSWORD");
    public static final String CLIENT_ID = TestEnvironment.required("CLIENT_ID");
    public static final String CLIENT_SECRET = TestEnvironment.required("OAUTH_CLIENT_SECRET");
    public static final String VIEWER_CLIENT_ID = TestEnvironment.optional("VIEWER_CLIENT_ID", "retail-viewer-client");
    public static final String VIEWER_CLIENT_SECRET = TestEnvironment.optional("OAUTH_VIEWER_CLIENT_SECRET", "");
    public static final String EXPIRED_CLIENT_ID = TestEnvironment.optional("EXPIRED_CLIENT_ID", "retail-expired-client");
    public static final String EXPIRED_CLIENT_SECRET = TestEnvironment.optional("OAUTH_EXPIRED_CLIENT_SECRET", "");
    public static final String RETAIL_API_KEY = TestEnvironment.optional("RETAIL_API_KEY", "");
    public static final String DATABASE_URL = TestEnvironment.optional("DATABASE_URL", "");
    public static final String DB_JDBC_URL = TestEnvironment.optional("DB_JDBC_URL", "jdbc:mysql://localhost:3306/orders_db");
    public static final String DB_USER = TestEnvironment.optional("DB_USER", "root");
    public static final String DB_PASSWORD = TestEnvironment.optional("DB_PASSWORD", "");
    public static final String GRANT_TYPE = TestEnvironment.optional("GRANT_TYPE", "client_credentials");

    private TestConfig() {}

}