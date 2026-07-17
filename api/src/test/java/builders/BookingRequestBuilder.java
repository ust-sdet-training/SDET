package builders;


import models.BookingRequest;

import java.util.List;


public final class BookingRequestBuilder {


    private String journeyType;

    private String inventoryId;

    private List<String> seatIds;

    private boolean refundable;



    private BookingRequestBuilder(){
    }



    public static BookingRequestBuilder booking(){

        return new BookingRequestBuilder();

    }



    public BookingRequestBuilder journeyType(
            String journeyType
    ){

        this.journeyType = journeyType;

        return this;

    }



    public BookingRequestBuilder inventoryId(
            String inventoryId
    ){

        this.inventoryId = inventoryId;

        return this;

    }



    public BookingRequestBuilder seatIds(
            List<String> seatIds
    ){

        this.seatIds = seatIds;

        return this;

    }



    public BookingRequestBuilder refundable(
            boolean refundable
    ){

        this.refundable = refundable;

        return this;

    }



    public BookingRequest build(){


        return new BookingRequest(

                journeyType,

                inventoryId,

                seatIds,

                refundable

        );

    }

}