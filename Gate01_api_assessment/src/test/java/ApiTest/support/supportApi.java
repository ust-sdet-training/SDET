package ApiTest.support;

import ApiTest.config.config;

public class supportApi {
    public static String Auth()
    {
        if (config.token==null || config.token.isEmpty()) {
            throw new IllegalStateException(
                    "API_KEY is not configured in .env file."
            );
        }
        return config.token;
    }
}
