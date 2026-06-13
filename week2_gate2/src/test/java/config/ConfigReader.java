package config;

import io.github.cdimascio.dotenv.Dotenv;

public class ConfigReader {

    private static final Dotenv dotenv =
            Dotenv.configure()
                    .ignoreIfMissing()
                    .load();

    private ConfigReader() {
    }

    public static String get(String key) {

        String value = dotenv.get(key);

        if (value == null || value.isBlank()) {
            throw new RuntimeException(
                    "Missing environment variable : " + key
            );
        }

        return value;
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}