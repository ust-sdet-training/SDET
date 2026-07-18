package clients;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class BaseApiClient {


    protected Response get(String endpoint) {

        return given()
                .spec(spec.RequestSpec.request())
                .when()
                .get(endpoint);
    }

    protected Response getWithAuth(String endpoint, String token) {

        return given()
                .spec(spec.RequestSpec.request())
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint);
    }

    protected Response post(String endpoint, Object body) {

        return given()
                .spec(spec.RequestSpec.request())
                .body(body)
                .when()
                .post(endpoint);
    }

    protected Response postWithAuth(
            String endpoint,
            Object body,
            String token
    ) {

        return given()
                .spec(spec.RequestSpec.request())
                .header("Authorization", "Bearer " + token)
                .body(body)
                .when()
                .post(endpoint);
    }
}