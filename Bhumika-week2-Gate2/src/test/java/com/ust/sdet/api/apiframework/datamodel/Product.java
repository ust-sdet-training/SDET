package com.ust.sdet.api.apiframework.datamodel;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown=true)
public record Product (
        int id,
        String name,
        String category,
        int price,
        int stock

){

}



