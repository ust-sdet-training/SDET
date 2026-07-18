package org.sdet.API_Framework.clients;

import io.restassured.response.Response;
import org.sdet.API_Framework.base.BaseAPI;
import org.sdet.API_Framework.constants.Endpoints;
import org.sdet.API_Framework.model.request.BookingRequest;

public class BookingClient extends BaseAPI {

    public Response createBooking(BookingRequest request) {

        return post(
                Endpoints.BOOKINGS,
                request
        );

    }

    public Response confirmBooking(String bookingId) {

        return post(
                Endpoints.CONFIRM_BOOKING,
                "bookingId",
                bookingId,
                null
        );

    }

    public Response cancelBooking(String bookingId) {

        return delete(
                Endpoints.CANCEL_BOOKING,
                "bookingId",
                bookingId
        );

    }

    public Response getBookingByPNR(String pnr) {

        return get(
                Endpoints.BOOKING_BY_PNR,
                "pnr",
                pnr
        );

    }


    public Response getMyBookings() {

        return get(
                Endpoints.MY_BOOKINGS
        );

    }

}