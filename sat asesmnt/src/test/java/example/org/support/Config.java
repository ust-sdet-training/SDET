package example.org.support;


public final class Config {
    private Config() {}

    public static String baseUrl() {
        return TestEnvironment
                .optional("BASE_URL", "http://localhost:5173")
                .replaceAll("/$", "");
    }
    public static String homeUrl() {
        return baseUrl() + "/home";
    }

    public static String catalogUrl() {
        return baseUrl() + "/catalog";
    }

    public static String cartUrl() {
        return baseUrl() + "/cart";
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                TestEnvironment.optional("HEADLESS", "true")
        );
    }
}
