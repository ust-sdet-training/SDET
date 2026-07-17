package sdet.com.utils;

import java.util.List;
import java.util.Map;

import io.restassured.path.json.JsonPath;

public final class TripStackJsonUtils {

    private TripStackJsonUtils() {
    }

    public static String firstAvailableSeat(String json){

        JsonPath jp=new JsonPath(json);

        List<Map<String,Object>> lower=jp.getList("decks.lower");

        for(Map<String,Object> seat:lower){

            if("available".equals(seat.get("state"))){

                return seat.get("seatId").toString();

            }
        }

        List<Map<String,Object>> upper=jp.getList("decks.upper");

        for(Map<String,Object> seat:upper){

            if("available".equals(seat.get("state"))){

                return seat.get("seatId").toString();

            }
        }

        throw new RuntimeException("No Seat");
    }
}
