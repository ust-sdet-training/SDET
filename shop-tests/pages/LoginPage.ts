import { Page } from "@playwright/test";

export class LoginPage {

  constructor(private readonly page: Page) {}

  async login(
    username: string,
    password: string
  ) {

    const loginResponse =
      this.page.waitForResponse(r => r.url().includes("/login") && r.status() === 200
      );
    await this.page.getByLabel("Email").fill(username);
    await this.page.getByLabel("Password").fill(password);
    await this.page.getByRole("button", { name: "Sign in"}).click();
    await loginResponse;
  }
   async invalidLogin(
    username: string,
    password: string
  ): Promise<void> {

    await this.page
      .getByLabel("Email")
      .fill(username);

    await this.page
      .getByLabel("Password")
      .fill(password);

    await this.page
      .getByRole("button", {
        name: "Sign in"
      })
      .click();
  }

  errorMessage() {
    return this.page.getByRole("alert");
  }
}