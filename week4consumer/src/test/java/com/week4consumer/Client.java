package com.week4consumer;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;

public class Client {
    
    private String baseUrl;
 
    public Client(String baseUrl){
        this.baseUrl=baseUrl;
    }
 
    public Product getProduct(int id) {
        
         return  given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/product/" + id)
                .then()
                .statusCode(200)
                .extract()
                .as(Product.class);
    }
    public static record Product(int productId,String name,double price){}
 
 
}
