package client;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CatalogClient {
    private final String baseUrl;

    public CatalogClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public Response getCatalogItem(String sku) {
        return given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .get("/catalog/" + sku)
                .then()
                .extract()
                .response();
    }
}