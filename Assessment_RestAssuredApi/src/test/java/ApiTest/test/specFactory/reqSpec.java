package ApiTest.test.specFactory;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class reqSpec {
    public static RequestSpecification getJsonPlaceholderPostRequestSpecification() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")
                .build();
    }
}
