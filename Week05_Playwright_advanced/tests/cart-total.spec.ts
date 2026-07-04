import { expect, test } from "../fixtures/evidence";

test.describe("cart total example", () => {

    // simple: check cart total with float math (flaky)
    test.only("flaky test", async ({ evidence }) => {
        const itemPrice = 333.33;
        const tax = itemPrice * 0.05;
        const total = itemPrice + tax;

        evidence.cartResponse = {
            itemPrice,
            tax,
            total,
        };
        evidence.diagnosis = "Flaky cart total: float money math can be off by a paisa.";

        expect(total).toBe(349.99);
    });

    // simple: check cart total using integer paise (fixed)
    test("flaky test - fix", async ({ evidence }) => {
        const itemPricePaise = 33333;
        const taxPaise = Math.round(itemPricePaise * 5 / 100);
        const totalPaise = itemPricePaise + taxPaise;

        evidence.cartResponse = {
            itemPricePaise,
            taxPaise,
            totalPaise,
        };
        evidence.diagnosis = "Fixed cart total: integer paise keeps money exact.";

        expect(totalPaise).toBe(35000);
    });

});
