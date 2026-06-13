package com.ust.sdet.api.apiframework.spec;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.ust.sdet.api.apiframework.auth.apiConfig.NoOpsToken;
import static com.ust.sdet.api.apiframework.auth.apiConfig.expiredToken;

import static com.ust.sdet.api.apiframework.config.Constants.RETAIL_API_KEY;
import static com.ust.sdet.api.apiframework.config.Constants.baseUrl;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.lessThan;

public class SpecFactory {

    public static RequestSpecification authed(String token)
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }

    public static RequestSpecification notauthed()
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .build();
    }

    public static RequestSpecification getauthedReq(String token)
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addHeader("X-API-KEY",RETAIL_API_KEY)
                .setAuth(oauth2(token))
                .build();
    }



    public static ResponseSpecification getauthedRes() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(800L))
                .build();
    }

    public static RequestSpecification deleteJsonReq(String token)
    {
        return  new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/cart")
                .setAccept("application/json")
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }

    public static ResponseSpecification deleteJsonRes()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(204)
                .expectResponseTime(lessThan(800L))
                .build();

    }

    public static ResponseSpecification postJson()
    {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(201)
                .expectResponseTime(lessThan(800L))
                .build();

    }


    public static ResponseSpecification missTokenRes() {

        return  new ResponseSpecBuilder()

                .expectContentType(ContentType.JSON)
                .expectStatusCode(401)
                .expectResponseTime(lessThan(800L))
                .build();
    }

    public static RequestSpecification  invalidTokenReq() {

        return   new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2("abcd"))
                .build();
    }

    public static ResponseSpecification invalidTokenRes() {

        return  new ResponseSpecBuilder()

                .expectContentType(ContentType.JSON)
                .expectStatusCode(401)
                .expectResponseTime(lessThan(800L))
                .build();
    }



    public static RequestSpecification NoOPSTokenReq() {

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(NoOpsToken()))
                .build();
    }


    public static ResponseSpecification NoOPSTokenRes() {

        return  new ResponseSpecBuilder()

                .expectContentType(ContentType.JSON)
                .expectStatusCode(403)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification expiredTokenReq() {

        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2((expiredToken())))
                .build();
    }


    public static ResponseSpecification expiredTokenRes() {

        return  new ResponseSpecBuilder()

                .expectContentType(ContentType.JSON)
                .expectStatusCode(401)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification Logreq()
    {
        return   new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/auth/login")
                .setContentType(ContentType.JSON)
                .setAccept("application/json")
                .build();
    }

    public static ResponseSpecification Logres ()
    {
        return   new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification addToCartreq(String token)
    {
        return    new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/cart/items")
                .setAccept("application/json")
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }


    public static RequestSpecification placeOrder(String token)
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/orders")
                .setAccept("application/json")
                .setContentType(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }



    public static RequestSpecification alloReqSpec(String path, int id, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api"+ path +"/"+id+"/allocate")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }

    public static RequestSpecification shipReqSpec(String path, int id, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api"+ path +"/"+id+"/ship")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }


    public static RequestSpecification reqSpec(String path, String pToken) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api"+ path)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(pToken))
                .build();

    }

    public static ResponseSpecification resSpec () {
        return  new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .build();
    }


    public static RequestSpecification reqXml()
    {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath("/api/legacy/products/101.xml")
                .setAccept("application/xml")
                .setContentType(ContentType.XML)
                .build();

    }

    public static ResponseSpecification resXml()
    {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.XML)
                .expectResponseTime(lessThan(800L))
                .build();

    }

}

