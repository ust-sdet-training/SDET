package com.api.data;

import java.io.FileInputStream;
import java.util.Properties;

public class Secrets {

    private static final Properties props = new Properties();

    static {
        try {
            props.load(
                    new FileInputStream(
                            "secrets.local.properties"
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return System.getenv(key.toUpperCase())
                != null
                ? System.getenv(key.toUpperCase())
                : props.getProperty(key);
    }
}
