package ust.sdet.SpecFactory;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import ust.sdet.Config.TestEnvironment;

import static io.restassured.RestAssured.given;

public class AuthSpec {

    ConfigSpec configSpec =new ConfigSpec();

    public RequestSpecification setToken(String token){
        return
                given()
                        .spec(configSpec.setHeaders())
                        .header("Authorization", "Bearer " + token)
//                        .auth().oauth2(token)
                        ;
    }


}
