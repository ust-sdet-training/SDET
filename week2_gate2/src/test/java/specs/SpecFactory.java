package specs;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import support.TokenManager;

public class SpecFactory {

    public static RequestSpecification authSpec(){
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:4000")
                .setContentType(ContentType.JSON)
                .setAuth(RestAssured.oauth2(TokenManager.getToken()))
                .build();
    }
    static String baseUrl(){
        return System.getenv().getOrDefault("BASE_URL","http://localhost:4000");

    }

    public static RequestSpecification legacyProductsXml(){
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl())
                .setBasePath("/api/legacy/products")
                .setAccept(ContentType.XML)
                .log(LogDetail.URI)
                .build();
    }

    public static RequestSpecification unauthed(){
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost:4000")
                .setContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification createdJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification notFound() {
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }

    public static ResponseSpecification unauthorized() {
        return new ResponseSpecBuilder()
                .expectStatusCode(401)
                .build();
    }

}
