package assessment.factory;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalToIgnoringCase;

public class SpecFactory {
    private static final String BASE_URL = System.getProperty(
            "baseUrl",
            System.getenv().getOrDefault("BASE_URL", "https://jsonplaceholder.typicode.com/")
    );

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = "";

    }

    public static RequestSpecification reqSpec = new RequestSpecBuilder()
            .setContentType(ContentType.JSON)
            .setBaseUri(BASE_URL)
            .setBasePath(basePath)
            .build();

    public static ResponseSpecification okJson = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();

    public static ResponseSpecification createdJson = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .expectContentType(ContentType.JSON)
            .build();

//    public static String getToken(String id, String secret){
//        return given()
//                .baseUri(BASE_URL)
//                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
//                .auth().preemptive().basic(id,secret)
//                .formParam("grant_type","client_credentials")
//                .when()
//                .post("/oauth/token")
//                .then()
//                .statusCode(200)
//                .body("token_type", equalToIgnoringCase("Bearer"))
//                .extract()
//                .path("access_token");
//    }
//public static RequestSpecification reqSpec(String X_API_KEY){
//    return new RequestSpecBuilder()
//            .addHeader("x-api-key",X_API_KEY)
//            .setContentType("application/json")
//            .build();
//}
}
