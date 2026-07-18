package API_FrameWork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeatMapResponse(
        @JsonProperty("flight_id") String flightId,
        String layout,
        List<Row> rows) {

    public String firstAvailableSeatId() {
        return rows.stream()
                .flatMap(row -> row.seats().stream())
                .filter(seat -> !seat.occupied())
                .map(Seat::seatId)
                .findFirst()
                .orElse(null);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Row(int row, @JsonProperty("exit_row") boolean exitRow, List<Seat> seats) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Seat(@JsonProperty("seat_id") String seatId, boolean occupied, String col) {
    }
}
