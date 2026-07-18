package com.sdet.restmock.support;

import com.sdet.restmock.config.BaseConfig;
import com.sdet.restmock.config.UserData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.util.Map;
import static com.sdet.restmock.config.UserData.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.lessThan;
public class ProductFactory {

//    static Map<String,String> tok= Map.of("email",BaseConfig.TEST_USERNAME,"password",BaseConfig.PASSWORD);
    static final String baseURL=BaseConfig.BASE_URL;

   public  static  ResponseSpecification ok=new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(ContentType.JSON)
            .build();

    public static ResponseSpecification created()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(300L))
                .build();


    }

    public static RequestSpecification getbus=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/api/buses")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();

    public static RequestSpecification booking=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/api/bookings")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();

    public static RequestSpecification tampered=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/api/auth/me")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();





    public static RequestSpecification getdel=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("booking/2")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();


    public static RequestSpecification post=new RequestSpecBuilder()
            .setBaseUri(baseURL)
            .setBasePath("/auth")
            .setContentType("application/json")
            .setAccept("application/json")
            .build();



    public static String getAuthToken()
    {
        String token=given()
                .baseUri(baseURL)
                .contentType("application/json")
                .body(UserData.tok)
                .when()
                .post("api/auth/login")
                .then().extract().path("token");
        return token;
    }

//    public static String getid()
//    {
//        String id=given()
//                .baseUri(baseURL).log().all()
//                .contentType("application/json")
//                .body(UserData.busdetails)
//                .header("Authorization", "Bearer " + Token)
//                .when()
//                .post("api/bookings")
//                .then().log().all().extract().path("id");
//        return id;
//    }
}
