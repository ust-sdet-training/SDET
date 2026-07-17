import { Page,Locator } from "@playwright/test";

export class SeatPageLocators{
  static selectTheAvailableDeck(page:Page,deck:string):Locator{
     return page.locator(`[data-state="available"][data-seat=${deck}]`);
  }
  
  

  
}