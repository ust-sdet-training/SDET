package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.RequestSpec;
import specs.ResponseSpec;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class CreatePostsTest {
    @Test
    @DisplayName("Create Posts with given data")
    public void CreatePosts(){
        String payload = """
                {
                  "title": "Automation Testing",
                  "body": "Rest Assured Assignment",
                  "userId": 1
                }
                
                """;
        given()
                .spec(RequestSpec.getRequestSpecification())
                .body(payload)
                .when()
                .post("posts")
                .then()
                .spec(ResponseSpec.created201())
                .body("title", equalTo("Automation Testing"))
                .body("body", equalTo("Rest Assured Assignment"))
                .body("userId", equalTo(1))
                .body(matchesJsonSchemaInClasspath("schemas/posts.schema.json"));
    }

}
