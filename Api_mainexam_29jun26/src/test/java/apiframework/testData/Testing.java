package apiframework.testData;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static apiframework.spec.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class Testing {

    @Test
    @DisplayName("Checking for Post")
    void post()
    {

        var order = Map.of("title", "Automation Testing", "body",
                "Rest Assured Assignment" , "userId" , 1);

        given()
                .spec(postReq())
                .body(order)
            .when()
                .post("/posts")
            .then()
                .log().all()
                .spec(postRes())
                .body("id", notNullValue() )
                .body("userId", equalTo(order.get("userId")))
                .body("body", equalTo(order.get("body")))
                .body("title", equalTo(order.get("title")))
                .body("body", not(emptyString()))
                .body("title", not(emptyString()))
        .body(matchesJsonSchemaInClasspath("schemas.json/order.schemas.json"));
    }

}
