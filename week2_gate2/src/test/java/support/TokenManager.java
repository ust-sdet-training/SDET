package support;

import services.AuthService;

public class TokenManager {

    private TokenManager() {
    }

    public static String opsToken() {
        return AuthService.getOpsAccessToken();
    }

    public static String viewerToken() {
        return AuthService.getViewerAccessToken();
    }
}