package api.tests;

import api.database.DbSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assumptions;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DbValidationTest {

    @Test
    void validateDbConnectionAndSimpleQuery() throws Exception {
        Assumptions.assumeTrue(DbSupport.isConfigured(), "DB not configured; set DB_URL/DB_USER/DB_PASSWORD to run DB tests");

        try (Connection c = DbSupport.getConnection();
             Statement s = c.createStatement()) {
            ResultSet rs = s.executeQuery("SELECT 1");
            rs.next();
            int v = rs.getInt(1);
            assertThat(v, equalTo(1));
        }
    }
}
