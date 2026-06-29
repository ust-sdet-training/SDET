package client;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class PostClient {
    private final RequestSpecification requestSpec;

    public PostClient(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response getPostsByUserId(int userId) {
        return given()
                .spec(requestSpec)
                .queryParam("userId", userId)
                .get("/posts");
    }
}
