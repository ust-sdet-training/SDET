package models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record OrderResponse(
        Long id,
        Long petId,
        Integer quantity,
        String status,
        Boolean complete
) {
}