import { APIRequestContext } from "@playwright/test";

const posApiUrl = process.env.POS_API_URL || "http://localhost:4000";

export async function getCartTotal(
  request: APIRequestContext,
  delay: number
) {
  const response = await request.get(
    `${posApiUrl}/api/debug/cart-total?delay=${delay}`
  );

  return response;
}