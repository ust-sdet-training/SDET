import { expect, type Page } from "@playwright/test";
import { testUsers } from "../fixtures/test";
export class LoginPage {
  constructor(private readonly page: Page) {}

  async goto() {
    await this.page.goto("/login");
  }

  async check(){
    this.page.goto("/login");
    await expect(this.page.getByRole("heading",{name:"Sign in to Retail Lab"})).toBeVisible();
    await expect(this.page.getByLabel("Email")).toBeVisible();
    await expect(this.page.getByLabel("Password")).toBeVisible();
    await expect(this.page.getByLabel("Country")).toBeVisible();
    await expect(this.page.getByLabel("Remember me")).toBeVisible();
  }
  async login(email: string, password: string) {
    await this.page.getByLabel("Email").fill(email);
    await this.page.getByLabel("Password").fill(password);
    const res = this.page.waitForResponse(
            r => r.url().includes('/login') && 
            r.status() == 200);
    await this.page.getByRole("button", { name: "Sign in" }).click();
    await res;
  }
}
