import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

import java.util.Map;

public class JSONPlaceholderTest {
    @Test
    @DisplayName("Get body from JSONPlaceholder site with id 1 using GET")
    void getResponse() {
        given()
            .pathParam("id", 1)
        .when()
            .get("https://jsonplaceholder.typicode.com/posts/{id}")
        .then()
            .statusCode(200)
            .body("userId", equalTo(1))
            .body("id", equalTo(1))
            .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
            .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
    }

    @Test
    @DisplayName("Create a resource using POST")
    void postResponse() {
        given()
            .body(Map.of("title", "foo",
                  "body", "bar",
                  "userId", 1
            ))
        .when()
            .post("https://jsonplaceholder.typicode.com/posts/")
        .then()
            .statusCode(201)
            .body("id", equalTo(101));
    }

    @Test
    @DisplayName("Update resource using PUT")
    void putResponse() {
        given()
            .body(Map.of("id", 1,
                    "title", "foo",
                    "body", "bar",
                    "userId", 1
                ))
            .pathParam("id", 1)
        .when()
            .put("https://jsonplaceholder.typicode.com/posts/{id}")
        .then()
            .statusCode(200)
            .body("id", equalTo(1));
    }

    @Test
    @DisplayName("Delete a resource using DELETE")
    void deleteResponse() {
        given()
            .pathParam("id", 1)
        .when()
            .delete("https://jsonplaceholder.typicode.com/posts/{id}")
        .then()
            .statusCode(200);
    }
}
