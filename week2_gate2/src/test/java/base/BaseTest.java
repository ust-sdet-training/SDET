package base;

import config.DatabaseConfig;
import org.junit.jupiter.api.BeforeAll;
import services.OrderService;
import support.DbSupport;

public class BaseTest {

    public static DbSupport database;

    protected static OrderService orderService;

    @BeforeAll
    static void setup() {

        database =
                new DbSupport(
                        DatabaseConfig.fromEnvironment()
                );

        orderService =
                new OrderService();
    }
}
