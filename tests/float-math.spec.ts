import { seedOrder, posApiUrl, authHeaders, createRefund} from "../utils/refund-return-helpers";
import { expect, test } from "../fixtures/log-and-evidence";

test("Prorating a partial refund", async ({
    request,
    log,
    evidence,
    page
}) => {

    log.info("Seeding order data");

    const order = await seedOrder(request);

    evidence.order = order;

    log.info("Creating refund request");

    const response = await createRefund(
        request,
        order.id,
        [
            {
                sku: "TEE",
                qty: 1
            }
        ]
    );

    evidence.refundResponse = response;

    log.info("Response converted to JSON body and stored");

    const responseBody = await response.json();

    evidence.refundBody = responseBody;

    expect(responseBody.amountPaise).toBe(34965);

    expect(
        responseBody.taxShares.reduce(
            (a: number, x: number) => a + x,
            0
        )
    ).toBe(4995);

    log.info("Going to Return page for validation");

    await page.goto(
        `/returns?orderId=${order.id}`
    );

    log.info("Checking displayed amount in UI");

    await expect(
        page.getByTestId("refund-total")
    ).toHaveText("₹349.65");

    log.info("Checking refund status");

    await expect(
        page.getByTestId("refund-status")
    ).toHaveText("PARTIALLY_REFUNDED");
});