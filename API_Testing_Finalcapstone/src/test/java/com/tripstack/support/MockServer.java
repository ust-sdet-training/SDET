// package com.tripstack.support;

// import java.io.IOException;
// import java.io.OutputStream;
// import java.net.InetSocketAddress;
// import java.nio.charset.StandardCharsets;

// import com.sun.net.httpserver.HttpExchange;
// import com.sun.net.httpserver.HttpServer;

// public class MockServer {
//     private HttpServer server;

//     public void start() throws IOException {
//         server = HttpServer.create(new InetSocketAddress(0), 0);
//         server.createContext("/flights", this::handleFlights);
//         server.createContext("/bookings", this::handleBookings);
//         server.start();
//     }

//     public String getBaseUrl() {
//         return "http://localhost:" + server.getAddress().getPort();
//     }

//     public void stop() {
//         if (server != null) {
//             server.stop(0);
//         }
//     }

//     private void handleFlights(HttpExchange exchange) throws IOException {
//         String response = "[{\"flightId\":\"FL123\",\"status\":\"available\"}]";
//         writeJson(exchange, 200, response);
//     }

//     private void handleBookings(HttpExchange exchange) throws IOException {
//         String response = "{\"status\":\"success\",\"message\":\"booking created\"}";
//         writeJson(exchange, 201, response);
//     }

//     private void writeJson(HttpExchange exchange, int statusCode, String body) throws IOException {
//         byte[] payload = body.getBytes(StandardCharsets.UTF_8);
//         exchange.getResponseHeaders().add("Content-Type", "application/json");
//         exchange.sendResponseHeaders(statusCode, payload.length);
//         try (OutputStream outputStream = exchange.getResponseBody()) {
//             outputStream.write(payload);
//         }
//     }
// }
