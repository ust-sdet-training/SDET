package com.travelbooking.models.request;

public class PaymentRequest {

    private String method;

    public PaymentRequest() {
    }

    public PaymentRequest(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}