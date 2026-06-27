package com.ust.sdet.OMS;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

public class Server{
    public static WireMockServer wm = new WireMockServer(4016);


    public static void startTheServer(){
        wm.start();
        configureFor("127.0.0.1",7989);
    }

    public static void stopTheServer(){
        wm.stop();
    }

}
