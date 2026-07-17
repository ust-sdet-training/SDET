package clients;


import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import specs.SpecFactory;


public abstract class BaseClient {


    protected RequestSpecification request(){

        return SpecFactory.getRequestSpec();

    }



    protected RequestSpecification authenticatedRequest(
            String token
    ){

        return SpecFactory.getAuthRequestSpec(token);

    }



    protected Response execute(
            RequestSpecification specification,
            String method,
            String endpoint
    ){


        return switch(method.toUpperCase()){


            case "GET" ->
                    specification
                            .when()
                            .get(endpoint);



            case "POST" ->
                    specification
                            .when()
                            .post(endpoint);



            case "PUT" ->
                    specification
                            .when()
                            .put(endpoint);



            case "DELETE" ->
                    specification
                            .when()
                            .delete(endpoint);



            default ->
                    throw new IllegalArgumentException(
                            "Unsupported HTTP method : "
                                    + method
                    );

        };

    }

}