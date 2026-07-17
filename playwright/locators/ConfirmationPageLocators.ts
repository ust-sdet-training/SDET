import { Page,Locator } from "@playwright/test";

export class ConfirmationPageLocators{
  static bookStatus(page:Page):Locator{
     return page.locator("//*[@data-id='state']");
  }
}