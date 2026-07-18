package org.sdet.API_Framework.clients;

import io.restassured.response.Response;
import org.sdet.API_Framework.base.BaseAPI;
import org.sdet.API_Framework.constants.Endpoints;

import java.util.Map;

public class FlightClient extends BaseAPI {

    public Response searchFlights(Map<String, Object> queryParams) {

        return get(
                Endpoints.FLIGHTS,
                queryParams
        );

    }

    public Response getSeatMap(String flightId) {

        return get(
                Endpoints.FLIGHT_SEATS,
                "id",
                flightId
        );

    }


}