package com.ust.sdet.OMS;

import au.com.dius.pact.provider.junitsupport.Provider;
import com.ust.sdet.OMS.Server;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import au.com.dius.pact.provider.junitsupport.State;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.extension.ExtendWith;

import static com.github.tomakehurst.wiremock.client.WireMock.*;


@Provider("OMS-provider")
//@PactFolder("target/pacts")
@PactBroker(
        host = "127.0.0.1",
        port = "9292"
)
public class OMSProviderVerification {


    public static Server server;

//    public static WireMockServer wm = new WireMockServer();
    @BeforeAll
    static void startServer(){
        Server.startTheServer();
    }

    @AfterAll
    static void stopServer(){
        Server.stopTheServer();
    }

    @BeforeEach
    void setContext(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("127.0.0.1", 4016));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void verifyThePact(PactVerificationContext context){
        context.verifyInteraction();
    }


    //    States
    @State("Check the order with ID 101 exists")
    public void orderExists(){

        Server.wm.stubFor(post(urlEqualTo("/orders/100"))
                .willReturn(okJson("""
                        {
                          "id":101,
                     
                          "category":"Mobiles",
                          "stock":100,
                          "product_name":"Samsung s26"
                          "status":"CONFIRMED",
                        
                        }
                        """)));
    }


    @State("Create a New Order with ID 101")
    public void createOrder(){

        Server.wm.stubFor(post(urlEqualTo("/orders"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type","application/json")
                        .withBody("""
                                {
                                  "id":101,
                     
                          "category":"Mobiles",
                          "stock":100,
                          "product_name":"Samsung s26"
                          
                                }
                                """)));
    }

    @State("Check the inventory for Mobile")
    public void inventoryExists(){

        Server.wm.stubFor(get(urlEqualTo("/inventory/Mobiles"))
                .willReturn(okJson("""
                        {
                          "mobile":"Samsung",
                          "stock":60,
                          "address":"India"
                        }
                        """)));
    }



}
