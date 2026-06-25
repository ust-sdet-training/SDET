package apiframework.spec;


import com.google.protobuf.Api;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static apiframework.config.Constants.API_KEY;
import static apiframework.config.Constants.baseUrl;
import static io.restassured.RestAssured.oauth;
import static io.restassured.RestAssured.oauth2;


public class SpecFactory {


    public static ResponseSpecification getRes()
    {
        return new ResponseSpecBuilder()

                .expectStatusCode(200)
                .build();
    }

    public static RequestSpecification getReq()
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }





    public static RequestSpecification postReq()
    {
        return new RequestSpecBuilder()


                .setBaseUri(baseUrl)
                .setBasePath("")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification  putReq()
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2("abc123"))
                .build();
    }

    public static ResponseSpecification postRes()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .build();
    }



    public static RequestSpecification  delReq()
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2("abc123"))

                .build();
    }



}
