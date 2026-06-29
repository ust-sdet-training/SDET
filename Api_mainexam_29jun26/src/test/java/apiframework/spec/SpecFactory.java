package apiframework.spec;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static apiframework.config.Constants.baseUrl;



public class SpecFactory {


    public static RequestSpecification postReq()
    {
        return new RequestSpecBuilder()


                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification postRes()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }

}
