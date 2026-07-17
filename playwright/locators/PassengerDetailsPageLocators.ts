import { Page,Locator } from "@playwright/test";

export class PassengerDetailsPageLocators{
  static  selectGender(page:Page,deck:string,gender:string){
    const dropdown = page.getByLabel(`Gender (seat ${deck})`);
        dropdown.locator('option').allTextContents();
         dropdown.selectOption({ label: gender });
  } 
}