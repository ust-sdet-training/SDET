package tests;


import clients.AuthClient;
import org.junit.jupiter.api.Test;
import utils.TokenManager;


public class AuthTest {


    @Test
    void loginTest(){
        String token = new AuthClient().login();
        TokenManager.setToken(token);
    }

}