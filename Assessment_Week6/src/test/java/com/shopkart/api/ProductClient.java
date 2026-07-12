package com.shopkart.api;


import com.shopkart.Config.RequestSpecFactory;
import com.shopkart.support.BaseApiTest;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.devtools.latest.network.model.Request;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ProductClient extends TokenGeneration{

    public ProductClient() {
        ProductClient.token = getToken();
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        ProductClient.ID = ID;
    }

    private static int ID;

    public static String getAuthToken() {
        return token;
    }

    private static String token;

    public static String getBobtoken() {
        return bobtoken;
    }

    public static void setBobtoken(String bobtoken) {
        ProductClient.bobtoken =BobToken.getToken();
    }

    private static String bobtoken;



    public Response getProduct(String product){

        return given()
                .spec(RequestSpecFactory.requestSpec())
                .when()
                .basePath("/api/products")
                .get(product);

    }

   public Response  getProducts(String product){
        return given()
                .spec(RequestSpecFactory.requestSpec())
                .queryParam("q", product)
                .when()
                .get("/api/products");
   }


   public int createCart(){
       return given()
               .spec(RequestSpecFactory.requestSpec())
               .auth().oauth2(getAuthToken())
               .when()
               .basePath("/api/carts")
               .post("").then().extract().path("cartId");
   }

  public Response addItems(String token,int id,Map<String,Object> request){
      return  given()
              .spec(RequestSpecFactory.requestSpec())
              .body(request)
              .auth().oauth2(getAuthToken())
              .pathParam("id", id)
              .when()
              .basePath("/api/carts/{id}/items")
              .post("");
  }
   public Response addToCart(String product,int qty){


           setID(createCart());
          String sku = getProducts(product).then().extract().path("[0].sku");

       Map<String, Object> request = Map.of(
               "sku", sku,
               "qty", qty
       );

           return addItems(getAuthToken(),getID(),request);
   }

 public  Response getCartItems(){
     return  given()
             .spec(RequestSpecFactory.requestSpec())
             .auth().oauth2(getAuthToken())
             .pathParam("id", getID())
             .when()
             .basePath("/api/carts/{id}")
             .get("");
 }
 public int makeCheckoutAt(String address){

         int  id = getCartItems().then().log().all().extract().path("cartId");


     Map<String, Object> request = Map.of(
             "address", address,
             "cartId", id
     );

         return given()
                 .spec(RequestSpecFactory.requestSpec())
                 .auth().oauth2(getAuthToken())
                 .body(request)
                 .when()
                 .basePath("/api/orders")
                 .post("").then().extract().path("id");

 }

  public Response placeOrderAt(String address){
        setID(makeCheckoutAt(address));

        return given()
                .spec(RequestSpecFactory.requestSpec())
                .auth().oauth2(getAuthToken())
                .pathParam("id",getID())
                .when()
                .basePath("/api/orders/{id}")
                .get("");

  }

  public Response unathorizedAcess(){

          return given()
                  .spec(RequestSpecFactory.requestSpec())
                  .auth().oauth2(BobToken.getToken())
                  .pathParam("id", getID())
                  .when()
                  .basePath("/api/orders/{id}")
                  .get("");
      }


    public Response cancelOrder(int orderId) {
        return given()
                .spec(RequestSpecFactory.requestSpec())
                .auth().oauth2(getAuthToken())
                .pathParam("id", orderId)
                .when()
                .post("/api/orders/{id}/cancel");
    }

    public Response addOutOfStockItem(String product) {

        int cartId = createCart();

        String sku = getProducts(product)
                .then()
                .extract()
                .path("[0].sku");

        int stock = getProducts(product)
                .then()
                .extract()
                .path("[0].stock");

        Map<String,Object> request = Map.of(
                "sku", sku,
                "qty", stock + 1
        );

        return addItems(getAuthToken(), cartId, request);
    }
  }







