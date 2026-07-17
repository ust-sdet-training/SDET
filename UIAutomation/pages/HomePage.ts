import { BasePage } from "./BasePage";

const BASE_URL = "https://tripstack.doomple.com/";

export class HomePage extends BasePage {
  private readonly loginLink = this.page.getByRole("link", { name: "Log in" });
  private readonly emailInput = this.page.getByRole("textbox", { name: "Email" });
  private readonly passwordInput = this.page.getByRole("textbox", { name: "Password" });
  private readonly signInHeading = this.page.getByRole("heading", { name: "Sign in to TripStack" });
  private readonly signInButton = this.page.getByRole("button", { name: "Sign in" });

  async open() {
    await this.goto(BASE_URL);
    await this.captureScreenshot("home-page-loaded");
  }

  async goToLogin() {
    await this.click(this.loginLink, "Log in link");
    await this.expectVisible(this.signInHeading, "Sign in to TripStack heading");
  }

 
  async login(email: string, password: string) {
    await this.click(this.emailInput, "Email field");
    await this.fill(this.emailInput, email, "Email field");
    await this.click(this.passwordInput, "Password field");
    await this.fill(this.passwordInput, password, "Password field", true);
    await this.captureScreenshot("login-form-filled");
    await this.click(this.signInButton, "Sign in button");
  }
}