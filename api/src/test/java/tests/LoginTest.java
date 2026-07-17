package tests;


import clients.AuthClient;
import models.LoginResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import support.TestData;



public class LoginTest {


    private final AuthClient authClient =
            new AuthClient();



    @Test
    void verifyLogin(){


        LoginResponse response =
                authClient.login(
                        TestData.loginUser()
                );



        Assertions.assertNotNull(
                response.token()
        );


        Assertions.assertEquals(
                "1015",
                response.empId()
        );


        Assertions.assertEquals(
                "traveller",
                response.role()
        );


        System.out.println(
                "Login successful"
        );

    }

}