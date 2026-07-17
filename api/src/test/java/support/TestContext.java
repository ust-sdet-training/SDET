package support;


public final class TestContext {


    private TestContext() {
        // Prevent object creation
    }



    private static String token;


    private static String firstBookingId;


    private static String secondBookingId;


    private static String firstPnr;


    private static String secondPnr;



    public static String getToken(){

        return token;

    }



    public static void setToken(
            String token
    ){

        TestContext.token = token;

    }



    public static String getFirstBookingId(){

        return firstBookingId;

    }



    public static void setFirstBookingId(
            String id
    ){

        firstBookingId = id;

    }



    public static String getSecondBookingId(){

        return secondBookingId;

    }



    public static void setSecondBookingId(
            String id
    ){

        secondBookingId = id;

    }



    public static String getFirstPnr(){

        return firstPnr;

    }



    public static void setFirstPnr(
            String pnr
    ){

        firstPnr = pnr;

    }



    public static String getSecondPnr(){

        return secondPnr;

    }



    public static void setSecondPnr(
            String pnr
    ){

        secondPnr = pnr;

    }


}