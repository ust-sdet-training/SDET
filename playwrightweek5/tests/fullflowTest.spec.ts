import { testCases } from "../fixtures/fullflowTestCases";
import {expect, test} from "../fixtures/artifactFixture";
import { addOrder } from "../src/api/addOrder";
import { refundOrder } from "../src/api/refundOrder";
import { getIDKEY } from "../src/utils/getIDKey";
import { apiCheck } from "../src/api/apicheck";
import { getLedger } from "../src/api/getLedger";

test.describe("contains all the tests of day5 code",()=>{

    for (let c of testCases) {
    test(c.name+": Checking the full flow",async({page,request,artifacts,log})=>{

        const order = await addOrder(request,c.sku,c.pname,c.unitprice,c.qty,c.taxpaisa);
        artifacts.order = order;
        log.info("Placed a order");
        expect(order.ok()).toBeTruthy();
        const orderId = (await order.json()).id;

        const checkResponse =await apiCheck(request,c.sku,orderId,c.refundqty);
        expect((await checkResponse.json()).verdict).toBe(c.checkExpectedStatus);
 
        const key = getIDKEY();
        const refund = await refundOrder(request,orderId,key,c.refundqty,c.sku);
        const r= await refund.json();
        artifacts.refundCheck = refund;
        if(refund.ok()){
        expect(r.taxShares.slice(0, c.refundqty).reduce((a, x) => a + x, 0)).toBe(r.taxPaise);
        expect(((await order.json()).lines[0].unitPaise)*c.refundqty).toBe(r.amountPaise-r.taxPaise);
        expect((await refund.json()).orderStatus).toBe(c.expectedstatus);
        }else{
            expect(refund.status()).toBe(422);
            expect(r.reason).toBe(c.checkExpectedStatus);

        }

    })
}
        
    
});

