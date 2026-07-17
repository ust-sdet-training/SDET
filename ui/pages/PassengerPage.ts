import { BasePage } from './BasePage';

export class PassengerPage extends BasePage {
  async fillPassenger(seatId: string, passenger: { firstName: string; lastName: string; age: string; gender: string; email: string; phone: string }) {
    await this.page.locator(`#name-${seatId}`).fill(passenger.firstName);
    await this.page.locator(`#lastname-${seatId}`).fill(passenger.lastName);
    await this.page.locator(`#age-${seatId}`).fill(passenger.age);
    await this.page.locator(`#gender-${seatId}`).selectOption(passenger.gender);
    await this.page.locator('#email').fill(passenger.email);
    await this.page.locator('#phone').fill(passenger.phone);
  }

  async continueToPayment() {
    await this.page.getByRole('button', { name: /continue to payment/i }).click();
    this.logger?.info('Passenger details completed');
  }
}
