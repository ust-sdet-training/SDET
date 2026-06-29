package ApiTest.test.specFactory;


import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class respSpec {
    public static ResponseSpecification createdSuccess() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }


}
