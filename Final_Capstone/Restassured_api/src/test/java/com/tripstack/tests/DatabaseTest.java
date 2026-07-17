package com.tripstack.tests;


import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


import com.tripstack.db.BookingDB;



public class DatabaseTest {


    @Test

    void validateNamespace()
            throws Exception{


        String emp=
                BookingDB.getEmployee(
                        "TS-1020-0001"
                );


        assertEquals(
                "1020",
                emp
        );


    }



}