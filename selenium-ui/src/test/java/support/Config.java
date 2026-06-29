package support;

public final class Config {

    private Config() {
    }

    public static String baseUrl() {
        return System.getProperty("baseUrl", "https://www.redbus.in/")
                .replaceAll("/$", "");
    }

    public static boolean headless() {
        return Boolean.parseBoolean(
                System.getProperty("headless", "false")
        );
    }

    public static boolean headed() {
        return Boolean.parseBoolean(
                System.getProperty("headed", "true")
        );
    }
}