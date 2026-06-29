package tests;

import base.BaseTest;
import io.restassured.response.Response;
import models.PostRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class CreatePostTest extends BaseTest {

    @Test
    @DisplayName("Create a new post and verify response")
    void createPostTest() {
        PostRequest request = new PostRequest(
                "Automation Testing",
                "Rest Assured Assignment",
                1
        );
        Response response = given()
                .spec(SpecFactory.requestSpec())
                .body(request)
                .when()
                .post("/posts");
        assertEquals(201, response.getStatusCode());
        assertEquals("Automation Testing", response.jsonPath().getString("title"));
        assertEquals("Rest Assured Assignment", response.jsonPath().getString("body"));
        assertEquals(1, response.jsonPath().getInt("userId"));
        assertNotNull(response.jsonPath().get("id"));
    }
}