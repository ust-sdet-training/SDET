import { expect, Page } from "@playwright/test";
import { testUsers } from "../fixtures/test.ts";
export class LoginPage {
  constructor(private readonly page: Page) {}

//   async goto() {
//     await this.page.goto("/login");
//   }

  async login(email: string, password: string) {
    const loginForm = this.page.getByRole("form", { name: "Login" });
    
    await loginForm.getByLabel("Email").fill(email);
    await loginForm.getByLabel("Password").fill(password);
    await loginForm.getByRole("button", { name: "Sign in" }).click();
  }

  async expectError(message: string) {
    await expect(this.page.getByRole("alert")).toContainText(message);
  }
}
