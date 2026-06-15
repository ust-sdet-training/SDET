package specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecs {

    public static RequestSpecification basicRequestSpec;

    private RequestSpecs() {
    }

    public static void createRequestSpecification() {

        basicRequestSpec =
                new RequestSpecBuilder()
                        .setContentType(ContentType.JSON)
                        .setAccept(ContentType.JSON)
                        .build();
    }


    public static RequestSpecification oauthSpec() {

        return new RequestSpecBuilder()
                .addHeader(
                        "Content-Type",
                        "application/x-www-form-urlencoded; charset=UTF-8"
                )
                .build();
    }

    public static RequestSpecification customerAuthSpec(
            String token) {

        return new RequestSpecBuilder()
                .addRequestSpecification(
                        basicRequestSpec)
                .addHeader(
                        "Authorization",
                        bearer(token))
                .build();
    }

    public static RequestSpecification opsAuthSpec(
            String token) {

        return new RequestSpecBuilder()
                .addRequestSpecification(
                        basicRequestSpec)
                .addHeader(
                        "Authorization",
                        bearer(token))
                .build();
    }

    public static RequestSpecification viewerAuthSpec(
            String token) {

        return new RequestSpecBuilder()
                .addRequestSpecification(
                        basicRequestSpec)
                .addHeader(
                        "Authorization",
                        bearer(token))
                .build();
    }

    public static RequestSpecification expiredAuthSpec(
            String token) {

        return new RequestSpecBuilder()
                .addRequestSpecification(
                        basicRequestSpec)
                .addHeader(
                        "Authorization",
                        bearer(token))
                .build();
    }

    // ---------- API PATHS ----------

    public static String loginPath() {

        return "/api/login";
    }

    public static String oauthTokenPath() {

        return "/api/oauth/token";
    }

    public static String createOrderPath() {

        return "/api/secure/orders";
    }

    public static String allocateOrder(
            long orderId) {

        return "/api/secure/orders/"
                + orderId
                + "/allocate";
    }

    public static String shipOrder(
            long orderId) {

        return "/api/secure/orders/"
                + orderId
                + "/ship";
    }

    public static String orderById(
            long orderId) {

        return "/api/secure/orders/"
                + orderId;
    }

    private static String bearer(
            String token) {

        return "Bearer " + token;
    }
    public static RequestSpecification basicSpec() {

        return basicRequestSpec;
    }



}