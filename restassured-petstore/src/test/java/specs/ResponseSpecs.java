package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;

public class ResponseSpecs {

    public ResponseSpecs(){}

    public static ResponseSpecification okJson_200(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification Error_404(){
        return new ResponseSpecBuilder()
                .expectStatusCode(404)
                .build();
    }
}
