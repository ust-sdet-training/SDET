package com.gamagate1.model;

import java.util.List;
import java.util.Map;

public class bookdata {
    public static Map<String, Object> booking1 = Map.of(
            "firstname", "Monisha",
            "lastname", "P",
            "totalprice", 1500,
            "depositpaid", true,
            "bookingdates", Map.of(
                    "checkin", "2024-01-01",
                    "checkout", "2024-01-05"
            ),
            "additionalneeds", "Breakfast"
    );

    public static Map<String, Object> bookingUpdated(int bookingId) {
        return Map.of(
                "firstname", "MonishaUpdated",
                "lastname", "P",
                "totalprice", 2000,
                "depositpaid", false,
                "bookingdates", Map.of(
                        "checkin", "2024-02-01",
                        "checkout", "2024-02-10"
                ),
                "additionalneeds", "Lunch"
        );
    }
//    public static Map<String,Object> pet1 = Map.of(
//                                                "id",0,
//                                                "category",Map.of(
//                                                    "id",0,
//                                                    "name","string"
//                                                ),
//                                                "name","doggie",
//                                                "photoUrls",List.of(
//                                                    "string"
//                                                ),
//                                                "tags",List.of(
//                                                    Map.of(
//                                                            "id",0,
//                                                            "name","string"
//                                                        )
//                                                ),
//                                                "status","available"
//                                            );
//    public static Map<String,Object> pet1Updated(long petId){
//        return Map.of(
//                                                "id",petId,
//                                                "category",Map.of(
//                                                    "id",122,
//                                                    "name","string"
//                                                ),
//                                                "name","doggie",
//                                                "photoUrls",List.of(
//                                                    "string"
//                                                ),
//                                                "tags",List.of(
//                                                    Map.of(
//                                                            "id",0,
//                                                            "name","string"
//                                                        )
//                                                ),
//                                                "status","available"
//                                            );
//                                        }
}

