package dbframework.config;

public final class DBConfig {
    private DBConfig() {
    }

    public static final String HOST = value("DB_HOST", "localhost");
    public static final String PORT = value("DB_PORT", "3306");
    public static final String NAME = value("DB_NAME", "tripstack");
    public static final String USER = value("DB_USER", "root");
    public static final String PASSWORD = value("DB_PASSWORD", "");
    public static final String URL = "jdbc:mysql://" + HOST + ":" + PORT + "/" + NAME;

    private static String value(String name, String defaultValue) {
        String value = System.getProperty(name);
        return value == null || value.isBlank() ? System.getenv().getOrDefault(name, defaultValue) : value;
    }
}
