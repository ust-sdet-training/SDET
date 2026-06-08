import { Page } from "@playwright/test";
 
export class Order {
 
  constructor(private readonly page: Page) {}
 
  orderSuccessMessage() {
    return this.page.getByRole("heading", {name: "Thank you for your order"});
  }
  
  orderConfirmationMessage()
   {return this.page.getByText(/is confirmed/i);}
   
  viewOrdersButton() {
    return this.page.getByRole("button", {
      name: "View orders"
    });
  }
}
 
