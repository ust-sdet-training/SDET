package com.assessment.pos.client;

import com.assessment.pos.model.ErrorResponse;
import com.assessment.pos.model.ProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CatalogApiClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String baseUrl;

    public CatalogApiClient(String baseUrl) {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
        this.baseUrl = baseUrl.endsWith("/")
                ? baseUrl.substring(0, baseUrl.length() - 1)
                : baseUrl;
    }

    public ProductResponse getProduct(String sku) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + "/catalog/" + sku))
                .GET()
                .build();

        try {
            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), ProductResponse.class);
            }

            if (response.statusCode() == 404) {
                ErrorResponse error =
                        objectMapper.readValue(response.body(), ErrorResponse.class);
                throw new IllegalArgumentException(error.getMessage());
            }

            throw new IllegalStateException(
                    "Unexpected response status: " + response.statusCode());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Request was interrupted", e);

        } catch (IOException e) {
            throw new IllegalStateException("Call to catalog service failed", e);
        }
    }
}