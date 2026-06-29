package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import spec.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonplaceholderTest {

    @Test
    void getOne() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/posts/1");

        response.then()
                .spec(SpecFactory.success200());

        assertEquals(200, response.getStatusCode(), "Status code should be 200");

        int id = response.jsonPath().getInt("id");
        Object userId = response.jsonPath().get("userId");
        String title = response.jsonPath().getString("title");
        String body = response.jsonPath().getString("body");

        assertEquals(1, id, "id should be 1");
        assertNotNull(userId, "userId should be present");
        assertFalse(title == null || title.isBlank(), "title should not be empty");
        assertFalse(body == null || body.isBlank(), "body should not be empty");
    }
}

