package com.ust.sdet.api.tests;

import com.ust.sdet.api.config.Config;
import com.ust.sdet.api.specs.SpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static com.ust.sdet.api.config.Config.expiredToken;
import static io.restassured.RestAssured.given;
import static io.restassured.matcher.RestAssuredMatchers.matchesXsdInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthTests {

    private static RequestSpecification authed;

    private static String Oauth_token(String clientId, String clientSecret) {

        return given()

                .baseUri(Config.BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .auth()
                .preemptive()
                .basic(clientId, clientSecret)
                .formParam("grant_type", "client_credentials")
                .when()
                .post("/api/oauth/token")
                .then()
                .log().all()
                .statusCode(200)
                .body("token_type", equalToIgnoringCase("Bearer"))
                .body("expires_in", greaterThan(0))
                .body("access_token", not(emptyOrNullString()))

                .extract()
                .path("access_token");
    }

    @BeforeAll
    static void setup() {
        String token = Oauth_token(Config.CLIENT_ID, Config.CLIENT_SECRET);
        authed = com.ust.sdet.api.specs.SpecFactory.authed(token);

    }

    @Test
    @DisplayName("Create order using authenticated spec")
    void createOrder() {

        Response response =
                given()
                        .spec(authed)
                        .body(Map.of(
                                "items", List.of(101, 107),
                                "currency", "INR"
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .spec(SpecFactory.created201())
                        .body("id", notNullValue())
                        .body("items.size()", equalTo(2))
                        .body("status", equalTo("CREATED"))
                        .body("orderNumber", notNullValue())
                        .body("total", greaterThan(0))
                        .body(matchesJsonSchemaInClasspath(
                                "Schema/order.schema.json"))
                        .extract()
                        .response();



        System.out.println(response.asPrettyString());
    }

    @Test
    @DisplayName("Get order using Auth")
    void getOrder() {

        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", 6001)
                .then()
                .spec(SpecFactory.ok200())
                .body("id", equalTo(6001))
                .body("status", equalTo("CREATED"))
                .body("orderNumber", notNullValue())
                .body("total", greaterThan(0))
                .body("items", notNullValue());

    }

    @Test
    @DisplayName("404 Not found")
    void pagenotfound() {

        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", 9999)
                .then()
                .statusCode(404)
                .time(lessThan(5000L));
    }

    @Test
    @DisplayName("Delete created order returns 204")
    //create
    void deleteOrderReturns204() {
        Response createResponse =
                given()
                        .spec(authed)
                        .body(Map.of(
                                "items", List.of(101, 107),
                                "currency", "INR"
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();

        //id
        long orderId = createResponse.jsonPath().getLong("id");
        given()
                .spec(authed)
                .when()
                .delete("/api/secure/orders/" + orderId)
                .then()
                .statusCode(204);

        // Verify
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/" + orderId)
                .then()
                .statusCode(404);
    }


    @Test
    @DisplayName("Exceed max cart quantity returns 409")
    void conflict409() {

        //clean state
        given()
                .spec(authed)
                .when()
                .delete("/api/cart")
                .then()
                .statusCode(204);

        // Add 4 items
        given()
                .spec(authed)
                .body(Map.of(
                        "productId", 104,
                        "quantity", 4
                ))
                .when()
                .post("/api/cart/items")
                .then()
                .statusCode(201)
                .body("quantity", equalTo(4))
                .body("product.id", equalTo(104));

        // Add 2 more to get 6
        given()
                .spec(authed)
                .body(Map.of(
                        "productId", 104,
                        "quantity", 2
                ))
                .when()
                .post("/api/cart/items")
                .then()
                .statusCode(409)
                .body("message",
                        equalTo("Maximum quantity per cart line is 5"));

        // Cleanup
        given()
                .spec(authed)
                .when()
                .delete("/api/cart")
                .then()
                .statusCode(204);
    }
    @Test
    @DisplayName("With no token returns 401")
    void noToken() {

        given()
                .spec(SpecFactory.unauthSpec())
                .when()
                .get("/api/secure/orders/{id}", 6002)
                .then()
                .spec(SpecFactory.unauthorized401())
                .time(lessThan(5000L))
                .log().all();

    }
    @Test
    @DisplayName("Invalid token returns 401")
    void invalidToken() {

        given()
                .spec(SpecFactory.authed("garbage"))
                .when()
                .get("/api/secure/orders/{id}", 6024)
                .then()
                .spec(SpecFactory.unauthorized401())
                .time(lessThan(5000L));
    }

    @Test
    @DisplayName("Viewer role returns 403")
    void forbiddenRole() {

        String viewerToken = Oauth_token(Config.RETAIL_CLIENT_ID, Config.RETAIL_CLIENT_SECRET);
        RequestSpecification viewerSpec = SpecFactory.authed(viewerToken);

        given()
                .spec(viewerSpec)
                .body(Map.of(
                        "items", List.of(101, 107)
                ))
                .when()
                .post("/api/secure/orders")
                .then()
                .spec(SpecFactory.forbidden403());
    }
    @Test
    void fetchWithApiKey() {
        given()
                .spec(SpecFactory.oauthSpec())
                .header("X-API-Key", Config.retail_demo_key)
                .when().get("/api/partner/orders/{id}", 5001)
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Order lifecycle - Create to Fetch to Delete")
    void orderLifecycle() {

        // Step 1: Create Order
        Response createResponse =
                given()
                        .spec(authed)
                        .body(Map.of(
                                "items", List.of(101, 107)
                        ))
                        .when()
                        .post("/api/secure/orders")
                        .then()
                        .statusCode(201)
                        .body("id", notNullValue())
                        .body(matchesJsonSchemaInClasspath(
                                "Schema/order.schema.json"))
                        .body("status", equalTo("CREATED"))
                        .extract()
                        .response();

        int orderId = createResponse.path("id");

        System.out.println("Created Order Id = " + orderId);

        // Step 2: Fetch Same Order
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .statusCode(200)
                .body("id", equalTo(orderId))
                .body(matchesJsonSchemaInClasspath(
                        "Schema/order.schema.json"))
                .body("status", equalTo("CREATED"));

        // Step 3: Delete Same Order
        given()
                .spec(authed)
                .when()
                .delete("/api/secure/orders/{id}", orderId)
                .then()
                .statusCode(204);

        // Step 4: Verify Deleted
        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", orderId)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Order fetch response time")
    void responseTimeValidation() {

        given()
                .spec(authed)
                .when()
                .get("/api/secure/orders/{id}", 6001)
                .then()
                .statusCode(200)
                .time(lessThan(5000L));
    }

    @Test
    @DisplayName("OAuth token generation returns valid token")
    void oauthTokenGeneration() {

        given()
                .baseUri(Config.BASE_URL)
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .auth()
                .preemptive()
                .basic(Config.CLIENT_ID, Config.CLIENT_SECRET)
                .formParam("grant_type", "client_credentials")
                .when()
                .post("/api/oauth/token")
                .then()
                .log().all()
                .statusCode(200)
                .body("token_type", equalToIgnoringCase("Bearer"))
                .body("expires_in", greaterThan(0))
                .body("access_token", not(emptyOrNullString()));
    }

    @Test
    @DisplayName("Expired token generated successfully")
    void expiredTokenGenerated() {

        given()
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
                .log().all()
                .statusCode(200)

                // Token exists
                .body("access_token", not(emptyOrNullString()))

                // Bearer token type
                .body("token_type", equalToIgnoringCase("Bearer"))

                // Expired token
                .body("expires_in", equalTo(0))

                // Correct scope
                .body("scope", containsString("orders:read"))
                .body("scope", containsString("orders:write"))

                // Response validations
                .contentType("application/json")
                .time(lessThan(5000L));
    }

    @Test
    @DisplayName("Validate XML product details")
    void xml_product_details() {

        Response response =
                given()
                        .spec(SpecFactory.req())
                        .when()
                        .get("/api/legacy/products/101.xml")
                        .then()
                        .statusCode(200)
                        .body(matchesXsdInClasspath("xsd/product.xsd"))
                        .body("product.id", equalTo("101"))
                        .body("product.name", equalTo("Running Shoes"))
                        .body("product.category", equalTo("Footwear"))
                        .body("product.price", equalTo("4499"))
                        .extract()
                        .response();
    }
}