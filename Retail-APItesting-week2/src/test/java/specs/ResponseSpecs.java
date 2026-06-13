package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static org.hamcrest.Matchers.*;

public final class ResponseSpecs {

    public static ResponseSpecification loginSuccessSpec;
    public static ResponseSpecification oauthSuccessSpec;

    public static ResponseSpecification orderCreatedSpec;
    public static ResponseSpecification orderSuccessSpec;

    public static ResponseSpecification badRequestSpec;
    public static ResponseSpecification unauthorizedSpec;
    public static ResponseSpecification forbiddenSpec;
    public static ResponseSpecification notFoundSpec;
    public static ResponseSpecification conflictSpec;
    public static ResponseSpecification cartCreatedSpec;

    private ResponseSpecs() {
    }

    public static void createResponseSpecifications() {

        loginSuccessSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .expectBody("token", notNullValue())
                        .expectBody("user.id", notNullValue())
                        .expectBody("user.email", notNullValue())
                        .expectBody("user.role", notNullValue())
                        .expectBody("user.name", notNullValue())
                        .build();

        oauthSuccessSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .expectBody("access_token", notNullValue())
                        .expectBody("token_type", equalTo("Bearer"))
                        .expectBody("expires_in", greaterThan(0))
                        .build();

        orderCreatedSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(201)
                        .expectContentType(ContentType.JSON)
                        .expectBody("id", notNullValue())
                        .expectBody("orderNumber", notNullValue())
                        .expectBody("status", notNullValue())
                        .build();

        orderSuccessSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(200)
                        .expectContentType(ContentType.JSON)
                        .expectBody("id", notNullValue())
                        .expectBody("orderNumber", notNullValue())
                        .expectBody("status", notNullValue())
                        .build();

        badRequestSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(400)
                        .build();

        unauthorizedSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(401)
                        .build();

        forbiddenSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(403)
                        .build();

        notFoundSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(404)
                        .build();

        conflictSpec =
                new ResponseSpecBuilder()
                        .expectStatusCode(409)
                        .build();
        ResponseSpecification productsListSpec = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectBody("items.size()", greaterThan(0))
                .expectBody("total", greaterThan(0))
                .build();

        ResponseSpecification cartCreatedSpec = new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectBody("id", notNullValue())
                .expectBody("quantity", greaterThan(0))
                .expectBody("product.id", notNullValue())
                .build();
    }
}