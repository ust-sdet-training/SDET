package api.tests;

import api.api.ApiClient;
import api.config.AppConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class AuthNegativeTest {

    @Test
    void loginWithBadCredentialsShouldFail() {
        Response resp = ApiClient.loginRaw(AppConfig.USER_EMAIL, "wrong-password");
        int status = resp.getStatusCode();
        assertThat("Login should not succeed with bad credentials", status == 200, equalTo(false));
    }
}
