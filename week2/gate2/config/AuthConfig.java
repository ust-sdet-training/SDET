package week2.gate2.config;


import org.junit.jupiter.api.Test;
import week2.gate2.support.TestEnvironment;

public final class AuthConfig {

    private AuthConfig() {
    }

    public static final String OPS_CLIENT_ID =
            TestEnvironment.required("OPS_CLIENT_ID");

    public static final String OPS_CLIENT_SECRET =
            TestEnvironment.required("OPS_CLIENT_SECRET");

    public static final String VIEWER_CLIENT_ID =
            TestEnvironment.optional("VIEWER_CLIENT_ID", "");

    public static final String NEW_VIEWER_CLIENT_SECRET =
            TestEnvironment.optional("NEW_VIEWER_CLIENT_SECRET", "");

    public static final String EXPIRED_CLIENT_ID =
            TestEnvironment.optional("EXPIRED_CLIENT_ID", "");

    public static final String EXPIRED_CLIENT_SECRET =
            TestEnvironment.optional("EXPIRED_CLIENT_SECRET", "");

    public static final String RETAIL_API_KEY =
            TestEnvironment.optional("RETAIL_API_KEY", "");

    public static final String CART_SESSION =
            TestEnvironment.optional(
                    "CART_SESSION",
                    "classroom-session"
            );

    public static final String DB_JDBC_URL = TestEnvironment.optional("DB_JDBC_URL", "");
    public static final String DB_USER = TestEnvironment.optional("DB_USER","");
    public static final String DB_PASSWORD = TestEnvironment.optional("DB_PASSWORD", "");
}
