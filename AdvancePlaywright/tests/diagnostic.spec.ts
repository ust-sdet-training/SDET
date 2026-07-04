import { test, expect } from "../fixtures/diagnostic.fixture";

const API_URL = process.env.API_BASE_URL || "http://localhost:4000";

const headers = {
    Authorization: "Bearer demo-token-1-customer"
};

test.describe("Diagnostics and Logging", () => {

    test("Verify checkout journey logs are generated", async ({ request, log, correlationId }) => {

        const cartSession = `cart-${Date.now()}`;

        log.info(`Test Started : ${correlationId}`);

        const addItem = await request.post(`${API_URL}/api/cart/items`, {

            headers: {
                ...headers,
                "X-Cart-Session": cartSession
            },

            data: {
                productId: 101,
                quantity: 1,
                size: "UK 9",
                color: "Black",
                fulfilment: "Home delivery"
            }

        });

        expect(addItem.status()).toBe(201);

        log.info("Product added to cart");

        const cart = await request.get(`${API_URL}/api/cart`, {

            headers: {
                ...headers,
                "X-Cart-Session": cartSession
            }

        });

        expect(cart.status()).toBe(200);

        const cartBody = await cart.json();

        expect(cartBody.items.length).toBe(1);

        log.info("Cart verified successfully");

        log.info(`Test Finished : ${correlationId}`);

    });

});