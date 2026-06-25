package ApiTest.test.specFactory;

import ApiTest.support.supportApi;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class reqSpec {
    public static RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()

                .setContentType(ContentType.JSON)
                .addHeader("Accept", "application/json")

                .build();
    }
    public static RequestSpecification getRequestSpecificationBooking() {
        return new RequestSpecBuilder()

                .addHeader("Accept", "application/json")
                .build();
    }
    public static RequestSpecification getRequestSpecificationUpdate() {
        return new RequestSpecBuilder()

                .addHeader("Accept", "application/json")
                .addHeader("token", supportApi.Auth())
                .build();
    }


}
