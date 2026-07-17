package tests;

import API_FrameWork.models.LoginResponse;
import API_FrameWork.service.AuthService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoginTest {

    @Test
    public void loginSuccessfully() {

        AuthService authService = new AuthService();

        LoginResponse response =
                authService.login(
                        "karl@tripstack.test",
                        "Password@123");

        Assertions.assertNotNull(response.getToken());
        Assertions.assertEquals("1011", response.getEmpId());
        Assertions.assertEquals("traveller", response.getRole());
    }
}