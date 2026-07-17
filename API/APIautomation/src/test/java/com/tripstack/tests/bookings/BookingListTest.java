package com.tripstack.tests.bookings;

import com.tripstack.base.BaseTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

@Tag("api")
@Tag("booking")
@Tag("security")
public class BookingListTest extends BaseTest {

    @Test
    void myBookingsAreAllInMyNamespace() {
        // Adjust the JSON path below if your list endpoint wraps results,
        // e.g. "bookings.empId" instead of "empId", depending on the response shape.
        bookingClient.listMyBookings(token).then()
                .statusCode(200)
                .body("empId", everyItem(equalTo("1014")));
    }
}