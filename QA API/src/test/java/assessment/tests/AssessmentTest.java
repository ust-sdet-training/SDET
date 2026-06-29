package assessment.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static assessment.factory.SpecFactory.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;


public class AssessmentTest {

    private static final String BASE_URL = System.getProperty("baseUrl",
            System.getenv().getOrDefault("BASE_URL", "https://jsonplaceholder.typicode.com/"));
    @BeforeAll
    static void setup(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "";
    }

    @Test
    @DisplayName("GET posts")
    void getPosts(){
        Post response = given()
                .spec(reqSpec)
                .when()
                .get("/posts/{id}",1)
                .then()
                .log().all()
                .spec(okJson)
                .body("id",equalTo(1))
                .body("userId",equalTo(1))
                .body("title",equalToIgnoringCase("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
                .extract()
                .as(Post.class);

        assertNotEquals("",response.body());
        assertNotEquals("",response.title());

    }



}
