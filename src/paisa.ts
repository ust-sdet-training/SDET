export function convertTo(amount: string): number {
  return Number(
    amount.trim()
      .replace(/[₹,\s]/g, "")
      .replace(".", "")      
  );
}
 