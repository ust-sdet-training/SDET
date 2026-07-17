package sdet.com.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public final class TripStackConfig {
    private static final Map<String, String> DOT_ENV = loadDotEnv();

    public static final String BASE_URL = getOrDefault("TRIPSTACK_BASE_URL", "https://tripstack.doomple.com");
    public static final String LOGIN_EMAIL = getRequired("TRIPSTACK_LOGIN_EMAIL");
    public static final String LOGIN_PASSWORD = getRequired("TRIPSTACK_LOGIN_PASSWORD");
    public static final String FROM = getOrDefault("TRIPSTACK_FROM", "DEL");
    public static final String TO = getOrDefault("TRIPSTACK_TO", "IXC");
    public static final String JOURNEY_DATE = LocalDate.now().plusDays(13).format(DateTimeFormatter.ISO_LOCAL_DATE);
    public static final String BUS_CODE = getOrDefault("TRIPSTACK_BUS_CODE", "BUS-DELIXC-01");
    public static final String SEAT = getOrDefault("TRIPSTACK_SEAT", "L3");
    public static final String OPERATOR = getOrDefault("TRIPSTACK_OPERATOR", "KPN Travels");
    public static final String PAYMENT_AMOUNT = getOrDefault("TRIPSTACK_PAYMENT_AMOUNT", "₹598.50");
    public static final String PASSENGER_NAME = getOrDefault("TRIPSTACK_PASSENGER_NAME", "Peggy");
    public static final String PASSENGER_EMAIL = getOrDefault("TRIPSTACK_PASSENGER_EMAIL", "peggy@tripstack.test");
    public static final String PASSENGER_PHONE = getOrDefault("TRIPSTACK_PASSENGER_PHONE", "9876543210");

    private TripStackConfig() {
    }

    private static String getOrDefault(String key, String defaultValue) {
        String value = firstNonBlank(System.getProperty(key), DOT_ENV.get(key), System.getenv(key));
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private static String getRequired(String key) {
        String value = firstNonBlank(System.getProperty(key), DOT_ENV.get(key), System.getenv(key));
        if (value == null || value.isBlank()) {
            throw new IllegalStateException("Missing required environment variable: " + key);
        }
        return value;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value;
            }
        }
        return null;
    }

    private static Map<String, String> loadDotEnv() {
        Map<String, String> values = new HashMap<>();
        Path envFile = Path.of(System.getProperty("user.dir"), ".env");
        if (!Files.exists(envFile)) {
            return values;
        }

        try (BufferedReader reader = Files.newBufferedReader(envFile, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                int separatorIndex = trimmed.indexOf('=');
                if (separatorIndex <= 0) {
                    continue;
                }
                String key = trimmed.substring(0, separatorIndex).trim();
                String value = trimmed.substring(separatorIndex + 1).trim();
                if ((value.startsWith("\"") && value.endsWith("\"")) || (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                values.put(key, value);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read .env file", e);
        }

        return values;
    }
}
