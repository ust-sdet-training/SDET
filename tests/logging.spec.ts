
import {expect,test} from "../fixtures/diagnostic-test";
import { redactForLog } from "../src/logger";
import { http } from "winston";

const postApiUrl=process.env.POS_API_URL || "http://localhost:4000";
const authHeaders={ Authorization:"Bearer demo-token-1-customer"};

test.describe("Diagnostics and Logging",()=>{
    test("records a correlated checkout diagnostic trail",async({correlationId,log,request,evidence})=>{
        const cartSession=`w5d2-diagnostics-${Date.now()}`;
        const headers={
            ...authHeaders,
            "X-Cart-Session":cartSession,
            "x-correlation-id":correlationId
        };
        log.info("checkout journey started",{cartSession});

        evidence.logs?.push("Checkout journey started");

        const addStart=Date.now();
        const addItem=await request.post(`${postApiUrl}/api/cart/items`,{
            headers,
            data:{
                productId:101,
                quantity:1,
                size:"UK 9",
                color:"Black",
                fulfilment:"Home delivery"
            }
        });
        log.info("cart item added",{
            cartSession,
            durationMs:Date.now()-addStart,
            httpStatus:addItem.status(),
            productId:101
        });
        evidence.logs?.push("Cart item added");
        //the status code should be 201 i given 200 so it failed after i changed to 201 then it is passing
        expect(addItem.status()).toBe(201);

        const cart=await request.get(`${postApiUrl}/api/cart`,{headers});
        const cartBody=await cart.json();
        evidence.cartResponse = cartBody;
        log.debug("cart snapshot loaded",{
            cartSession,
            httpStatus:cart.status(),
            itemCount:cartBody.items.length,
            total:cartBody.total
        });
        evidence.logs?.push("Cart snapshot loaded");
        expect(cart.status()).toBe(200);
        expect(cartBody.items).toHaveLength(1);
        expect(cartBody.total).toBeGreaterThan(0);

        const orderStart=Date.now();
        const order=await request.post(`${postApiUrl}/api/orders`,{
            headers,
            data:{
                address: "Block A, UST Campus, Trivandrum",
                coupon: "WELCOME",
                deliverySlot: "Tomorrow 1-AM - 1 PM",
                discount: 0,
                paymentMethod: "UPI",
                shipping: 99
            }
        });

        const orderBody=await order.json();
        log.info("order placed",{
            cartSession,
            durationMs:Date.now()-orderStart,
            httpStatus:order.status(),
            orderId:orderBody.id,
            orderNumber:orderBody.orderNumber,
            total:orderBody.total
        });
        evidence.logs?.push("Order placed");
        expect(order.status()).toBe(201)
        expect(orderBody.orderNumber).toMatch(/^ORD-/)
        expect(orderBody.status).toBe("Confirmed")
        expect(orderBody.total).toBe(cartBody.total+99)

        evidence.diagnosis =`Checkout completed successfully
        Order Number : ${orderBody.orderNumber}
        Status : ${orderBody.status}
        Total : ${orderBody.total}`;

    });
    test("redacts sensitive fields in structured logs",async ({log,evidence})=>{
        const payload={
            cardNumber: "411111111111",
            headers:{
                authorization:"Bearer should-not-leak"
            },
            orderId: "ORD-LOG-1001",
            token: "secret-token"
        };

        log.info("payment payload prepared",payload);
        evidence.logs?.push("Sensitive payload logged");

        evidence.diagnosis = "Sensitive information successfully redacted.";

        expect(redactForLog(payload)).toEqual({
            cardNumber: "[REDACTED]",
            headers: {
                authorization: "[REDACTED]"
            },
            orderId: "ORD-LOG-1001",
            token: "[REDACTED]"
        });
    });
});