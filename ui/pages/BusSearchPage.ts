import { APP_BASE_URL, APP_PATHS } from '../constants';
import { BasePage } from './BasePage';

export class BusSearchPage extends BasePage {
  async goto() {
    this.logger?.info('Search step started');
    await this.page.goto(`${APP_BASE_URL}${APP_PATHS.busSearch}`);
  }

  async search(from: string, to: string, date: string) {
    await this.page.getByRole('combobox', { name: 'From' }).fill(from);
    await this.page.getByRole('combobox', { name: 'To' }).fill(to);
    await this.page.getByRole('textbox', { name: 'Date of journey' }).fill(date);
    await this.page.getByRole('button', { name: /search buses/i }).click();
    this.logger?.info('Search completed');
  }
}
