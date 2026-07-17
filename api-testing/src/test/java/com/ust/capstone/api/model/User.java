package com.ust.capstone.api.model;

public record User(

        long empId,
        String role,
        String email,
        String displayName

) {
}