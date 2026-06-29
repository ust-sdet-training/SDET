package com.ust.api.specs;

import com.ust.api.models.Post;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpecFactory {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";

    public static Post getPostById(int id) {
        Response response = RestAssured.given()
                .baseUri(BASE_URL)
                .accept(ContentType.JSON)
                .when()
                .get("/posts/" + id)
                .then()
                .statusCode(200)
                .extract()
                .response();

        return response.as(Post.class);
    }
}
