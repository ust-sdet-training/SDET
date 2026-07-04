import {test,expect } from "../fixtures/diagnostic-test-evidence";

test("Login  flacky", async ({page,log,evidence}) => {
    log.info("Login to the debug-lab")
     await page.goto("/debug-lab");
    log.info("click the Refresh cart Button"); 
 
        await page.getByRole("button", { name: "Refresh cart total" }).click();
    log.info("Validate the cart Total");  
    evidence.cartTotal=  await page.getByTestId("debug-cart-total");
        await expect(page.getByTestId("debug-cart-total")).toHaveText("Rs. 9,197");    
    });
     
 