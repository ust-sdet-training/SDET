import { Page } from 'playwright';
import {test,expect} from '../fixtures/diagnostic-test-evidence';
import { getCartTotal } from '../src/cartResponse';
import {cases} from '../support/cases';
import {refunds} from '../src/refunds';
import { orderItem } from '../src/orderItem';
import { request } from 'node:http';
import {convertTo} from '../src/paisa';
 
async function openCleanPos(page: Page) {
        await page.goto("/pos");
        await page.waitForLoadState("networkidle");
    }
test.describe('Week 5 Day 6 Assessment',()=>{
   test('Cart Total Off by Paisa',async({log,request,evidence})=>{
      log.info("Getting the Cart Response")
      const resp = await getCartTotal(request,650);
      evidence.response = resp;
      const r = await resp.json();
      log.info("Comparing the float total");
      let total = "9197";
      
      log.info("Converting float to integer paisa")
      let total_as_Integer = await convertTo(total)
      console.log(total_as_Integer);
      evidence.total=total;
      log.info("validate the total is equal to the response total");  
      expect(total_as_Integer).toBe(r.total);
    
   })

   test('Offile Test Before load',async({page,log,context,evidence})=>{
    
    log.info("Opening the POS Page");

   await openCleanPos(page);

    log.info("Setting Offline as false");
   await context.setOffline(false);


    log.info("Validating with navigator.onLine");
   evidence.validationForOnline = await page.evaluate(() => navigator.onLine); 
    await expect.poll(() => page.evaluate(() => navigator.onLine))
        .toBe(true);

    log.info("Load a Queue Sale");    
    await page.getByRole("button",{name:"Queue sale"}).click();


    log.info("Outbox count 0 since application is in Online")
    evidence.error = await page.getByTestId("outbox-count");

    await expect(page.getByTestId("outbox-count")).toHaveText("0");

});

const p = cases.p_approved;
  test('Refunds post Twice',async({request,log,evidence})=>{
    log.info("Posting a Refund order")
           const  r = await orderItem(request,p.taxpaisa,p.sku,p.name,p.unitprice,p.qty);
       evidence.orderItem = r;

    log.info("Making a first refund with Same Id but Unique Idempotency key")   
       const resp = await refunds(request,(await r.json()).id,p.qty,p.sku,crypto.randomUUID());
        evidence.refunds1Response = await resp.json();

      console.log(await resp.json());  
    log.info("Making a Second refund with Same Id but Unique Idempotency key");   

          const resp1 = await refunds(request,(await r.json()).id,p.qty,p.sku,crypto.randomUUID());
        evidence.refunds2response = await resp1.json();
    log.info("Unique Idempotency key protects the Duplicate refund");    
      console.log(await resp1.json());


});
 });
