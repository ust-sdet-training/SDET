package org.sdet.API_Framework.base;
import java.util.*;
import io.restassured.response.Response;
import org.sdet.API_Framework.specs.RequestSpec;

import static io.restassured.RestAssured.given;

public abstract class BaseAPI {

    protected Response get(String endpoint) {

        return given()
                .spec(RequestSpec.withToken())
                .when()
                .get(endpoint);

    }


    protected Response post(String endpoint,
                            Object body) {

        return given()
                .spec(RequestSpec.withToken())
                .body(body)
                .when()
                .post(endpoint);

    }

    protected Response postWithoutToken(String endpoint,
                                        Object body) {

        return given()
                .spec(RequestSpec.withoutToken())
                .body(body)
                .when()
                .post(endpoint);

    }

    protected Response post(String endpoint) {

        return given()
                .spec(RequestSpec.withToken())
                .when()
                .post(endpoint);

    }

    protected Response post(String endpoint,
                            String pathName,
                            Object pathValue,
                            Object body) {

        var request = given()
                .spec(RequestSpec.withToken())
                .pathParam(pathName, pathValue);

        if (body != null) {
            request.body(body);
        }

        return request
                .when()
                .post(endpoint);

    }

    protected Response delete(String endpoint,
                              String pathName,
                              Object pathValue) {

        return given()
                .spec(RequestSpec.withToken())
                .pathParam(pathName, pathValue)
                .when()
                .delete(endpoint);

    }

    protected Response get(String endpoint,
                           String pathName,
                           Object pathValue) {

        return given()
                .spec(RequestSpec.withToken())
                .pathParam(pathName, pathValue)
                .when()
                .get(endpoint);

    }

    protected Response get(String endpoint,
                           Map<String, Object> queryParams) {

        return given()
                .spec(RequestSpec.withToken())
                .queryParams(queryParams)
                .when()
                .get(endpoint);

    }

}