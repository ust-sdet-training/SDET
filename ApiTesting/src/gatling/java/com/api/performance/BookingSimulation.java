package com.api.performance;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BookingSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://tripstack.doomple.com/api")
                    .acceptHeader("application/json");

    ScenarioBuilder searchBusScenario =
            scenario("Search Bus Performance Test")
                    .exec(
                            http("Search Bus")
                                    .get("/buses")
                                    .queryParam("from", "CCU")
                                    .queryParam("to", "DEL")
                                    .queryParam("date", "2026-07-29")
                                    .check(status().is(200))
                    );
    {
        setUp(
                searchBusScenario.injectOpen(
                        rampUsers(50).during(30)
                )
        ).protocols(httpProtocol);
    }
}