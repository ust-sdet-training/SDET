import{expect,test} from '@playwright/test';
import { getBooking } from '../databse/booking';
test('return journey',async({page})=>{

  await page.goto('https://tripstack.doomple.com/login');
  await page.getByRole('textbox', { name: 'Email' }).fill("erin@tripstack.test");
  await page.getByRole('textbox', { name: 'Password' }).fill("Password@123");
  await page.getByRole('button', { name: 'Sign in' }).click();
  const checkoutStart = performance.now();
  await page.getByRole('link', { name: 'Buses' }).click();
  await page.getByRole('combobox', { name: 'From' }).fill("BOM");
    await page.getByRole('combobox', { name: 'To' }).fill("PUN");
    await page.getByRole('textbox', { name: 'Date of journey' }).fill("2026-07-20");
    await page.getByRole('button', { name: 'Search Buses' }).click();
    await page.getByRole('button', { name: 'Select Seats' }).click();
    await page.locator("[data-state='available']").first().click();
    await page.getByRole('button', { name: 'Continue to passenger details' }).click();

    await page.getByRole('textbox', { name: 'First name' }).fill("Erin");
    await page.getByRole('textbox', { name: 'Last name' }).fill("Tripstack");
    await page.getByRole('spinbutton', { name: 'Age' }).fill("22");
    await page.getByRole('textbox', { name: 'Email' }).fill("erin@tripstack.test");
    await page.getByRole('textbox', { name: 'Phone number' }).fill("1234567890");
    await page.getByRole('button', { name: 'Continue to payment' }).click(); 
await page.getByRole('textbox', { name: 'Name on card' }).fill("Erin Tripstack");
await page.getByRole('textbox', { name: 'Card number' }).fill("4111111111111111");
await page.getByRole('textbox', { name: 'Expiry' }).fill("1233");
await page.getByRole('textbox', { name: 'CVV' }).fill("123");
await page.getByRole('button', { name: 'Pay ₹472.50' }).click();
const holdExpired = page.getByText('HOLD_EXPIRED');

if (await holdExpired.isVisible()) {
    console.log("HOLD_EXPIRED happened");
    console.log(await holdExpired.textContent());
}
else {
    console.log("Hold is valid");
}
const checkoutEnd = performance.now();
const checkoutLatency = checkoutEnd - checkoutStart;
expect(checkoutLatency).toBeLessThan(6000);
console.log(`Checkout latency: ${checkoutLatency} ms`);

  
});
