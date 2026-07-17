package builders;


import models.LoginRequest;


public final class LoginRequestBuilder {


    private String email;

    private String password;



    private LoginRequestBuilder() {
    }



    public static LoginRequestBuilder login(){

        return new LoginRequestBuilder();

    }



    public LoginRequestBuilder email(String email){

        this.email = email;

        return this;

    }



    public LoginRequestBuilder password(String password){

        this.password = password;

        return this;

    }



    public LoginRequest build(){

        return new LoginRequest(
                email,
                password
        );

    }

}