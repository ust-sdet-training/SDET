import { test, expect } from '../fixtures/artifacts';

import {seedOrder, refunds} from '../functions/refundFunctions'

var header ={

  "Authorization":"Bearer demo-token-1-customer",
  "content-type":"application/json"

}

test.describe('Tests invloving checking the refunding apis',()=>{
  test('Test for checking Full refund', async ({ evidence, page, log }) => {
    log.info("redirecting to the url")
    await page.goto('/returns');

    var payload = {"lines": [
                        {
                        "sku": "TEE",
                        "qty": 3
                        }
                    ]}

    log.info("creating an order",{payload})

    var response = await seedOrder(page.request,payload,header)
    var createdOrder = await response.json()

    evidence.apiResponse = createdOrder

    log.info("Created Order:-",{createdOrder})

    var order = {"orderId":createdOrder.id,
      "lines": [
                        {
                        "sku": "TEE",
                        "qty": 3
                        }
                    ]
    }

    log.info("creating a refund request:-",{order})

    evidence.apiResponse = createdOrder

    response = await refunds(page.request,order,header)

    evidence.apiResponse = await response.json()

    await page.goto(`/returns?orderId=${createdOrder.id}`)

    evidence.screenshot =   await page.screenshot({
                            fullPage: true
                        });
                          
   await expect.soft(page.getByTestId('refund-total')).toHaveText('₹1,048.95')


  });


  test('Test for checking Partial refund', async ({ evidence, page, log }) => {
    log.info("redirecting to the url")
    await page.goto('/returns');

    var payload = {"lines": [
                        {
                        "sku": "TEE",
                        "qty": 3
                        }
                    ]}

    log.info("creating an order",{payload})

    var response = await seedOrder(page.request,payload,header)
    var createdOrder = await response.json()

    evidence.apiResponse = createdOrder

    log.info("Created Order:-",{createdOrder})

    var order = {"orderId":createdOrder.id,
      "lines": [
                        {
                        "sku": "TEE",
                        "qty": 1
                        }
                    ]
    }

    log.info("creating a refund request:-",{order})

    evidence.apiResponse = createdOrder

    response = await refunds(page.request,order,header)

    evidence.apiResponse = await response.json()   

    await page.goto(`/returns?orderId=${createdOrder.id}`)

    evidence.screenshot =   await page.screenshot({
                            fullPage: true
                        });


    await expect.soft(page.getByTestId('refund-total')).toHaveText('₹349.65')



  });


  test('Test for checking No double refund', async ({ evidence, page, log }) => {
    log.info("redirecting to the url")
    await page.goto('/returns');

    var payload = {"lines": [
                        {
                        "sku": "TEE",
                        "qty": 3
                        }
                    ]}

    log.info("creating an order",{payload})

    var response = await seedOrder(page.request,payload,header)
    var createdOrder = await response.json()

    evidence.apiResponse = createdOrder

    log.info("Created Order:-",{createdOrder})

    var idempotencyHeader ={ "Idempotency-Key" : crypto.randomUUID()}

    var order = {"orderId":createdOrder.id,
      "lines": [
                        {
                        "sku": "TEE",
                        "qty": 1
                        }
                    ]
    }

    log.info("creating a refund request:-",{order})

    evidence.apiResponse = createdOrder

    response = await refunds(page.request,order,header,idempotencyHeader)

     log.info("creating a second refund request:-",{order})

    response = await refunds(page.request,order,header,idempotencyHeader)

    var refundResponse = await response.json()  

    evidence.apiResponse = refundResponse
    
    expect.soft(refundResponse.refundCount).toBe(1)


  });

  const testData = [
  { payload: {"lines": [
                        {
                        "sku": "TEE",
                        "qty": 4
                        }
                    ]}, responseexpect: 'OVER_REFUND' },
  { payload: {"lines": [
                        {
                        "sku": "TEE",
                        "qty": 3
                        }
                    ]}, responseexpect: 'ALREADY_REFUND' }
];



   testData.forEach(({ payload, responseexpect }) => {
      test(` Rejected refund test for ${responseexpect}`, async ({ evidence,page,request,log }) => {
              var response = await seedOrder(request,{},header)

              var createdOrder = await response.json()
  
              var idempotencyKey = crypto.randomUUID()

              log.info("first refund request",{payload})

              var response = await refunds(request,{"orderId":createdOrder.id,...payload},header,{"Idempotency-Key":idempotencyKey})

              log.info("second refund request",{payload})

              var response = await refunds(request,{"orderId":createdOrder.id,...payload},header,{"Idempotency-Key":idempotencyKey})

              var refundResponse = await response.json();

              evidence.apiResponse = refundResponse

              log.info("asserting refund status")

              expect.soft(refundResponse.verdict).toBe(responseexpect)
  
  
          });
      });
  })
