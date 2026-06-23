
package assessment.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static assessment.factory.SpecFactory.*;
import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssessmentTest {
    private static final String BASE_URL = System.getProperty("baseUrl",
            System.getenv().getOrDefault("BASE_URL", "https://petstore.swagger.io"));
    @BeforeAll
    static void setup(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "/v2";
    }

    @Test
    @DisplayName("Place an order for a pet")
    void placeOrder(){
        var order = Map.of("id",12,"petId",1,"quantity",1,"shipDate","2026-06-23T12:05:02.379+0000","status","placed","complete",true);
        given()
                .spec(reqSpec)
                .body(order)
                .when()
                .post("/store/order")
                .then()
                .log().all()
                .spec(okJson)
                .body("id",equalTo(12))
                .body("quantity",equalTo(1));
    }

    @Test
    @DisplayName("Get an order")
    void getOrder(){
        Response order = given().spec(reqSpec)
                .get("/store/order/{id}",12)
                .then()
                .spec(okJson)
                .body("id",equalTo(12))
                .body("quantity",equalTo(1))
                .extract().response();

        assertEquals("placed",order.path("status"));
        assertEquals("2026-06-23T12:05:02.379+0000",order.path("shipDate"));
    }



}

