import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecFactory {

    private static final String BASE_URL = "https://restful-booker.herokuapp.com";


    public static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }


    public static RequestSpecification authSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)

                .addHeader("Cookie", "token=" + token)
                .build();
    }


    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()

                .expectStatusCode(statusCode)
                .build();
    }

}