package ApiTest.test.specFactory;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class respSpec {
    public static ResponseSpecification okSuccess() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }
}
