package api.tripstack.config;

import io.github.cdimascio.dotenv.Dotenv;

public final class ConfigManager {
    private static final Dotenv DOTENV = Dotenv.configure().ignoreIfMissing().load();

    private ConfigManager() { }

    public static String baseUrl() {
        return value("TRIPSTACK_BASE_URL", "https://tripstack.doomple.com");
    }
    public static String email() {
        return required("TRIPSTACK_EMAIL");
    }
    public static String password() {
        return required("TRIPSTACK_PASSWORD");
    }
    public static String employeeId() {
        return "1030";
    }

    private static String required(String name) {
        String value = value(name, null);
        if (value == null || value.isBlank()) throw new IllegalStateException("Missing environment variable: " + name);
        return value;
    }

    private static String value(String name, String fallback) {
        String systemValue = System.getenv(name);
        if (systemValue != null && !systemValue.isBlank()) return systemValue;
        String dotenvValue = DOTENV.get(name);
        return dotenvValue == null || dotenvValue.isBlank() ? fallback : dotenvValue;
    }
}
