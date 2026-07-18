import { test, expect } from "../fixture/TripStackFixture";
import { Env } from "../config/Env";

test("Resilience - Connection Reset during Payment", async ({ page,log, book }) => {

    await book.login(Env.username, Env.password);

    await book.search(
        Env.from,
        Env.to,
        Env.date
    );

    await book.selectseat();

    await book.enterdetails(
        Env.firstname,
        Env.lastname,
        Env.age,
        Env.email,
        Env.ph_no
    );

    const paymentResponsePromise = page.waitForResponse(response =>
        response.url().includes("/api/bookings/") &&
        response.url().includes("/pay")
    );

    await book.paymentExpectFailure(
        Env.firstname,
        Env.cardnumber,
        Env.exp,
        Env.cvv
    );

    const paymentResponse = await paymentResponsePromise;

    const responseBody = await paymentResponse.json();

    console.log("Status :", paymentResponse.status());
    console.log("Response :", responseBody);
    log.info("payment status is being print")
    expect(paymentResponse.status()).toBe(502);
    log.info("expect the payment status to be 502")
    expect(responseBody.error).toBe("GATEWAY_UNAVAILABLE");

});