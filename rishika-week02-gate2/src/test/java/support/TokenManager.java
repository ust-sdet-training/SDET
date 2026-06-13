
package support;

import services.AuthService;

public class TokenManager {

    private static String token;

    public static String getToken() {

        if (token == null) {

            token = new AuthService().getAccessToken();
        }

        return token;
    }
}

 