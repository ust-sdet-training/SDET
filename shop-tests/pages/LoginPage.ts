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
  }

  async expectError(message: string) {
    await expect(this.page.getByRole("alert")).toContainText(message);
  }
}


// import { Locator, Page } from '@playwright/test';

// export class LoginPage {
//     readonly page: Page;
//     readonly email: Locator;
//     readonly password: Locator;
//     readonly signInButton: Locator;

//     constructor(page: Page) {
//         this.page = page;

//         this.email = page.getByLabel('Email');
//         this.password = page.getByLabel('Password');

//         this.signInButton = page.getByRole('button', {
//             name: 'Sign in'
//         });
//     }

//     async goto(): Promise<void> {
//         await this.page.goto('/login');
//     }

//     async login(
//         email: string,
//         password: string
//     ): Promise<void> {

//         await this.email.fill(email);
//         await this.password.fill(password);

//         await this.signInButton.click();
//     }
// }
