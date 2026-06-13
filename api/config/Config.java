package com.ust.sdet.api.config;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Config {

    public static final String BASE_URL =
            env("BASE_URL", "http://localhost:4000");

    public static final String EMAIL =
            env("EMAIL", "customer@example.com"
            );

    public static final String PASSWORD =
            env(
                    "PASSWORD",
                    "Password@123"
            );

    public static final String CLIENT_ID =
            env(
                    "CLIENT_ID",
                    "retail-ops-client"
            );

    public static final String CLIENT_SECRET =
            env(
                    "CLIENT_SECRET",
                    "2a2729b27b47fe27b6412403d886ef4781bbff36b0e2b58e"
            );
    public static final String RETAIL_CLIENT_ID =
            env(
                    "RETAIL_CLIENT_ID",
                    "retail-viewer-client"
            );

    public static final String RETAIL_CLIENT_SECRET =
            env(
                    "RETAIL_CLIENT_SECRET",
                    "241354ac6e2b75796df4eea67c3681ee520fed7d1d78c7fb"
            );

    public static final String retail_demo_key =
            env(
                    "retail-demo-key",
                    "4bd53b7820525db94f8f34d9316bb35ee1e2ab9fe80e4c3f"
            );
    public static final String EXPIRED_CLIENT_ID =
            env(
                    "OAUTH_EXPIRED_CLIENT_ID",
                    "retail-expired-client"
            );

    public static final String EXPIRED_CLIENT_SECRET =
            env(
                    "OAUTH_EXPIRED_CLIENT_SECRET",
                    "851d19d4df703b868ae8fc71d0143410670272630553ae21"
            );

    private static String env(
            String key,
            String fallback
    ){

        String value =
                System.getenv(
                        key
                );

        return value == null
                ? fallback
                : value;

    }
    public static String expiredToken() {

        return given()
                .baseUri(Config.BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .auth()
                .preemptive()
                .basic(
                        Config.EXPIRED_CLIENT_ID,
                        Config.EXPIRED_CLIENT_SECRET
                )
                .formParam("grant_type", "client_credentials")
                .when()
                .post("/api/oauth/token")
                .then()
                .statusCode(200)
                .body("expires_in", equalTo(0))
                .extract()
                .path("access_token");
    }

}