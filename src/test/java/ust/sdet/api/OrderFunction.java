package ust.sdet.api;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static ust.sdet.api.Authorization.BASE_URL;
import static ust.sdet.api.Authorization.authedReqSpec;

public class OrderFunction {

    public static Response makeNewSecureOrder(String accessToken, Map orders){

        return given()
                        .spec(ordersTokenReqSpec(accessToken, orders))
                        .when()
                        .post("")
                        .then()
                        .body("status", equalTo("CREATED"))
                .body(matchesJsonSchemaInClasspath("schemas/json/orders.schema.json"))
                        .extract().response();

    }
    public static void makeNewSecureOrderFailure(String accessToken, Map orders, int errorcode){

        given()
                .spec(ordersTokenReqSpec(accessToken, orders))
                .when()
                .post("")
                .then()
                .spec(ResponseJson.ErrorJson(errorcode));

    }

    public static void makeNoTokenSecureOrder(Map orders){

         given()
                .baseUri(BASE_URL)
                .basePath("/api/secure/orders")
                .body(orders)
                .when()
                .post("")
                .then()
                .spec(ResponseJson.ErrorJson(401));

    }
    public static void allocateSecureOrder(String accesstoken,String id){

        String accessToken = Authorization.LoginOpsUser();

             given()
                .spec(authedReqSpec(accessToken))
                .when()
                .post("/{id}/allocate",id)
                .then()
                .body("status", equalTo("ALLOCATED"));
    }

    public static void shipSecureOrder(String accesstoken,String id){

        String accessToken = Authorization.LoginOpsUser();

        given()
                .spec(authedReqSpec(accessToken))
                .when()
                .post("/{id}/ship",id)
                .then()
                .body("status", equalTo("SHIPPED"))
                .spec(ResponseJson.okJson());
    }
    public static void shipSecureOrderFail(String accesstoken,String id){

        String accessToken = Authorization.LoginOpsUser();

        given()
                .spec(authedReqSpec(accessToken))
                .when()
                .post("/{id}/ship",id)
                .then()
                .spec(ResponseJson.ErrorJson(409));
    }
    public static RequestSpecification ordersTokenReqSpec(String token, Map<String,Object> maps){
        return
                given()
                        .spec(authedReqSpec(token))
                        .basePath("/api/secure/orders")
                        .body(maps);
    }

}
