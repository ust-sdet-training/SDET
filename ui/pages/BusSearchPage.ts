import { APP_BASE_URL, APP_PATHS } from '../constants';
import { BasePage } from './BasePage';

export class BusSearchPage extends BasePage {
  async goto() {
    this.logger?.info('Search step started');
    await this.page.goto(`${APP_BASE_URL}${APP_PATHS.busSearch}`);
  }

  async search(from: string, to: string, date: string) {
    const fromField = this.page.getByLabel(/from/i).or(this.page.getByPlaceholder(/from/i)).first();
    const toField = this.page.getByLabel(/to/i).or(this.page.getByPlaceholder(/to/i)).first();
    const dateField = this.page.getByLabel(/date of journey/i).or(this.page.locator('input[type="date"]')).first();

    await fromField.fill(from);
    await toField.fill(to);
    await dateField.fill(date);
    await this.page.getByRole('button', { name: /search buses/i }).click();
    this.logger?.info('Search completed');
  }
}
