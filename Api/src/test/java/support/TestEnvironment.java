package support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class TestEnvironment {
    private static final Map<String, String> ENV = loadEnv();
    private TestEnvironment() {
    }
    public static String getBaseUrl() {
        String baseUrl = ENV.get("BASE_URL");

        if (baseUrl == null || baseUrl.isBlank()) {
            throw new IllegalStateException("BASE_URL not found");
        }
        return baseUrl;
    }
    private static Map<String, String> loadEnv() {
        Map<String, String> env = new HashMap<>();
        Path envFile = Path.of(".env");
        try {
            for (String line : Files.readAllLines(envFile)) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#") || !line.contains("=")) {
                    continue;
                }
                String[] parts = line.split("=", 2);
                env.put(parts[0].trim(), parts[1].trim());
            }
        } catch (IOException e) {
            throw new RuntimeException("Unable to read .env file", e);
        }
        return env;
    }
}