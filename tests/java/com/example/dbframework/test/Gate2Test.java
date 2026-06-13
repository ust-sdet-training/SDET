package com.example.dbframework.tests;

import com.example.dbframework.support.RequestSpecFactory;
import com.example.dbframework.config.DatabaseConfig;
import com.example.dbframework.model.OrderRow;
import com.example.dbframework.support.DbSupport;
import com.example.dbframework.support.ResponseSpecFactory;
import com.example.dbframework.support.EnvironmentalVariables;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.example.dbframework.support.RequestSpecFactory.fetchToken;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;


class Gate2Test {
    private static final String TOKEN_URL_CLIENT_ID = EnvironmentalVariables.client();
    private static final String TOKEN_URL_CLIENT_SECRET = EnvironmentalVariables.client_secret();


    private static final List<Long> createdOrderIds = Collections.synchronizedList(new ArrayList<Long>());
    private static DbSupport database;
    private static String opstoken;
    private static RequestSpecification SecuredAuthOrder;

    @BeforeAll
    static void setup()
    {

        database = new DbSupport(DatabaseConfig.fromEnvironment());
        opstoken =fetchToken(TOKEN_URL_CLIENT_ID,TOKEN_URL_CLIENT_SECRET);

        SecuredAuthOrder = RequestSpecFactory.authedOrder(opstoken);
    }

    @Test
    @DisplayName("Verifying Persisted Order")
    void validatingflow() throws Exception{
        var order = EnvironmentalVariables.getOrder();


        Response created =  given().spec(SecuredAuthOrder).body(order)
                .when().post("").then().spec(ResponseSpecFactory.validateOrders()).extract().response();

        Integer id = created.path("orderId");
        OrderRow row = RequestSpecFactory.support(created);

        assertNotNull(row,"order must be persisted");
        assertNotNull("orderNumber",row.getOrderNumber());
        assertEquals("CREATED",row.getStatus());
        assertEquals(0,row.getTotal().compareTo(created.jsonPath().getObject("total", BigDecimal.class)));
        assertEquals("svc-retail-ops",row.getUserId());
        assertNotNull(row.getCreatedAt());
        Instant createdOrderIds = row.getCreatedAt()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant now = Instant.now();
        assertTrue(createdOrderIds.isAfter(now.minusSeconds(60)));
        assertTrue(createdOrderIds.isBefore(now.plusSeconds(5)));

      Response allocate = given().spec(SecuredAuthOrder)
                .when().post("/{id}/allocate",id)
                .then().spec(ResponseSpecFactory.validate200Response()).extract().response();
       OrderRow allocaterow= RequestSpecFactory.support(allocate);
        assertNotNull(allocaterow,"order must be persisted");
        assertNotNull("orderNumber",allocaterow.getOrderNumber());
        assertEquals("ALLOCATED",allocaterow.getStatus());
        assertEquals(0,allocaterow.getTotal().compareTo(allocate.jsonPath().getObject("total", BigDecimal.class)));
        assertEquals("svc-retail-ops",allocaterow.getUserId());
        assertNotNull(row.getCreatedAt());
        Instant createdOrderIdAllocate = allocaterow.getCreatedAt()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant Allocate_now = Instant.now();
        assertTrue(createdOrderIdAllocate.isAfter(Allocate_now.minusSeconds(60)));
        assertTrue(createdOrderIdAllocate.isBefore(Allocate_now.plusSeconds(5)));


       Response shipped = given().spec(SecuredAuthOrder)
                .when().post("/{id}/ship",id)
                .then().spec(ResponseSpecFactory.validate200Response()).extract().response();
        OrderRow shippedrow= RequestSpecFactory.support(shipped);
        assertEquals("SHIPPED",shippedrow.getStatus());
        assertEquals(0,shippedrow.getTotal().compareTo(shipped.jsonPath().getObject("total", BigDecimal.class)));
        assertEquals("svc-retail-ops",shippedrow.getUserId());
        assertNotNull(shippedrow.getCreatedAt());
        Instant createdOrderIdShipped = shippedrow.getCreatedAt()
                .atZone(ZoneId.systemDefault())
                .toInstant();
        Instant Shipped_now = Instant.now();
        assertTrue(createdOrderIdShipped.isAfter(Shipped_now.minusSeconds(60)));
        assertTrue(createdOrderIdShipped.isBefore(Shipped_now.plusSeconds(5)));


      Response apilayer=  given().spec(SecuredAuthOrder)
                .when().get("/{id}",id)
                .then().log().all().spec(ResponseSpecFactory.validate200Response()).extract().response();
      Integer Order_id = apilayer.path("id");
      OrderRow db = DbSupport.findOrder(Order_id);
        assertEquals(apilayer.path("orderNumber"),db.getOrderNumber());
        assertEquals(apilayer.path("status"),db.getStatus());
        assertEquals(0,db.getTotal().compareTo(apilayer.jsonPath().getObject("total", BigDecimal.class)));
        assertEquals("svc-retail-ops",db.getUserId());
    }



  @AfterEach
  void cleanup() throws Exception{
      for(long orderId:createdOrderIds){
          database.deleteOrder(orderId);
      }
      createdOrderIds.clear();
  }
}

