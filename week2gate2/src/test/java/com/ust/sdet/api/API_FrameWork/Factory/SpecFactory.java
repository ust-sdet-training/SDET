package com.ust.sdet.api.API_FrameWork.Factory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import com.ust.sdet.api.API_FrameWork.config.AuthConfig;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

public final class SpecFactory {

    private SpecFactory() {}


    public static String healthPath() {
        return "/health";
    }

    public static String productsPath() {
        return "/products";
    }

    public static String productById(int id) {
        return "/products/" + id;
    }

    public static String legacyProductXmlPath(int id) {
        return "/legacy/products/" + id + ".xml";
    }

    public static String loginPath() {
        return "/auth/login";
    }

    public static String oauthTokenPath() {
        return "/oauth/token";
    }

    public static String cartPath() {
        return "/cart";
    }

    public static String cartItemsPath() {
        return "/cart/items";
    }

    public static String ordersPath() {
        return "/orders";
    }

    public static String secureOrdersPath() {
        return "/secure/orders";
    }

    public static String secureOrderById(int id) {
        return "/secure/orders/" + id;
    }

    public static String allocateOrderPath(long id) {
        return "/secure/orders/" + id + "/allocate";
    }

    public static String shipOrderPath(long id) {
        return "/secure/orders/" + id + "/ship";
    }

    public static String secureOrderById(long id) {
        return "/secure/orders/" + id;
    }

    public static String partnerOrderById(int id) {
        return "/partner/orders/" + id;
    }

    public static final String PRODUCT_SCHEMA =
            "Schemas/json/product.schema.json";

    public static final String PRODUCT_LIST_SCHEMA =
            "Schemas/json/productList.schema.json";

    public static final String CUSTOMER_ORDER_SCHEMA =
            "Schemas/json/orders_schema/customer-orders.schema.json";

    public static final String OPS_ORDER_SCHEMA =
            "Schemas/json/orders_schema/partner-orders.schema.json";


    public static RequestSpecification readSpec() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification writeSpec() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .build();
    }


    public static RequestSpecification authRead(String token) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", bearer(token))
                .build();
    }

    public static RequestSpecification authWrite(String token) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", bearer(token))
                .build();
    }

    public static RequestSpecification opsRead() {
        return authRead(getOAuthToken());
    }

    public static RequestSpecification opsWrite() {
        return authWrite(getOAuthToken());
    }


    public static RequestSpecification viewerRead() {
        return authRead(getViewerToken());
    }

    public static RequestSpecification viewerWrite() {
        return authWrite(getViewerToken());
    }


    public static RequestSpecification expiredRead() {
        return authRead(getExpiredToken());
    }

    public static RequestSpecification expiredWrite() {
        return authWrite(getExpiredToken());
    }


    public static RequestSpecification customerRead(String token) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .addHeader("Authorization", bearer(token))
                .addHeader("X-Cart-Session", AuthConfig.CART_SESSION)
                .build();
    }

    public static RequestSpecification customerWrite(String token) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", bearer(token))
                .addHeader("X-Cart-Session", AuthConfig.CART_SESSION)
                .build();
    }


    public static RequestSpecification partnerRead(String apiKey) {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .addHeader("X-API-Key", apiKey)
                .build();
    }


    public static ResponseSpecification ok200() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification created201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification success200or201() {
        return new ResponseSpecBuilder()
                .expectStatusCode(anyOf(
                        equalTo(200),
                        equalTo(201)
                ))
                .build();
    }

    public static ResponseSpecification noContent204() {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .build();
    }

    public static ResponseSpecification badRequest400() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .build();
    }

    public static ResponseSpecification unauthorized401() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }

    public static ResponseSpecification forbidden403() {
        return new ResponseSpecBuilder()
                .expectStatusCode(403)
                .build();
    }

    public static ResponseSpecification notFound404() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static ResponseSpecification conflict409() {
        return new ResponseSpecBuilder()
                .expectStatusCode(409)
                .build();
    }



    public static String bearer(String token) {
        return "Bearer " + token;
    }

    public static String getOAuthToken() {
        return getToken(
                AuthConfig.OPS_CLIENT_ID,
                AuthConfig.OPS_CLIENT_SECRET
        );
    }

    public static String getViewerToken() {
        return getToken(
                AuthConfig.VIEWER_CLIENT_ID,
                AuthConfig.VIEWER_CLIENT_SECRET
        );
    }

    public static String getExpiredToken() {
        return getToken(
                AuthConfig.EXPIRED_CLIENT_ID,
                AuthConfig.EXPIRED_CLIENT_SECRET
        );
    }

    private static String getToken(
            String clientId,
            String clientSecret) {

        return given()
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .formParam("grant_type", "client_credentials")

                .when()
                .post(oauthTokenPath())

                .then()
                .spec(ok200())
                .extract()
                .path("access_token");
    }

    public static String getCustomerToken() {

        Map<String, String> login = Map.of(
                "email", "customer@example.com",
                "password", "Password@123"
        );

        return given()
                .spec(writeSpec())
                .body(login)

                .when()
                .post(loginPath())

                .then()
                .log().all()
                .spec(ok200())
                .extract()
                .path("token");
    }
}