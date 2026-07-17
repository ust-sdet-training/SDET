package models;


public record LoginResponse(

        String token,

        String empId,

        String role,

        String displayName

) {
}