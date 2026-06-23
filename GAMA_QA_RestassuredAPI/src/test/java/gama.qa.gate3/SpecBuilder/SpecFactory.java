package gama.qa.gate3.SpecBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.Matchers.lessThan;

public class SpecFactory {

    public static final String API_BASEURL = System.getProperty(
            "baseUrl",
            TestEnvironmentCheck.optional("BASE_URL","http://localhost:4000")
    );
    static ResponseSpecification resSpec;

    @BeforeAll
    static void setup(){
        RestAssured.baseURI = API_BASEURL;
        RestAssured.basePath = "/pet";
    }

    public static RequestSpecification petStatusSuccessReqSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(API_BASEURL)
                .setBasePath("/pet/findByStatus?status=")
                .setAccept(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification petStatusSuccessRespSpec(){
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }
    public static ResponseSpecification petStatusFailRespSpec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification petIDSuccessReqSpec(){
        return new RequestSpecBuilder()
                .setBaseUri(API_BASEURL)
                .setBasePath("/pet")
                .setAccept(ContentType.JSON)
                .build();
    }
    public static ResponseSpecification petIDSuccessRespSpec(){
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }




}
