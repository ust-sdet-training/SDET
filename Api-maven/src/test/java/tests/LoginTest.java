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
        LoginResponse response = new AuthService().login(TestData.EMAIL, TestData.PASSWORD);

        Assertions.assertNotNull(response.token(), "Login token should be generated");
        Assertions.assertEquals("1011", response.empId());
        Assertions.assertEquals("traveller", response.role());
        System.out.println("Login completed successfully");
    }
}
