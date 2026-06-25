package tests;

import base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import specs.ResponseSpecs;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class PostTests extends BaseTest {

    @Test
    @DisplayName("Create Posts")
    public void createPosts() {

        String payload = """
                   {"userId": 100,
                        "id": 101,
                        "title": "assessment",
                        "body": "quia et suscipit\\nsuscipit recusandae consequuntur expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum rerum est autem sunt rem eveniet architecto"
                    }
                
                """;

        given()
                .spec(requestSpec)
                .body(payload)

                .when()
                .post("/posts")

                .then()
                .spec(ResponseSpecs.created201())
                .body("id", equalTo(101))
                .body("userId", equalTo(100))
                .body("title", equalTo("assessment"))
                .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"))
                .body(matchesJsonSchemaInClasspath("schemas/getbypostid.schema.json"));
    }
}