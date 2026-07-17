package models;


public class BookingRequest {


    private String flightId;
    private String passengerId;
    private String seatNumber;


    public BookingRequest(
            String flightId,
            String passengerId,
            String seatNumber
    ){

        this.flightId=flightId;
        this.passengerId=passengerId;
        this.seatNumber=seatNumber;

    }


    public String getFlightId(){
        return flightId;
    }


    public String getPassengerId(){
        return passengerId;
    }


    public String getSeatNumber(){
        return seatNumber;
    }

}