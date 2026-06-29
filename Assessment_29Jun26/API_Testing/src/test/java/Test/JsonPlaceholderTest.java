package Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class JsonPlaceholderTest {
    @Test
    public void validatePostsForSpecificUser() {

        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";

        Response response =
                given()
                        .queryParam("userId", 1)
                        .when()
                        .get("/posts")
                        .then()
                        .extract()
                        .response();

        assertEquals(200, response.statusCode());

        List<Object> posts = response.jsonPath().getList("$");

        assertFalse(posts.isEmpty(), "Response should not be empty");

        JsonPath jsonPath = response.jsonPath();
        List<Integer> userIds = jsonPath.getList("userId");

        for (Integer id : userIds) {
            assertEquals(1, id, "Expected userId to be 1 but found " + id);
        }

    }
}