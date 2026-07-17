package support;


import config.SecretsManager;
import models.LoginRequest;


public final class TestData {


    private TestData() {
        // Prevent object creation
    }



    public static LoginRequest loginUser(){


        return new LoginRequest(

                SecretsManager.email(),

                SecretsManager.password()

        );

    }



    public static String firstInventory(){

        return "FL-COKBOM-51";

    }



    public static String secondInventory(){

        return "FL-BOMDEL-52";

    }



    public static boolean refundable(){

        return true;

    }

}