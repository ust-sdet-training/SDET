package clients;


import io.restassured.response.Response;
import spec.RequestSpec;
import utils.ConfigReader;


import static io.restassured.RestAssured.given;


public class FlightClient {


    public Response searchFlights(String token){


        return given()

                .spec(RequestSpec.request())

                .header(
                        "Authorization",
                        "Bearer " + token
                )


                .queryParam(
                        "from",
                        ConfigReader.get("flight.from")
                )

                .queryParam(
                        "to",
                        ConfigReader.get("flight.to")
                )


                .when()

                .get(
                        ConfigReader.get("flight.endpoint")
                )


                .then()

                .log()
                .all()

                .extract()

                .response();

    }


}