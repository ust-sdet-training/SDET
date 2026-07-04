import { test as base } from "@playwright/test";

type Evidence = {
  attachJson: (name: string, body: unknown) => Promise<void>;
  attachText: (name: string, body: string) => Promise<void>;
};

export const test = base.extend<{ evidence: Evidence }>({
  evidence: async ({}, use, testInfo) => {
    await use({
      attachJson: async (name, body) => {
        await testInfo.attach(name, {
          body: JSON.stringify(body, null, 2),
          contentType: "application/json"
        });
      },
      attachText: async (name, body) => {
        await testInfo.attach(name, {
          body,
          contentType: "text/plain"
        });
      }
    });
  }
});

export { expect } from "@playwright/test";
