package api.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public final class PropertyReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream stream = PropertyReader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (stream != null) {
                PROPERTIES.load(stream);
            }
        } catch (IOException e) {
            throw new IllegalStateException("Cannot load application.properties", e);
        }

        try {
            File envFile = new File(".env");
            if (!envFile.exists()) {
                // also try classpath
                try (InputStream is = PropertyReader.class.getClassLoader().getResourceAsStream(".env")) {
                    if (is != null) {
                        try (BufferedReader r = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                            r.lines().forEach(PropertyReader::parseAndSet);
                        }
                    }
                }
            } else {
                try (BufferedReader r = new BufferedReader(new InputStreamReader(new FileInputStream(envFile), StandardCharsets.UTF_8))) {
                    r.lines().forEach(PropertyReader::parseAndSet);
                }
            }
        } catch (IOException ignored) {
        }

        System.getenv().forEach((k, v) -> {
            if (k != null && v != null && !v.isBlank()) {
                PROPERTIES.setProperty(k, v);
            }
        });
    }

    private PropertyReader() {
    }

    public static String getProperty(String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (sys != null && !sys.isBlank()) {
            return sys;
        }
        return PROPERTIES.getProperty(key, defaultValue);
    }

    private static void parseAndSet(String line) {
        if (line == null || line.isBlank() || line.trim().startsWith("#")) {
            return;
        }
        String trimmed = line.trim();
        int idx = trimmed.indexOf('=');
        if (idx <= 0 || idx == trimmed.length() - 1) {
            return;
        }
        String key = trimmed.substring(0, idx).trim();
        String value = trimmed.substring(idx + 1).trim();
        if (!key.isBlank()) {
            PROPERTIES.setProperty(key, value);
        }
    }
}
