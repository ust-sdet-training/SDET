package com.tripstack.tests;


import org.junit.jupiter.api.Test;


import com.tripstack.clients.UserClient;


public class LoginTest {


    @Test

    void verifyLogin(){


        new UserClient()
                .getMe();


    }



}
