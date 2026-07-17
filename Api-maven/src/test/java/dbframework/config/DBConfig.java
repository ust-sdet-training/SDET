package dbframework.config;

public final class DBConfig {

    private DBConfig() {
    }

    public static final String URL =
            "jdbc:mysql://localhost:3306/tripstack?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Kolkata";

    public static final String USERNAME = "root";

    public static final String PASSWORD = "Hemu@123";
}