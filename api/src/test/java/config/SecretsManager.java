package config;


import io.github.cdimascio.dotenv.Dotenv;


public final class SecretsManager {


    private static final Dotenv dotenv =
            Dotenv.configure()
                    .ignoreIfMissing()
                    .load();



    private SecretsManager(){}



    public static String get(String key){


        String value =
                System.getenv(key);



        if(value == null){

            value =
                    dotenv.get(key);

        }



        if(value == null){

            throw new RuntimeException(
                    "Missing secret : "
                            + key
            );

        }


        return value;

    }




    public static String email(){

        return get(
                "TRIPSTACK_EMAIL"
        );

    }



    public static String password(){

        return get(
                "TRIPSTACK_PASSWORD"
        );

    }


}