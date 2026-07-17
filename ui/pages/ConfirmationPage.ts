import { BasePage } from './BasePage';

export class ConfirmationPage extends BasePage {
  async getPNR(): Promise<string | null> {
    const body = await this.page.locator('body').innerText();
    const match = body.match(/TS-\d{4}-\d{4}/);
    return match ? match[0] : null;
  }
}
