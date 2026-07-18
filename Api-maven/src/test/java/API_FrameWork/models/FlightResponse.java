package API_FrameWork.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightResponse {
    private String from;
    private String to;
    private String date;
    private int pax;
    @JsonProperty("class")
    private String flightClass;
    private int count;
    private List<Flight> flights;
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getPax() {
        return pax;
    }
    public void setPax(int pax) {
        this.pax = pax;
    }
    public String getFlightClass() {
        return flightClass;
    }
    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
    public List<Flight> getFlights() {
        return flights;
    }
    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Flight {
        private String id;
        @JsonProperty("airline_code")
        private String airlineCode;
        @JsonProperty("airline_name")
        private String airlineName;
        @JsonProperty("flight_no")
        private String flightNo;
        private String origin;
        private String dest;
        @JsonProperty("dep_time")
        private String departureTime;
        @JsonProperty("arr_time")
        private String arrivalTime;
        @JsonProperty("total_paise")
        private int totalPaise;
        public String getId() {
            return id;
        }
        public void setId(String id) {
            this.id = id;
        }
        public String getAirlineCode() {
            return airlineCode;
        }
        public void setAirlineCode(String airlineCode) {
            this.airlineCode = airlineCode;
        }
        public String getAirlineName() {
            return airlineName;
        }
        public void setAirlineName(String airlineName) {
            this.airlineName = airlineName;
        }
        public String getFlightNo() {
            return flightNo;
        }
        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }
        public String getOrigin() {
            return origin;
        }
        public void setOrigin(String origin) {
            this.origin = origin;
        }
        public String getDest() {
            return dest;
        }
        public void setDest(String dest) {
            this.dest = dest;
        }
        public String getDepartureTime() {
            return departureTime;
        }
        public void setDepartureTime(String departureTime) {
            this.departureTime = departureTime;
        }
        public String getArrivalTime() {
            return arrivalTime;
        }
        public void setArrivalTime(String arrivalTime) {
            this.arrivalTime = arrivalTime;
        }
        public int getTotalPaise() {
            return totalPaise;
        }
        public void setTotalPaise(int totalPaise) {
            this.totalPaise = totalPaise;
        }
    }
}