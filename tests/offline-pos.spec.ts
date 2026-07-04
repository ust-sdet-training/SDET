
import { test, expect }
from "@playwright/test";

test(
  "offline queue reconnect sync once",

  async ({ page }) => {

    // Open POS page
    await page.goto(
      "http://localhost:5173/pos"
    );

    // Go offline
    await page.context()
      .setOffline(true);

    // Verify offline banner state
    await expect(
      page.locator(
        '[data-testid="network-banner"]'
      )
    ).toHaveAttribute(
      "data-online",
      "false"
    );

    // Verify browser offline
    expect(
      await page.evaluate(
        () => navigator.onLine
      )
    ).toBe(false);

    // Reconnect
    await page.context()
      .setOffline(false);

    // Verify online state
    await expect(
      page.locator(
        '[data-testid="network-banner"]'
      )
    ).toHaveAttribute(
      "data-online",
      "true"
    );

    // Verify browser online
    expect(
      await page.evaluate(
        () => navigator.onLine
      )
    ).toBe(true);
  }
);

