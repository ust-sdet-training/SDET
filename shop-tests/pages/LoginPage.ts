import { expect, type Page } from "@playwright/test";

export class LoginPage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto("/login");
  }

  async login(email: string, password: string) {
    await this.page.getByLabel("Email").fill(email);
    await this.page.getByLabel("Password").fill(password);
    await this.page.getByRole("button", { name: "Sign in" }).click();
    await expect(this.page).toHaveURL('/home')
  }

   async inlogin(email: string, password: string) {
    await this.page.getByLabel("Email").fill(email);    
    await this.page.getByLabel("Password").fill(password);
    await this.page.getByRole("button", { name: "Sign in" }).click();
    await expect(this.page.getByRole("alert")).toContainText(/invalid credentials/i);
    await expect(this.page).toHaveURL('/login')
  }

  async expectError(message: string) {
    await expect(this.page.getByRole("alert")).toContainText(message);
  }
}
