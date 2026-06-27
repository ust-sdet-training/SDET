package test.contract.pos;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(
        providerName = "oms-provider",
        pactVersion = PactSpecVersion.V4
)
class PosOmsConsumerPactTest {


    private static final Logger log = LoggerFactory.getLogger(PosOmsConsumerPactTest.class);



    /// SKU-GET//

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getInventoryShow(PactDslWithProvider builder) {

        return builder
                .given("Checking For Sku-10")

                .uponReceiving("Request for Sku-10 is available")
                .path("/order/10")
                .method("GET")

                .willRespondWith()
                .status(200)

                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json")

                .body(new PactDslJsonBody()
                        .integerType("id", 10)
                        .stringType("status", "Confirmed")
                        .numberType("total", 100))

                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getInventoryShow")
    void testGetInventory(MockServer mockServer) {

        Response response =

                given()
                        .baseUri(mockServer.getUrl())

                        .when()
                        .get("/order/10");

        response.then()
                .statusCode(200);

        response.then();
    }




    ////-----CREATE ORDERS----------//

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact createOrders(PactDslWithProvider builder) {
        return builder
                .given("Checking for creating order")
                .uponReceiving("Order will be created")
                .path("/order")
                .method("POST")


                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )

                .body(new PactDslJsonBody()
                        .integerType("statuscode", 0)
                        .integerType("orderId", 0)
                        .stringType("status", "NEW")
                        .numberType("total", 0))

                .willRespondWith()
                .status(201)
                .matchHeader(
                        "Content-Type",
                        "application/json(;.*)?",
                        "application/json"
                )

                .body(new PactDslJsonBody()
                        .integerType("statuscode", 201)
                        .integerType("orderId", 1)
                        .stringType("status", "SUCCESSFULL")
                        .numberType("total", 100.0)).toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "createOrders")
    void toTestCreate(MockServer mockServer)
    {
        OmsClient omsClient =
                new OmsClient(mockServer.getUrl());

        OmsClient.Order request =
                new OmsClient.Order(
                        0,
                        0,
                        "NEW",
                        0
                );

        OmsClient.Order response =
                omsClient.createOrder(request);
        assertEquals(1, response.orderId());
        assertEquals("SUCCESSFULL", response.status());
        assertEquals(100.0, response.total());

    }


    /// ----------------GET-----------------------------///

    @Pact(provider = "oms-provider", consumer = "pos-consumer")
    V4Pact getTheId(PactDslWithProvider builder){
        return builder
                .given("check the id exist")
                .uponReceiving("Id is present")
                .path("/product")
                .method("GET")

                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")

                .body(new PactDslJsonBody()
                        .integerType("id", 0)
                        .stringType("status", "New"))


                .willRespondWith()
                .status(200)

                .matchHeader("Content-Type", "application/json(;.*)?", "application/json")

                .body(new PactDslJsonBody()
                        .integerType("id", 1)
                        .stringType("status", "Confirmed"))
                .toPact(V4Pact.class);
    }

    @Test
    @PactTestFor(pactMethod = "getTheId")
    public void checkForId(MockServer mockServer)
    {
        OmsClient omsClient = new OmsClient(mockServer.getUrl());

        OmsClient.product request = new OmsClient.product(      // OmsClient.product used because it will then contain types of id(int) and status(String) only.
                0,"New"                                  // new OmsClient.product is done becasue am object is created, if we create object then only we can use it.
        );

        OmsClient.product response = omsClient.getidOrder(request);         // since we created object , we used it here
        assertEquals(1, response.id());
        assertEquals("Confirmed", response.status());

    }

}





































