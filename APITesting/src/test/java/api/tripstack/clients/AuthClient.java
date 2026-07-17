package api.tripstack.clients;

import api.tripstack.constants.ApiRoutes;
import api.tripstack.models.LoginRequest;
import api.tripstack.utils.ResilienceUtils;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthClient {
    public Response login(LoginRequest request) {
        return ResilienceUtils.withRetry(
                () -> given().contentType("application/json").body(request).post(ApiRoutes.LOGIN),
                3,
                300,
                response -> response != null && response.getStatusCode() >= 500,
                throwable -> true
        );
    }


    public Response getCurrentUser(String token) {
        return authorised(token).get(ApiRoutes.ME);
    }

    private io.restassured.specification.RequestSpecification authorised(String token) {
        return given().header("Authorization", "Bearer " + token);
    }
}
