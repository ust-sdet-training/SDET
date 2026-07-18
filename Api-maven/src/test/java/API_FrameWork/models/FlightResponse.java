package API_FrameWork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FlightResponse(
        String from,
        String to,
        String date,
        int pax,
        @JsonProperty("class") String flightClass,
        int count,
        List<Flight> flights) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Flight(
            String id,
            @JsonProperty("airline_code") String airlineCode,
            @JsonProperty("airline_name") String airlineName,
            @JsonProperty("flight_no") String flightNo,
            String origin,
            String dest,
            @JsonProperty("dep_time") String departureTime,
            @JsonProperty("arr_time") String arrivalTime,
            @JsonProperty("total_paise") int totalPaise) {
    }
}
