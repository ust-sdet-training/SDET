package utils;

public class PetPayload {

    public static String createPet(long petId) {

        return """
        {
          "id": %d,
          "name": "Tommy",
          "status": "available"
        }
        """.formatted(petId);
    }

    public static String updatePet(long petId) {

        return """
        {
          "id": %d,
          "name": "TommyUpdated",
          "status": "sold"
        }
        """.formatted(petId);
    }
}