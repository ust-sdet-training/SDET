package com.ust.sdet.api.specification;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static com.ust.sdet.api.support.Utils.BASE_URL;
import static io.restassured.RestAssured.oauth2;
import static org.hamcrest.Matchers.*;

public class OrderSpec extends CommonSpec{
    public static RequestSpecification orderReqSpec(String token) {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setBasePath("/api/secure/orders")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .setAuth(oauth2(token))
                .build();
    }
    public static ResponseSpecification orderResSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(201)
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(800L))
                .expectBody("id", notNullValue())
                .expectBody("orderId", notNullValue())
                .expectBody("status", notNullValue())
                .expectBody("payment", notNullValue())
                .expectBody("paymentMethod", notNullValue())
                .expectBody("channel", notNullValue())
                .expectBody("items.size()", greaterThan(0))
                .expectBody("items.product.id", notNullValue())
                .expectBody("subtotal", notNullValue())
                .expectBody("total", notNullValue())
                .expectBody("address", notNullValue())
                .expectBody("deliverySlot", notNullValue())
                .expectBody("userId", notNullValue())
                .expectBody("createdAt", notNullValue())
                .build();
    }
//            "items": [
//    {
//        "product": {
//        "id": 101,
//                "name": "Running Shoes",
//                "price": 4499,
//                "category": "Footwear"
//    },
//        "quantity": 1,
//            "productId": 101
//    },
//    {
//        "product": {
//        "id": 104,
//                "name": "Insulated Water Bottle",
//                "price": 999,
//                "category": "Fitness"
//    },
//        "quantity": 1,
//            "productId": 104
//    }
//    ],
//
}
