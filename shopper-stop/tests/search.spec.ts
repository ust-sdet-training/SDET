
import {test,expect} from "@playwright/test";
import { SearchPage } from "../pages/SearchPage";


test("Search for a product which is not present",async({page})=>{
   await page.goto('https://www.shoppersstop.com/');
  await page.getByRole('textbox', { name: 'Search' }).click();
  await page.getByRole('textbox', { name: 'Search' }).fill('lahari gandla');
  await page.getByRole('textbox', { name: 'Search' }).press('Enter');
  await page.getByText('No Result Found');
  await page.getByText('Looks like we couldn\'t find a');
 
})