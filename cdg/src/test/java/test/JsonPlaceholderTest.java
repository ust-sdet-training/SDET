package test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import spec.SpecFactory;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static spec.SpecFactory.*;

public class JsonPlaceholderTest {


    @Test
    public void testGetPost() {
        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .when()
                .get("/posts/1");
                response
                        .then()
                                .statusCode(200)
                                        .body("id",equalTo(1))
                                                .body("body",equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"
                                                ));

        assertEquals(200, response.getStatusCode());
        assertEquals(1, response.jsonPath().getInt("id"));
        assertTrue(response.jsonPath().getString("title").length() > 0);
    }


    @Test
    public void testGetAllPosts() {
        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .when()
                .get("/posts");
response.then()
                .contentType(ContentType.JSON)
                        .body("id",notNullValue())
        .body("title",notNullValue())
                .body("[0].userId", greaterThan(0));


        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("").size() > 0);
    }


    @Test
    public void testCreatePost() {
        String requestBody =reqbody;

        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .body(requestBody)
                .when()
                .post("/posts");
        response
                .then()
                .statusCode(201)
                        .body("id",greaterThan(0));




        assertEquals(201, response.getStatusCode());
        assertTrue(response.jsonPath().getInt("id") > 0);
        assertEquals("Test Post", response.jsonPath().getString("title"));
    }


    @Test
    public void testUpdatePost() {
        String requestBody = resbody;

        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .body(requestBody)
                .when()
                .put("/posts/1");

        assertEquals(200, response.getStatusCode());
        assertEquals("Updated Title", response.jsonPath().getString("title"));
        assertEquals("This is an updated post", response.jsonPath().getString("body"));
    }


    @Test
    public void testDeletePost() {
        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .when()
                .delete("/posts/1");

        assertEquals(200, response.getStatusCode());
    }

    @Test
    public void testGetPostsByUserId() {
        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .queryParam("userId", 1)
                .when()
                .get("/posts");

        assertEquals(200, response.getStatusCode());
        assertTrue(response.jsonPath().getList("").size() > 0);
        assertEquals(1, response.jsonPath().getInt("[0].userId"));
    }


    @Test
    public void testPatchPost() {
        String requestBody = "{\n" +
                "  \"title\": \"Patched Title\"\n" +
                "}";

        Response response = given()
                .spec(SpecFactory.jsonPlaceholderSpec())
                .body(requestBody)
                .when()
                .patch("/posts/1");
        response
                .then()
                        .statusCode(200)
                                .body("title",equalTo("Patched Title"));


        assertEquals(200, response.getStatusCode());
        assertEquals("Patched Title", response.jsonPath().getString("title"));
    }

}

