package utils;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public final class SeatGenerator {


    private SeatGenerator() {
        // Prevent object creation
    }



    private static final List<String> AVAILABLE_SEATS =
            new ArrayList<>();



    static {


        // Generate seats dynamically
        // Example: 1A to 30F

        for (int row = 1; row <= 30; row++) {


            for (char column = 'A'; column <= 'F'; column++) {


                AVAILABLE_SEATS.add(
                        row + String.valueOf(column)
                );

            }

        }

    }





    public static String generateUniqueSeat() {


        if (AVAILABLE_SEATS.isEmpty()) {


            throw new RuntimeException(
                    "No seats available"
            );

        }



        // Shuffle to avoid always selecting same seat

        Collections.shuffle(
                AVAILABLE_SEATS
        );



        // Remove selected seat so same test run
        // will not generate duplicate seats

        return AVAILABLE_SEATS.remove(0);

    }


}