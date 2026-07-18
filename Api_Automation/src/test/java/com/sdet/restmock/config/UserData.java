package com.sdet.restmock.config;
import java.util.List;
import java.util.Map;

public class UserData
{

    public static Map<String,Object> busdetails=Map.of("journeyType", "bus",
        "inventoryId", "BUS-HYDCCU-04",
        "seatIds" , List.of("14A")
        );

    public static Map<String,Object> tamperSeat=Map.of("journeyType", "bus",
            "inventoryId", "BUS-HYDCCU-04",
            "seatIds" , List.of("16A")
    );

//    public static Map wrongPassword=Map.of("username",BaseConfig.TEST_USERNAME,"password",BaseConfig.WRONG_PASSWORD);
}
