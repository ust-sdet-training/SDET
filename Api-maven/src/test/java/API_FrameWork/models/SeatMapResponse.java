package API_FrameWork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeatMapResponse {

    @JsonProperty("flight_id")
    private String flightId;

    private String layout;

    private List<Row> rows;

    public String getFlightId() {
        return flightId;
    }

    public String getLayout() {
        return layout;
    }

    public List<Row> getRows() {
        return rows;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Row {

        private int row;

        @JsonProperty("exit_row")
        private boolean exitRow;

        private List<Seat> seats;

        public int getRow() {
            return row;
        }

        public boolean isExitRow() {
            return exitRow;
        }

        public List<Seat> getSeats() {
            return seats;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Seat {

        @JsonProperty("seat_id")
        private String seatId;

        private boolean occupied;

        private String col;

        public String getSeatId() {
            return seatId;
        }

        public boolean isOccupied() {
            return occupied;
        }

        public String getCol() {
            return col;
        }
    }
}