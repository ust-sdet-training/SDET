import { APIRequestContext, expect } from "@playwright/test";
 
const posApiUrl = process.env.POS_API_URL || 'http://localhost:4000';

export async function orderItem(
  request: APIRequestContext,
  taxPaise: number,
  sku:string,
  name:string,
  unitPaise:number,
  qty:number,
) {
  const response = await request.post(`${posApiUrl}/api/refund-lab/orders`, {
    headers :{
                Authorization: 'Bearer demo-token-1-customer',
              "Content-Type" :"application/json"
            },
    data:{
  "taxPaise": taxPaise,
  "lines": [
    {
      "sku": sku,
      "name": name,
      "unitPaise": unitPaise,
      "qty": qty
    }
  ]
       }});
 
  return  response;
}
 