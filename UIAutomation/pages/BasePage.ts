import { Page, Locator, TestInfo, expect } from "@playwright/test";
import { AppLogger } from "../src/logger";

export class BasePage {
  constructor(
    protected readonly page: Page,
    protected readonly log: AppLogger
  ) {}

  // Navigation

  async goto(url: string) {
    this.log.info(`Navigating to: ${url}`);
    await this.page.goto(url);
  }

  async reload() {
    this.log.info("Reloading page");
    await this.page.reload();
  }

  async back() {
    this.log.info("Navigating back");
    await this.page.goBack();
  }

  async forward() {
    this.log.info("Navigating forward");
    await this.page.goForward();
  }

  async refresh() {
    this.log.info("Refreshing page");
    await this.page.reload();
  }

  // Click

  async click(locator: Locator) {
    this.log.info("Clicking element");
    await locator.click();
  }

  async doubleClick(locator: Locator) {
    this.log.info("Double clicking element");
    await locator.dblclick();
  }

  async rightClick(locator: Locator) {
    this.log.info("Right clicking element");
    await locator.click({ button: "right" });
  }

  async hover(locator: Locator) {
    this.log.info("Hovering over element");
    await locator.hover();
  }

  async forceClick(locator: Locator) {
    this.log.info("Force clicking element");
    await locator.click({ force: true });
  }

  // Input

  async fill(locator: Locator, text: string) {
    this.log.info(`Entering text: ${text}`);
    await locator.fill(text);
  }

  async type(locator: Locator, text: string) {
    this.log.info(`Typing text: ${text}`);
    await locator.pressSequentially(text);
  }

  async clear(locator: Locator) {
    this.log.info("Clearing input");
    await locator.clear();
  }

  async press(locator: Locator, key: string) {
    this.log.info(`Pressing key: ${key}`);
    await locator.press(key);
  }

  // Dropdown

  async selectByValue(locator: Locator, value: string) {
    this.log.info(`Selecting value: ${value}`);
    await locator.selectOption(value);
  }

  async selectByLabel(locator: Locator, label: string) {
    this.log.info(`Selecting label: ${label}`);
    await locator.selectOption({ label });
  }

  async selectByIndex(locator: Locator, index: number) {
    this.log.info(`Selecting index: ${index}`);
    await locator.selectOption({ index });
  }

  // Checkbox & Radio

  async check(locator: Locator) {
    this.log.info("Checking checkbox");
    await locator.check();
  }

  async uncheck(locator: Locator) {
    this.log.info("Unchecking checkbox");
    await locator.uncheck();
  }

  // Waits

  async waitForVisible(locator: Locator) {
    this.log.info("Waiting for element to be visible");
    await locator.waitFor({ state: "visible" });
  }

  async waitForHidden(locator: Locator) {
    this.log.info("Waiting for element to be hidden");
    await locator.waitFor({ state: "hidden" });
  }

  async waitForEnabled(locator: Locator) {
    this.log.info("Waiting for element to be enabled");
    await expect(locator).toBeEnabled();
  }

  async waitForDisabled(locator: Locator) {
    this.log.info("Waiting for element to be disabled");
    await expect(locator).toBeDisabled();
  }

  async wait(milliseconds: number) {
    this.log.info(`Waiting ${milliseconds} ms`);
    await this.page.waitForTimeout(milliseconds);
  }

  async waitForLoad() {
    this.log.info("Waiting for page load");
    await this.page.waitForLoadState("load");
  }

  async waitForNetworkIdle() {
    this.log.info("Waiting for network idle");
    await this.page.waitForLoadState("networkidle");
  }

  // Assertions

  async verifyVisible(locator: Locator) {
    this.log.info("Verifying element is visible");
    await expect(locator).toBeVisible();
  }

  async verifyHidden(locator: Locator) {
    this.log.info("Verifying element is hidden");
    await expect(locator).toBeHidden();
  }

  async verifyText(locator: Locator, text: string) {
    this.log.info(`Verifying text: ${text}`);
    await expect(locator).toHaveText(text);
  }

  async verifyContainsText(locator: Locator, text: string) {
    this.log.info(`Verifying text contains: ${text}`);
    await expect(locator).toContainText(text);
  }

  async verifyURL(url: string) {
    this.log.info(`Verifying URL: ${url}`);
    await expect(this.page).toHaveURL(url);
  }

  async verifyTitle(title: string) {
    this.log.info(`Verifying title: ${title}`);
    await expect(this.page).toHaveTitle(title);
  }

  // Get Data

  async getText(locator: Locator) {
    this.log.info("Getting text");
    return await locator.textContent();
  }

  async getInnerText(locator: Locator) {
    this.log.info("Getting inner text");
    return await locator.innerText();
  }

  async getValue(locator: Locator) {
    this.log.info("Getting input value");
    return await locator.inputValue();
  }

  async getAttribute(locator: Locator, name: string) {
    this.log.info(`Getting attribute: ${name}`);
    return await locator.getAttribute(name);
  }

  async getCount(locator: Locator) {
    this.log.info("Getting locator count");
    return await locator.count();
  }

  async getTitle() {
    this.log.info("Getting page title");
    return await this.page.title();
  }

  async getURL() {
    this.log.info("Getting current URL");
    return this.page.url();
  }

  // Screenshot

  async takeScreenshot(testInfo: TestInfo, name: string) {
    this.log.info(`Capturing screenshot: ${name}`);
    await testInfo.attach(name, {
      body: await this.page.screenshot({ fullPage: true }),
      contentType: "image/png",
    });
  }

  async saveScreenshot(path: string) {
    this.log.info(`Saving screenshot to: ${path}`);
    await this.page.screenshot({
      path,
      fullPage: true,
    });
  }

  // Keyboard

  async pressKey(key: string) {
    this.log.info(`Pressing keyboard key: ${key}`);
    await this.page.keyboard.press(key);
  }

  async pressEnter() {
    this.log.info("Pressing Enter");
    await this.page.keyboard.press("Enter");
  }

  async pressTab() {
    this.log.info("Pressing Tab");
    await this.page.keyboard.press("Tab");
  }

  // Mouse

  async scrollToBottom() {
    this.log.info("Scrolling to bottom");
    await this.page.evaluate(() =>
      window.scrollTo(0, document.body.scrollHeight)
    );
  }

  async scrollToTop() {
    this.log.info("Scrolling to top");
    await this.page.evaluate(() => window.scrollTo(0, 0));
  }

  async scrollIntoView(locator: Locator) {
    this.log.info("Scrolling element into view");
    await locator.scrollIntoViewIfNeeded();
  }

  frame(name: string) {
    this.log.info(`Getting frame: ${name}`);
    return this.page.frame({ name });
  }

  async acceptAlert() {
    this.log.info("Accepting alert");
    this.page.once("dialog", dialog => dialog.accept());
  }

  async dismissAlert() {
    this.log.info("Dismissing alert");
    this.page.once("dialog", dialog => dialog.dismiss());
  }

  async close() {
    this.log.info("Closing page");
    await this.page.close();
  }
}