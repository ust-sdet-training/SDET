import { Page,Locator } from "@playwright/test";

export class SearchPageLocators{
  static selectDate(page:Page):Locator{
     return  page.locator("#home-date");   
  } 
}