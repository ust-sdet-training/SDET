import { test, expect } from '../fixtures/test';
import { testUsers } from '../fixtures/test-users';

test('test', async ({ book, page }) => {
  await book.loginSuccessFlow();

  await book.search('BOM', 'DEL', '2026-08-17');
  await book.selectBus('Kallada Travels');
  await book.selectSeat();
  await book.travellerDetails(testUsers.user.email, testUsers.user.phone);

  await book.payment();
  await book.confirmSeat();
//   await page.getByRole('link', { name: 'Log in' }).click();
//   await page.getByRole('textbox', { name: 'Email' }).click();
//   await page.getByRole('textbox', { name: 'Email' }).fill('bob@tripstack.test');
//   await page.getByRole('textbox', { name: 'Password' }).click();
//   await page.getByRole('textbox', { name: 'Password' }).fill('Password@123');
//   await page.getByRole('button', { name: 'Sign in' }).click();


    
//   await page.getByRole('tab', { name: 'Buses' }).click();
//   await page.getByRole('combobox', { name: 'From' }).click();
//   await page.getByRole('combobox', { name: 'From' }).fill('BOM');
//   await page.getByRole('option', { name: 'Mumbai BOM' }).click();
//   await page.getByRole('combobox', { name: 'To' }).click();
//   await page.getByRole('combobox', { name: 'To' }).fill('DE');
//   await page.getByRole('option', { name: 'Delhi DEL' }).click();
//   await page.getByRole('button', { name: 'Search' }).click();

//   await page.getByLabel('Kallada Travels').getByRole('button', { name: 'Select Seats' }).click();
//   await page.getByRole('button', { name: 'Seat L3 available' }).click();
//   await page.getByRole('button', { name: 'Continue to passenger details'}).click();

//   await page.getByRole('textbox', { name: 'First name (seat L3)' }).click();
//   await page.getByRole('textbox', { name: 'First name (seat L3)' }).fill('Bob');
//   await page.getByRole('textbox', { name: 'Last name (seat L3)' }).click();
//   await page.getByRole('textbox', { name: 'Last name (seat L3)' }).fill('tedd');
//   await page.getByRole('spinbutton', { name: 'Age (seat L3)' }).click();
//   await page.getByRole('spinbutton', { name: 'Age (seat L3)' }).fill('23');
//   await page.getByLabel('Gender (seat L3)').selectOption('male');
//   await page.getByRole('textbox', { name: 'Email' }).click();
//   await page.getByRole('textbox', { name: 'Email' }).fill('bob@tripstack.test');
//   await page.getByRole('textbox', { name: 'Phone number' }).click();
//   await page.getByRole('textbox', { name: 'Phone number' }).fill('9898989898');
//   await page.getByRole('button', { name: 'Continue to payment' }).click();

//   await page.getByRole('textbox', { name: 'Name on card' }).click();
//   await page.getByRole('textbox', { name: 'Name on card' }).fill('Bob Tedd');
//   await page.getByRole('textbox', { name: 'Card number' }).click();
//   await page.getByRole('textbox', { name: 'Card number' }).fill('1234567890123456');
//   await page.getByRole('textbox', { name: 'Expiry' }).click();
//   await page.getByRole('textbox', { name: 'Expiry' }).fill('12/28');
//   await page.getByRole('textbox', { name: 'CVV' }).click();
//   await page.getByRole('textbox', { name: 'CVV' }).fill('244');
//   await page.getByRole('button', { name: 'Pay ₹' }).click();
//   await page.getByText('TS-1002-').click();
//   await page.getByRole('button', { name: 'View my trips' }).click();
});