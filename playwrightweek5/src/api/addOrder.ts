import { APIRequestContext, expect } from "@playwright/test";

export async function addOrder(
  request: APIRequestContext,
  sku: string,
  name:string,
  unitprice:number,
  qty: number,
  taxpaisa: number,


) {
  const response = await request.post('http://localhost:4000/api/refund-lab/orders', {
        headers: {
            Authorization: 'Bearer demo-token-1-customer',
            'Content-Type': 'application/json'
        },
        data: {
              taxPaise: taxpaisa,
              lines: [
                {
                  sku: sku,
                  name: name,
                  unitPaise: unitprice,
                  qty: qty
                }
              ]
            }
        });
        expect(response.ok()).toBeTruthy();

  return response;
}