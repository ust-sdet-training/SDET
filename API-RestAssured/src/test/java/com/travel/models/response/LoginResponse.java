package com.travel.models.response;

public record LoginResponse(String token, String empId, String role, String displayName) { }