package week2.gate2.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import week2.gate2.config.DatabaseConfig;
import week2.gate2.model.OrderRow;
import week2.gate2.repository.Repository;
import week2.gate2.support.DbSupport;
import week2.gate2.support.TestEnvironment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import week2.gate2.config.AuthConfig;
import static week2.gate2.factory.SpecFactory.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class NegativeAssertionTest {
    private static final List<Long> createdOrderIds = new ArrayList<>();
    public static String opsToken;
    public static String viewerToken;
    public static String invalidUser;

    private static final String BASE_URL = System.getProperty("baseUrl",
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4000"));
    DatabaseConfig config = DatabaseConfig.fromEnvironment();

    DbSupport dbSupport = new DbSupport(config);
    Repository database = new Repository(dbSupport);


    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/api";
        opsToken = getToken(AuthConfig.OPS_CLIENT_ID, AuthConfig.OPS_CLIENT_SECRET);
        viewerToken = getToken(AuthConfig.VIEWER_CLIENT_ID, AuthConfig.NEW_VIEWER_CLIENT_SECRET);

    }

    @AfterEach
    void cleanCreatedRows() throws Exception {
        for (long orderId : createdOrderIds) {
            database.deleteOrder(orderId);
        }
        createdOrderIds.clear();
    }

    @Test
    @DisplayName("Unauthenticated secure order creation")
    void unauthenticated_createOrder(){
        given()
                .spec(authedOrders(invalidUser))
                .body(Map.of(
                        "items", List.of(101,107),
                        "currency", "INR"
                ))
                .when()
                .post("")
                .then()
                .spec(unauthenticatedJson);
    }

    @Test
    @DisplayName("Unauthenticated secure order creation")
    void unauthorized_createOrder(){
        given()
                .spec(authedOrders(viewerToken))
                .body(Map.of(
                        "items", List.of(101,107),
                        "currency", "INR"
                ))
                .when()
                .post("")
                .then()
                .spec(unauthorizedJson);
    }

    @Test
    @DisplayName("409 Status Reproduced")
    void ship_withoutAllocation() throws Exception{
        Response c =  given()
                .spec(authedOrders(opsToken))
                .body(Map.of(
                        "items", List.of(101,107),
                        "currency", "INR"
                ))
                .when()
                .post("")
                .then()
                .body(matchesJsonSchemaInClasspath("schemas/json/secure-order.schema.json"))
                .statusCode(201)
                .extract().response();

        long id = c.jsonPath().getLong("orderId");
        createdOrderIds.add(id);

        OrderRow row = database.findOrder(id);
        assertNotNull(row,"order must be persisted");
        assertEquals("CREATED", row.status());
        assertEquals(0,row.total().compareTo(
                c.jsonPath().getObject("total", BigDecimal.class)
        ));



        given()
                .spec(authedOrders(opsToken))
                .contentType(ContentType.JSON)
                .when()
                .post("{id}/ship",id)
                .then()
                .statusCode(409);
    }

    @Test
    @DisplayName("Allocate non existing order")
    void allocate_nonOrder(){
        given()
                .spec(authedOrders(opsToken))
                .contentType(ContentType.JSON)
                .when()
                .post("{id}/allocate",9999)
                .then()
                .spec(notFoundJson);
    }

    @Test
    @DisplayName("Ship non existing order")
    void ship_nonOrder(){
        given()
                .spec(authedOrders(opsToken))
                .contentType(ContentType.JSON)
                .when()
                .post("{id}/ship",9999)
                .then()
                .spec(notFoundJson);
    }

}
