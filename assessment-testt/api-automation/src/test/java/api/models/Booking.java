package api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Booking {
    public String id;
    public String pnr;
    public String empId;
    public String journeyType;
    public String inventoryId;
    public String state;
    public List<String> seatIds;
}
