package com.apitesting.support;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SecretMaskingFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification req, FilterableResponseSpecification res, FilterContext ctx) {
        if (req.getHeaders().hasHeaderWithName("Authorization")) {
            System.out.println("Authorization: Bearer ***MASKED***");
        }
        return ctx.next(req, res);
    }
}