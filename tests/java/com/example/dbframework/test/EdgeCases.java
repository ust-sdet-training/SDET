package com.example.dbframework.tests;

import com.example.dbframework.support.DbSupport;
import com.example.dbframework.config.DatabaseConfig;
import com.example.dbframework.support.EnvironmentalVariables;
import com.example.dbframework.support.ResponseSpecFactory;
import com.example.dbframework.support.RequestSpecFactory;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.dbframework.support.RequestSpecFactory.fetchToken;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class EdgeCases {
    private static final String CLIENT_ID = EnvironmentalVariables.client();
    private static final String CLIENT_SECRET = EnvironmentalVariables.client_secret();
    private static final String VIEWER_ID = EnvironmentalVariables.getVIEWER_CLIENT_ID();
    private static final String VIEWER_SECRET = EnvironmentalVariables.getVIEWER_CLIENT_SECRET();
    private static final String EXPIRED_ID = EnvironmentalVariables.getEXPIRED_CLIENT_ID();
    private static final String EXPIRED_SECRET = EnvironmentalVariables.getEXPIRED_CLIENT_SECRET();


     private static final List<Long> createdOrderIds = Collections.synchronizedList(new ArrayList<Long>());
     private static DbSupport database;
     private static String opstoken;
     private static String viewertoken;
     private static String expiredtoken;
     private static String invalidtoken;
    private static  String invalid_id;
    private static  String existing_id;
     private static RequestSpecification SecuredAuthOrder;
     @BeforeAll
      static void setup()
     {

         database = new DbSupport(DatabaseConfig.fromEnvironment());
         opstoken =fetchToken(CLIENT_ID,CLIENT_SECRET);
         viewertoken = fetchToken(VIEWER_ID,VIEWER_SECRET);
         expiredtoken = fetchToken(EXPIRED_ID,EXPIRED_SECRET);
         invalidtoken = EnvironmentalVariables.getInvalid_token();
         SecuredAuthOrder = RequestSpecFactory.authedOrder(opstoken);
         invalid_id = EnvironmentalVariables.getInvalid_Id();
         existing_id =EnvironmentalVariables.getExisting_id();
     }

    @Test
    @DisplayName("Calling Allocate after Shipped")
    void Calling_Allocate_After_Shipped(){
        given().spec(SecuredAuthOrder)
                .when().post("/{id}/allocate",existing_id)
                .then().spec(ResponseSpecFactory.validate409Response())
                .body("currentStatus",equalTo("SHIPPED"))
                .body("expectedStatus",equalTo("CREATED"));
    }

    @Test
    @DisplayName("Calling ship after create")
    void Calling_ship(){
        var order = EnvironmentalVariables.getOrder();


       Response created= given().spec(SecuredAuthOrder).body(order)
                .when().post("").then().spec(ResponseSpecFactory.validateOrders()).extract().response();

        Integer id = created.path("orderId");
        given().spec(SecuredAuthOrder)
                .when().post("/{id}/ship",id)
                .then().spec(ResponseSpecFactory.validate409Response())
                .body("currentStatus",equalTo("CREATED"))
                .body("expectedStatus",equalTo("ALLOCATED"))
                .body("message",equalTo("Cannot move order from CREATED to SHIPPED"));
    }

    @Test
    @DisplayName("Calling shipped after created")
    void Calling_Shipped_after_create(){
        given().spec(SecuredAuthOrder)
                .when().post("/{id}/ship",existing_id)
                .then().spec(ResponseSpecFactory.validate409Response())
                .body("currentStatus",equalTo("SHIPPED"))
                .body("expectedStatus",equalTo("ALLOCATED"));
    }

    @Test
    @DisplayName("Calling Allocate with Invalid")
    void Calling_Allocat_with_InvalidorBeforeCreated(){
        given().spec(SecuredAuthOrder)
                .when().post("/{id}/allocate",invalid_id)
                .then().spec(ResponseSpecFactory.validate404Response())
                .body("message",equalTo("Order not found"));
    }

    @Test
    @DisplayName("Viewer Token Validation")
    void Viewer_token_test(){
         given().spec(RequestSpecFactory.authedOrder(viewertoken))
                 .when().post("").then().spec(ResponseSpecFactory.validate403Response())
                 .body("message",equalTo("OPS role required"));
    }

    @Test
    @DisplayName("Expiry Token Validation")
    void Expiry_token_test(){
        given().spec(RequestSpecFactory.authedOrder(expiredtoken))
                .when().post("").then().log().all().spec(ResponseSpecFactory.validate401Response())
                .body("message",equalTo("Access token expired"));
    }

    @Test
    @DisplayName("Expiry Token Validation")
    void Invalid_token_test(){
        given().spec(RequestSpecFactory.authedOrder(invalidtoken))
                .when().post("").then().log().all().spec(ResponseSpecFactory.validate401Response())
                .body("message",equalTo("Authentication required"));
    }

    @Test
    @DisplayName("No authentication")
    void No_authentication(){
        given().spec(RequestSpecFactory.getOrderswithnoauth())
                .when().post("").then().log().all().spec(ResponseSpecFactory.validate401Response())
                .body("message",equalTo("Authentication required"));
    }

    @AfterEach
    void cleanOrders() throws Exception{
        for(long orderId:createdOrderIds){
            database.deleteOrder(orderId);
        }
        createdOrderIds.clear();
    }
}
