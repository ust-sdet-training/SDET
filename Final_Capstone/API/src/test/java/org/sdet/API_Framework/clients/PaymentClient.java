package org.sdet.API_Framework.clients;

import io.restassured.response.Response;
import org.sdet.API_Framework.base.BaseAPI;
import org.sdet.API_Framework.constants.Endpoints;

public class PaymentClient extends BaseAPI {

    public Response pay(String bookingId) {

        return post(
                Endpoints.PAY_BOOKING,
                "bookingId",
                bookingId,
                null
        );

    }

}