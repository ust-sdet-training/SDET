package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UserResponse(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String phone,
        Integer userStatus
) {
}