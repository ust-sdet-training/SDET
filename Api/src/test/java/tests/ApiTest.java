package tests;

import base.BaseTest;
import io.restassured.response.Response;
import model.PostResponse;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import utils.SpecFactory;
public class ApiTest extends BaseTest {
    @Test
    void createNewPost() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Automation Testing");
        requestBody.put("body", "Rest Assured Assignment");
        requestBody.put("userId", 1);
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .body(requestBody)
                        .when()
                        .post(SpecFactory.postsPath())
                        .then()
                        .statusCode(201)
                        .extract()
                        .response();
        PostResponse postResponse = response.as(PostResponse.class);
        assertEquals("Automation Testing", postResponse.getTitle());
        assertEquals("Rest Assured Assignment", postResponse.getBody());
        assertEquals(1, postResponse.getUserId());
        assertNotNull(postResponse.getId());
        assertTrue(postResponse.getId() > 0);
    }
}