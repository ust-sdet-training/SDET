
import { test, expect } from "../fixtures/evidence";
import { createOrder,Ledger, generateIdempotencyKey, refundOrder, openReturnsPage, } from "../src/refundFunc";


test.describe('Prorating, Full refund, Verifying over-refund, under refund and money leaks', () => {
 
  test('Refund Scenarios', async ({ request, page, evidence}) => {
    //Create a new refundable order for the scenario.
    const response = await request.post('http://localhost:4000/api/refund-lab/orders', {
      headers: {
        Authorization: 'Bearer demo-token-1-customer',
        'Content-Type': 'application/json'
      },
      data: {
        taxPaise: 4995,
        lines: [
          {
            sku: 'TEE',
            name: 'Training Tee',
            unitPaise: 33300,
            qty: 3
          }
        ]
      }
    });
 
    //Verify order is created and is truthy
    expect(response.ok()).toBeTruthy();
 
    //Extract the order Id
    const order = await response.json();
    const orderId = order.id;
    //Show the evidence of the order created for the refund scenario.
    evidence.order = order;
 
    console.log('Order ID:', orderId);

    // Open the returns page for the newly created order.
    await page.goto(`http://localhost:5173/returns?orderId=${orderId}`);

    //Verify the initial refund UI state before processing any refund.
    await expect(page.getByTestId('refund-line')).toContainText('RETURNABLE');
    await expect(page.getByTestId('refund-line')).toContainText('₹333.00');
    await expect(page.getByTestId('refunded-TEE')).toHaveText('0');

    // Submit a refund request and validate the returned refund values.
    const refund = await request.post('http://localhost:4000/api/refunds', {
      headers: {
        Authorization: 'Bearer demo-token-1-customer',
        'Idempotency-Key': `refund-${Date.now()}`,
        'Content-Type': 'application/json'
      },
      data: {
        orderId,
        lines: [
          {
            sku: 'TEE',
            qty: 1
          }
        ]
      }
    });
 
    expect(refund.ok()).toBeTruthy();
 
    const refunditem = await refund.json();
    //Show the refund evidence for the refund scenario.
    evidence.refund = refunditem;
 

    //Assert the refund items are correctly calculated and prorated.
    expect(refunditem.amountPaise).toBe(34965);
    expect(refunditem.lineAmountPaise).toBe(33300);
    expect(refunditem.taxPaise).toBe(1665);
    expect(refunditem.taxShares[2]).toBe(1665);
    // expect(refund.refundId).toBe(8007);
 
    expect(
         refunditem.taxShares.reduce((sum: number, tax: number) => sum + tax, 0)
    ).toBe(4995);
 
    //Refresh the page and confirm the refunded quantity is reflected in the UI.
    await page.reload();

    //Assert the refunded quantity is correctly displayed
    await expect(page.getByTestId('refunded-TEE')).toHaveText('1');
    
  });


  // Make a full refund by creating an order, refunding it, and verifying the ledger and UI reflect the full refund.
  test('Full Refund', async ({ request, page, log, evidence }) => {

        log.info("Creating order");
        const order = await createOrder(request);
        

        const orderId = order.id;

        log.info("Refunding entire order");
        const refundResponse = await refundOrder(request, orderId, 3, generateIdempotencyKey("full"));

        expect(refundResponse.ok()).toBeTruthy();

        const refund = await refundResponse.json();
        evidence.refund = refund;

        expect(refund.amountPaise).toBe(104895);

        const ledger = await Ledger(request, orderId);
        evidence.ledger = ledger;

        log.info("Full refund completed", {
        status: ledger.status
    });

    //Assert the ledger reflects the correct status and amounts after the full refund.
        expect(ledger.status).toBe('REFUNDED');
        expect(ledger.refundableBalancePaise).toBe(0);
        expect(ledger.refundCount).toBe(1);
        expect(refund.lineAmountPaise).toBe(99900);
        expect(refund.taxPaise).toBe(4995);
        expect(ledger.lastRefund.amountPaise).toBe(104895);

        //Assert the UI reflects the refunded quantity and total after the full refund.
        await openReturnsPage(page, orderId);

        await expect(page.getByTestId('refunded-TEE')).toHaveText('3');

        await expect(page.getByTestId('refund-total')).toHaveText('₹1,048.95');

    });

  //Create a testCases for individual refund scenarios

    const testCases = [
    {
        name: 'Valid refund',
        qty: 1,
        expectedVerdict: 'APPROVED'
    },
    {
        name: 'Over refund',
        qty: 4,
        expectedVerdict: 'OVER_REFUND'
    },
    // {
    //     name: 'Already refunded',
    //     qty: 1,
    //     expectedVerdict: 'ALREADY_REFUNDED'
    // }
    ];
 

    // Run multiple refund verdict scenarios for valid and over-refund cases.
    for (const tc of testCases) {

    test(tc.name, async ({ request, evidence }) => {
 
        //Navigate to the refund page for the specific created order
        const orderResponse = await request.post('http://localhost:4000/api/refund-lab/orders', {
        headers: {
            Authorization: 'Bearer demo-token-1-customer',
            'Content-Type': 'application/json'
        },
        data: {
            taxPaise: 4995,
            lines: [
            {
                sku: 'TEE',
                name: 'Training Tee',
                unitPaise: 33300,
                qty: 3
            }
            ]
        }
        });

        expect(orderResponse.ok()).toBeTruthy();
 
        const order = await orderResponse.json();
        //Show the evidence of created order
        evidence.order = order;
 
        //Check the refund response by navgating to this page
        const checkResponse = await request.post('http://localhost:4000/api/refunds/check', {
        headers: {
            Authorization: 'Bearer demo-token-1-customer',
            'Content-Type': 'application/json'
        },
        data: {
            orderId: order.id,
            lines: [
            {
                sku: 'TEE',
                qty: tc.qty
            }
            ]
        }
        });
 
        expect(checkResponse.ok()).toBeTruthy();

        const result = await checkResponse.json();
        //Show the evidence of the refund check result
        evidence.result = result;

        //Assert the refund verdict matches the expected outcome for each scenario
        expect(result.verdict).toBe(tc.expectedVerdict);
 
    });
    }



//      
test('Money leak refusing the order', async ({ request, log, evidence }) => {

    log.info("Create an order");
    //Create an order for the refund scenario
    const orderRecieved = await createOrder(request);
    evidence.order = orderRecieved;

    const orderId = orderRecieved.id;

    //Check the ledger before attempting any refund
    const before = await Ledger(request, orderId);
    evidence.ledger = before;
    log.info("Attempting over refund");

    //Attempt to refund more than the refundable balance
    const overRefund = await refundOrder(request, orderId, 4, 'over-refund');
    expect(overRefund.status()).toBe(422);
    

    //Shiw the overRefund error evidence for the refund scenario
    const error = await overRefund.json();
    evidence.overRefundError = error;
    log.info("Over refund rejected", {
        reason: error.reason
    });

    //Assert the refund rejection reason and verdict are as expected
    expect(error.message).toBe('Refund rejected');
    // expect(error.reason).toBe('OVER_REFUND');
    expect(error.verdict).toBe('OVER_REFUND');

    //Check the ledger after the over-refund attempt to ensure no money was leaked
    const after = await Ledger(request, orderId);
    //Assert the refundable balance remains unchanged after the failed refund attempt
    expect(after.refundableBalancePaise).toBe(before.refundableBalancePaise);

    //Generate an idemotency key for the refund request 
    const idempotencyKey = generateIdempotencyKey();

    const refund1 = await refundOrder(request, orderId, 1, idempotencyKey);

    expect(refund1.ok()).toBeTruthy();

    const refund = await refund1.json();
    // evidence.refund = refund;

    const refund2 = await refundOrder(request, orderId, 1, idempotencyKey);

    expect(refund2.status()).toBe(200);
    const replay = await refund2.json();
    evidence.retryRefund = replay;

    expect(replay.refundId).toBe(refund.refundId);
    expect(replay.amountPaise).toBe(refund.amountPaise);

    const ledger = await Ledger(request, orderId);
    evidence.ledgerAfter = ledger;

    log.info("Verified idempotent refund", {
        refundCount: ledger.refundCount,
        balance: ledger.refundableBalancePaise
    });

    //Assert the ledger reflects the correct refund count and remaining refundable balance after the idempotent refund attempts
    expect(ledger.status).toBe('PARTIALLY_REFUNDED');
    expect(ledger.refundCount).toBe(1);
    expect(ledger.refundableBalancePaise).toBe(69930);
    expect(ledger.lastRefund.amountPaise).toBe(34965);

  });
 
});
 