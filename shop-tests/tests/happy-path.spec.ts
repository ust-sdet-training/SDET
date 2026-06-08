import { test } from '../fixtures/shop';
import { testUsers } from '../fixtures/testUsers';
import { testProducts } from '../fixtures/testProducts';

test.describe("End to end flow", () => {
    test("end to end", async ({ shop }) => {
        await shop.searchExistingProduct(testProducts.exisitingproduct.name);
        await shop.addToProductToCart();
        await shop.goToCheckout();
        await shop.placeProductOrder();
        await shop.validateOrder();
    });
});