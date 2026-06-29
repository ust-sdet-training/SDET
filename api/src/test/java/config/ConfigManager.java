package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private final Properties properties;

    public ConfigManager() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new IllegalStateException("config.properties not found");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("baseUrl");
    }
}
