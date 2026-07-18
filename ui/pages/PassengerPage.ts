import { BasePage } from './BasePage';

export class PassengerPage extends BasePage {
  async fillPassenger(seatId: string, passenger: { firstName: string; lastName: string; age: string; gender: string; email: string; phone: string }) {
    const firstNameField = this.page.locator(`#name-${seatId}, [name="firstName"], [id*="name"]`).first();
    const lastNameField = this.page.locator(`#lastname-${seatId}, [name="lastName"], [id*="lastname"]`).first();
    const ageField = this.page.locator(`#age-${seatId}, [name="age"], [id*="age"]`).first();
    const genderField = this.page.locator(`#gender-${seatId}, [name="gender"], [id*="gender"]`).first();

    await firstNameField.fill(passenger.firstName);
    await lastNameField.fill(passenger.lastName);
    await ageField.fill(passenger.age);
    await genderField.selectOption(passenger.gender).catch(() => undefined);
    await this.page.locator('#email, [name="email"]').first().fill(passenger.email);
    await this.page.locator('#phone, [name="phone"]').first().fill(passenger.phone);
  }

  async continueToPayment() {
    await this.page.getByRole('button', { name: /continue to payment/i }).click();
    this.logger?.info('Passenger details completed');
  }
}
