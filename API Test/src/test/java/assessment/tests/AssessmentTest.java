package assessment.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static assessment.factory.SpecFactory.*;
import static io.restassured.RestAssured.given;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;



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
                .body("userId",equalTo(1))
                .body("id",equalTo(1))
                .body("title",equalToIgnoringCase("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body(matchesJsonSchemaInClasspath("schemas/json/post.schema.json"))
                .extract()
                .as(Post.class);

        assertEquals(1, response.id());
        assertEquals("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", response.title());

    }

    @Test
    @DisplayName("GET user")
    void getUser(){
        Response response = given()
                .spec(reqSpec)
                .when()
                .get("/users/{id}",1)
                .then()
                .log().all()
                .spec(okJson)
                .body("id",equalTo(1))
                .body("name",equalToIgnoringCase("Leanne Graham"))
                .extract().response();
        assertEquals(1, (Integer) response.path("id"));
        assertEquals("Leanne Graham",response.path("name"));

    }

    @Test
    @DisplayName("CREATE posts")
    void createPost(){
        var post = Map.of("userId",6,"title","API Testing", "body","Testing for creating post");
        given()
                .spec(reqSpec)
                .body(post)
                .when()
                .post("/posts")
                .then()
                .spec(createdJson)
                .body("userId",equalTo(6))
                .body("title",equalToIgnoringCase("API Testing"));

    }

    @Test
    @DisplayName("Update post")
    void updatePost(){
        var post = Map.of("userId",2,"title","New title", "body","New body");
        given()
                .spec(reqSpec)
                .body(post)
                .when()
                .put("/posts/{id}",1)
                .then()
                .spec(okJson)
                .body("userId",equalTo(2))
                .body("title",equalToIgnoringCase("New title"));
    }

    @Test
    @DisplayName("Delete post")
    void deletePost(){
        given()
                .spec(reqSpec)
                .when()
                .delete("posts/{id}",1)
                .then()
                .spec(okJson);
    }

}
