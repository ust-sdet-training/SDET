package ust.sdet.test;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import ust.sdet.api.Authorization;
import ust.sdet.api.DBFunctions;
import ust.sdet.api.OrderFunction;
import ust.sdet.db.config.DatabaseConfig;
import ust.sdet.db.config.DbSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ust.sdet.api.OrderFunction.productDetails_MatchesXmlSchema;
import static ust.sdet.spec.Headers.assertOkXmlContract;


public class SDETMainTest {

    static DbSupport database;
    static List<String> createdOrderIds = new ArrayList<>();

    static String accessToken,expiredAccessToken,viewerAccessToken;

    @BeforeAll
    static void setup(){

        database = new DbSupport(DatabaseConfig.fromEnvironment());
        accessToken = Authorization.LoginOpsUser();
        expiredAccessToken  =Authorization.LoginExpiredUser();
        viewerAccessToken =Authorization.LoginViewerUser();

    }

    @AfterAll
    static void cleanCreatedRows() throws Exception{
        for(String orderid : createdOrderIds){
            database.deleteOrder(orderid);
        }
        createdOrderIds.clear();
    }

   @Test
   void DBConnectTest() throws SQLException {

       assertTrue(database.isReachable());

   }

   @Test
    void CreateAllocateShipSecureOrderandVerifyDB() throws SQLException {
       Response response = OrderFunction.makeNewSecureOrder(accessToken,Map.ofEntries(
               entry("items", List.of(101, 107)),
               entry("currency", "INR")
       ));


       System.out.println(response.asPrettyString());

       String orderid = Integer.toString(response.path("id"));


       DBFunctions.validateDBStatus(response,"CREATED");
       OrderFunction.allocateSecureOrder(accessToken,orderid);
       DBFunctions.validateDBStatus(response,"ALLOCATED");
       OrderFunction.shipSecureOrder(accessToken,orderid);
       DBFunctions.validateDBStatus(response,"SHIPPED");

   }
   //Errors 401,403,409

    @Test
    void CreateOrderNoToken() {

        OrderFunction.makeNoTokenSecureOrder(Map.ofEntries(
                entry("items", List.of(101, 107)),
                entry("currency", "INR")
        ));

    }
    @Test
    void CreateOrderExpiredToken() {

        OrderFunction.makeNewSecureOrderFailure(expiredAccessToken,Map.ofEntries(
                entry("items", List.of(101, 107)),
                entry("currency", "INR")
        ),401);

    }
    //404 test
    @Test
    void DeleteUnknownOrder() {

        OrderFunction.deleteNewOrder(accessToken,101);

    }
    @Test
    void CreateOrderGarbageToken() {

        OrderFunction.makeNewSecureOrderFailure("Garbage",Map.ofEntries(
                entry("items", List.of(101, 107)),
                entry("currency", "INR")
        ),401);

    }

    @Test
    void CreateOrderViewerToken() {

        OrderFunction.makeNewSecureOrderFailure(viewerAccessToken,Map.ofEntries(
                entry("items", List.of(101, 107)),
                entry("currency", "INR")
        ),403);

    }

    //409 error
    @Test
    void ShipWithoutAllocation() throws SQLException {
        Response response = OrderFunction.makeNewSecureOrder(accessToken,Map.ofEntries(
                entry("items", List.of(101, 107)),
                entry("currency", "INR")
        ));



        String orderid = Integer.toString(response.path("id"));

        createdOrderIds.add(orderid);

        DBFunctions.validateDBStatus(response,"CREATED");

        OrderFunction.shipSecureOrderFail(accessToken,orderid);

    }

    @Test
    void CheckXMLFormat(){
        productDetails_MatchesXmlSchema(101);
    }




}
