import {expect, test} from "../fixtures/artifactFixture";
import { testProduct } from "../fixtures/testProduct";
import { testUsers } from "../fixtures/testUsers";
import { addOrder } from "../src/api/addOrder";
import { getLedger } from "../src/api/getLedger";
import { refundOrder } from "../src/api/refundOrder";
import { getIDKEY } from "../src/utils/getIDKey";

test.describe("Week5 Playwright Test", ()=> {

    test("Login flacky", async ({page,log}) => {

        log.info("Assesment Test 1: Login Flacky Started running");
        await page.goto("login");
        log.info("Login Page Loaded");


         await page.getByLabel("Email").fill(testUsers.customer.email);
        log.info("Email entered");

        await page.getByLabel("Password").fill(testUsers.customer.password);
        log.info("Password entered");

        await page.getByRole("button", { name: "Sign in" }).click();
        log.info("Click action happened");
    });

    test("Cart total off by a paisa - Failing Example", async ({ request , log, artifacts}) => {

        const post = testProduct.postProduct;

        const order = await addOrder(request,post.sku,post.pname,post.unitprice,3,post.taxpaisa);
        artifacts.order = order;
        log.info("order added");
        const orderId = (await order.json()).id;

        const key = getIDKEY();

        const refund = await refundOrder(request,orderId,key,1,post.sku);
        artifacts.refundOrder = refund;
        log.info("refund added");

        let r = await refund.json();
        expect(r.lineAmountPaise + r.taxPaise).toBe(r.amountPaise);
        expect(r.taxShares.reduce((a, b) => a + b, 0)).toBe(post.taxpaisa);
        log.info("Cart paisa matches with the fund sended");
        
    });

    test("Scenario 3: Sync test hangs on WebKit", async ({ page, context,log }) => {
        
        await page.goto("/pos");
        log.info("POS page opened");

        await context.setOffline(true);
        log.info("Network is configered to offline");


        await expect(page.getByTestId("network-banner")).toContainText(/Offline/i);
        log.info("Network Banner shown OFFLINE");

        let networkStatus = await page.evaluate(() => navigator.onLine);
        expect(networkStatus).toBe(false);
        log.info("Network Status using navigator.Online shown OFFILNE");

        await context.setOffline(false);
        log.info("Network is configered to Online");

        networkStatus = await page.evaluate(() => navigator.onLine);
        expect(networkStatus).toBe(true);
        log.info("Network Status using navigator.Online shown ONLINE");


        await expect(page.getByTestId("network-banner")).toContainText(/Online/i);
        log.info("Network Banner shown ONLINE");

  });

  test("Scenario 4: Refund posts twice", async ({ log ,request,artifacts}) => {
        const post = testProduct.postProduct;

        const order = await addOrder(request,post.sku,post.pname,post.unitprice,post.qty,post.taxpaisa);
        artifacts.order = order;
        log.info("order added");

        const orderId = (await order.json()).id;

        const key = getIDKEY();

        const refund = await refundOrder(request,orderId,key,post.qty,post.sku);
        artifacts.refund1response = refund;
        log.info("first refund added");

        const refund2 = await refundOrder(request,orderId,key,post.qty,post.sku);
        artifacts.refund2response = refund2;
        log.info("second refund added");
        
        log.info("first refund is created and have 201 status code");
        expect(refund.status()).toBe(201);
        
        log.info("second refund is returned 200 not created");
        expect(refund2.status()).toBe(200);

        const ledger = await getLedger(request, orderId);
        expect((await ledger.json()).refundCount).toBe(1);
        log.info("Refund only added one time check");
  });
    
});