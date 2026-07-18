import { expect, test } from '@playwright/test';


async function searchBus(page:any, from:string, to:string, date:string) {

    await page.getByRole('combobox', { name: 'From' }).fill(from);
    await page.getByRole('combobox', { name: 'To' }).fill(to);
    await page.getByRole('textbox', { name: 'Date of journey' }).fill(date);
    await page.getByRole('button', { name: 'Search Buses' }).click();
    await page.waitForLoadState('networkidle');
}


async function selectFirstBus(page:any) {

    await page.getByRole('button', { name: 'Select Seats' }).first().click();
    await page.locator("[data-state='available']").first().click();
    await page.getByRole('button', { name: 'Continue to passenger details' }).click();
}


test('simulated return journey booking', async ({ page }) => {
    await page.goto('https://tripstack.doomple.com/login');

    await page.getByRole('textbox', { name: 'Email' }).fill("erin@tripstack.test");

    await page.getByRole('textbox', { name: 'Password' }).fill("Password@123");

    await page.getByRole('button', { name: 'Sign in' }).click();
    await page.getByRole('link', { name: 'Buses' }).click();

    await searchBus(
        page,
        "BOM",
        "PUN",
        "2026-07-20"
    );


    await selectFirstBus(page);
    await page.getByRole('link', { name: 'Buses' }).click();
    await searchBus(
        page,
        "PUN",
        "BOM",
        "2026-07-22"
    );
    await selectFirstBus(page);

    await page.getByRole('textbox', { name: 'First name' }).fill("Erin");
    await page.getByRole('textbox', { name: 'Last name' }).fill("Tripstack");
    await page.getByRole('spinbutton', { name: 'Age' }).fill("22");
    await page.getByRole('textbox', { name: 'Email' }).fill("erin@tripstack.test");
    await page.getByRole('textbox', { name: 'Phone number' }).fill("1234567890");
    await page.getByRole('button', { name: 'Continue to payment' }).click();
    const checkoutStart = performance.now();
    await page.getByRole('textbox', { name: 'Name on card' }).fill("Erin Tripstack");
    await page.getByRole('textbox', { name: 'Card number' }).fill("4111111111111111");
    await page.getByRole('textbox', { name: 'CVV' }).fill("123");
    await page.getByRole('textbox', { name: 'Expiry' }).fill("1233");
    await page.getByRole('button', { name:/Pay/ })
    .click();
    const holdExpired = page.getByText('HOLD_EXPIRED');
    if (await holdExpired.count() > 0) {
        console.log("HOLD_EXPIRED happened");
        console.log(await holdExpired.first().textContent());

    }
    else {console.log("Seat hold is valid");}

    const checkoutEnd = performance.now();
    const checkoutLatency = checkoutEnd - checkoutStart;
    console.log(`Checkout latency: ${checkoutLatency} ms`);
    expect(checkoutLatency).toBeLessThan(550);


});