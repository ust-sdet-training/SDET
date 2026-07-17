package utils;


import com.fasterxml.jackson.databind.ObjectMapper;


public final class JsonUtils {


    private JsonUtils(){
    }



    private static final ObjectMapper MAPPER =
            new ObjectMapper();




    public static String convertToJson(
            Object object
    ){


        try {


            return MAPPER.writeValueAsString(
                    object
            );


        }
        catch(Exception exception){


            throw new RuntimeException(
                    "JSON conversion failed",
                    exception
            );

        }

    }



    public static <T> T convertFromJson(
            String json,
            Class<T> clazz
    ){


        try {


            return MAPPER.readValue(
                    json,
                    clazz
            );


        }
        catch(Exception exception){


            throw new RuntimeException(
                    "JSON parsing failed",
                    exception
            );

        }

    }


}