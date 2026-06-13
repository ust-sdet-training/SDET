package utils;

import java.io.InputStream;
import java.util.Properties;

public final class TestDataReader {

    private static final Properties properties =
            new Properties();

    static {

        try {

            InputStream file =
                    TestDataReader.class
                            .getClassLoader()
                            .getResourceAsStream(
                                    "testdata.properties");

            if (file == null) {
                throw new RuntimeException(
                        "testdata.properties not found");
            }

            properties.load(file);

        } catch (Exception e) {

            throw new RuntimeException(
                    "Failed to load testdata.properties",
                    e);
        }
    }

    private TestDataReader() {
    }

    public static String getCustomerEmail() {
        return properties.getProperty("customer.email");
    }

    public static String getCustomerPassword() {
        return properties.getProperty("customer.password");
    }

    public static String getInvalidEmail() {
        return properties.getProperty("invalid.email");
    }

    public static String getInvalidPassword() {
        return properties.getProperty("invalid.password");
    }
}