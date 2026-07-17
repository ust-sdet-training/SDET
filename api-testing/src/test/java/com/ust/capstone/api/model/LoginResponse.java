package com.ust.capstone.api.model;

public record LoginResponse(
        String token,
        long empId,
        String role,
        String displayName
) {
}