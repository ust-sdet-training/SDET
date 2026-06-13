
package tests;

import base.BaseTest;
import config.DatabaseConfig;
import org.junit.jupiter.api.Test;

public class TestConnection extends BaseTest {

    @Test
    void databaseConnectionTest() {

        var connection = DatabaseConfig.getConnection();

    }
}
 