package performance;


import clients.AuthClient;
import models.LoginRequest;
import models.LoginResponse;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class LoginPerformanceTest {


    private final AuthClient authClient =
            new AuthClient();



    @Test
    void verifyLoginResponseTime(){


        LoginRequest request =
                new LoginRequest(
                        "olivia@tripstack.test",
                        "Password@123"
                );


        long start =
                System.currentTimeMillis();



        LoginResponse response =
                authClient.login(request);



        long end =
                System.currentTimeMillis();



        long responseTime =
                end - start;



        System.out.println(
                "Login Response Time : "
                        + responseTime
                        + " ms"
        );



        assertNotNull(
                response.token()
        );


        // Performance SLA
        assertTrue(
                responseTime < 5000,
                "Login response exceeded 5 seconds"
        );

    }

}