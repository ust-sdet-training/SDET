package api.tests;

import api.database.DbSeeder;
import api.database.DbSupport;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DbSeederTest {

    @Test
    void shouldSeedBookingAndFindByPnr() {
        Assumptions.assumeTrue(DbSupport.isConfigured(), "DB not configured; set DB_URL/DB_USER/DB_PASSWORD to run DB tests");

        DbSeeder.seedConfirmedBusBooking();

        Map<String, Object> booking = DbSupport.findBookingByPnr(DbSeeder.PNR);
        assertThat(booking.get("pnr"), equalTo(DbSeeder.PNR));
        assertThat(booking.get("emp_id"), equalTo(DbSeeder.EMP_ID));
    }
}
