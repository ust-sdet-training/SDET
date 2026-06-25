package tests;

import io.restassured.response.Response;
import models.Base;
import org.junit.jupiter.api.Test;
import specs.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class JsonplaceholderTest {

    @Test
    void create() {

        long id = System.currentTimeMillis() / 1000;
        Base base = new Base(1,1,"page","page contains numbers");

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .body(base)
                        .when()
                        .post("/posts");

        response.then()
                .spec(SpecFactory.success201());

        assertEquals(
                "page",
                response.jsonPath().getString("title"));
    }

    @Test
    void getAll() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/posts");

        response.then()
                .spec(SpecFactory.success200());

        assertNotNull(response.jsonPath().getString("title"));
    }

    @Test
    void getComments() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/posts/1/comments");

        response.then()
                .spec(SpecFactory.success200());

        assertNotNull(response.jsonPath().getString("title"));
    }

    @Test
    void getPostId() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/comments?postId=1");

        response.then()
                .spec(SpecFactory.success200());

        assertNotNull(response.jsonPath().getString("title"));
    }

    @Test
    void getOne() {

        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .get("/posts/1");

        response.then()
                .spec(SpecFactory.success200());

        assertNotNull(response.jsonPath().getString("title"));
    }

    @Test
    void update() {
        Base base = new Base(1, "page contains paragraphs");
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .body(base)
                        .when()
                        .put("/posts/1");
        response.then()
                .spec(SpecFactory.success200());
    }

    @Test
    void delete() {
        Response response =
                given()
                        .spec(SpecFactory.requestSpec())
                        .when()
                        .delete("/posts/1");
        response.then()
                .spec(SpecFactory.success200());
    }

}

