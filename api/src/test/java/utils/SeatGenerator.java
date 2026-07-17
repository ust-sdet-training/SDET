package utils;


import java.util.HashSet;
import java.util.Random;
import java.util.Set;


public final class SeatGenerator {


    private SeatGenerator(){
        // Prevent object creation
    }



    private static final Set<String> USED_SEATS =
            new HashSet<>();



    private static final String[] SEATS =
            {
                    "10A",
                    "10B",
                    "11A",
                    "12C",
                    "14B",
                    "15C",
                    "16A",
                    "16B",
                    "17C",
                    "18A",
                    "20A",
                    "20B",
                    "21A",
                    "21B"
            };



    public static String generateUniqueSeat(){


        String seat;


        do {


            seat =
                    SEATS[
                            new Random()
                                    .nextInt(SEATS.length)
                            ];


        }
        while(
                USED_SEATS.contains(seat)
        );


        USED_SEATS.add(seat);


        return seat;

    }


}