package ApiTest.test;

import ApiTest.config.config;
import ApiTest.test.specFactory.reqSpec;
import ApiTest.test.specFactory.respSpec;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonPlaceholderPostTest {

    @Test
    public void createNewPostAndVerifyResponse() {
        RestAssured.baseURI = config.Base_URI;

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("title", "Automation Testing");
        requestBody.put("body", "Rest Assured Assignment");
        requestBody.put("userId", 1);

        Response response = given()
                .spec(reqSpec.getJsonPlaceholderPostRequestSpecification())
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .spec(respSpec.createdSuccess())
                .extract()
                .response();

        assertEquals("Automation Testing", response.jsonPath().getString("title"));
        assertEquals("Rest Assured Assignment", response.jsonPath().getString("body"));
        assertEquals(1, response.jsonPath().getInt("userId"));
        assertNotNull(response.jsonPath().getInt("id"));
    }
}
