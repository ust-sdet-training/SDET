package com.apitesting.data.builder;
import com.apitesting.data.testUser;

import com.apitesting.client.AuthClient;
import com.apitesting.data.model.Customer;
import com.apitesting.data.secrets.*;

public class CustomerBuilder {
    private String name;

    public static CustomerBuilder aCustomer() {
        return new CustomerBuilder();
    }

    public CustomerBuilder named(String name) {
        this.name = name;
        return this;
    }

    public Customer build(){
        String email = testUser.EMAIL();
        String password = testUser.PASSWORD();
        return new Customer(name,email,password);
    }

    public String loginAndGetToken() {
        Customer customer = CustomerBuilder.aCustomer().named(name).build();
        return new AuthClient().login(customer.email(), customer.password()).jsonPath().getString("token");
    }
}