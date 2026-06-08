import {expect,Page,Locator} from '@playwright/test';
import { SearchPage } from './SearchPage';

export class CartPage
{
    constructor(private readonly page:Page){};
  
    countProduct = ()=> this.page.getByTestId('cart-count');
    totalCartPrice =()=> this.page.getByTestId('order-total');

  async open():Promise<void> {
    await this.page.goto("/cart");
  }
}
