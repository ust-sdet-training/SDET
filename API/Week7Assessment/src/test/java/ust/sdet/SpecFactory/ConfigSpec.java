package ust.sdet.SpecFactory;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ust.sdet.Config.TestEnvironment;

import static io.restassured.RestAssured.given;

public class ConfigSpec {

    public RequestSpecification setHeaders(){
        return
                given()
                .baseUri(TestEnvironment.required("BASE_URL"))
                .basePath("/api")
                .contentType(ContentType.JSON);
    }

}
