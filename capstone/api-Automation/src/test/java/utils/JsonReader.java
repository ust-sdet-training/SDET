package utils;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.InputStream;


public class JsonReader {


    private static JsonNode data;


    static {

        try {

            ObjectMapper mapper =
                    new ObjectMapper();


            InputStream input =
                    JsonReader.class
                            .getClassLoader()
                            .getResourceAsStream(
                                    "testdata.json"
                            );


            data =
                    mapper.readTree(input);


        }catch(Exception e){

            throw new RuntimeException(
                    "Cannot read test data"
            );

        }

    }


    public static JsonNode getData(String key){

        return data.get(key);

    }

}