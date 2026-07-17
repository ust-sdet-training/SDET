import { Page } from '@playwright/test';
import type { PassengerDetails } from '../types/booking';

export class PassengerPage {
  constructor(private readonly page: Page) {}

  private readonly firstNameBox = (seat: string) =>
    this.page.getByLabel(new RegExp(`first name.*seat ${seat}`, 'i'));
  private readonly lastNameBox = (seat: string) =>
    this.page.getByLabel(new RegExp(`last name.*seat ${seat}`, 'i'));
  private readonly ageBox = (seat: string) => this.page.getByLabel(new RegExp(`age.*seat ${seat}`, 'i'));
  private readonly emailBox = () => this.page.getByLabel(/email/i);
  private readonly phoneBox = () => this.page.getByLabel(/phone/i);
  private readonly continueButton = () => this.page.getByRole('button', { name: /continue to payment/i });

  async fillDetails(details: PassengerDetails, seat: string): Promise<void> {
    await this.firstNameBox(seat).fill(details.firstName);
    await this.lastNameBox(seat).fill(details.lastName);
    await this.ageBox(seat).fill(details.age);
    await this.emailBox().fill(details.email);
    await this.phoneBox().fill(details.phone);
  }

  async continueToPayment(): Promise<void> {
    await this.continueButton().click();
  }
}
