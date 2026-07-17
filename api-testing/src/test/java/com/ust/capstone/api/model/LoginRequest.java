package com.ust.capstone.api.model;

public record LoginRequest(
    String email,
    String password
) {
}