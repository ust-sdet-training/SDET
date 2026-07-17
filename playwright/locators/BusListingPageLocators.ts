import { Page,Locator } from "@playwright/test";

export class BusListingPageLocators{
  static selectSeatSection(page:Page):Locator{
     return page.locator('[data-sort="seats"]');
  } 

  static getTheSeaterBus(page:Page):Locator{
     return page.locator('[data-kind="seater"]');
  }
}