package support;

public final class TestEnvironment {

    private TestEnvironment() {
    }

    public static String optional(String name, String defaultValue) {

        String value = System.getProperty(name);

        if (value == null || value.isBlank()) {
            value = System.getenv(name);
        }

        return (value == null || value.isBlank())
                ? defaultValue
                : value;
    }

    public static String required(String name) {

        String value = optional(name, null);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Missing required environment variable or system property: "
                            + name
            );
        }

        return value;
    }
}
