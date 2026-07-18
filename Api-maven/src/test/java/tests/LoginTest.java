package tests;

import API_FrameWork.config.TestData;
import API_FrameWork.models.LoginResponse;
import API_FrameWork.service.AuthService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LoginTest extends BaseTest {
    @Test
    @DisplayName("Verify user can login successfully")
    void loginSuccessfully() {
        AuthService authService = new AuthService();

        LoginResponse response = authService.login(TestData.EMAIL, TestData.PASSWORD);

        Assertions.assertNotNull(response.getToken(), "Login token should be generated");
        Assertions.assertEquals("1011", response.getEmpId());
        Assertions.assertEquals("traveller", response.getRole());

        System.out.println("Login completed successfully");
    }
}