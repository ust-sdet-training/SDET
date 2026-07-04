import { test as base } from "@playwright/test";

type Evidence = {
  attachJson: (name: string, body: unknown) => Promise<void>;
  attachText: (name: string, body: string) => Promise<void>;
};

export const test = base.extend<{ evidence: Evidence }>({
  evidence: async ({}, use, testInfo) => {
    const attach = (name: string, body: string, contentType: string) =>
      testInfo.attach(name, { body, contentType });

    const evidence: Evidence = {
      attachJson: (name, body) => attach(name, JSON.stringify(body, null, 2), "application/json"),
      attachText: (name, body) => attach(name, body, "text/plain")
    };

    await use(evidence);
  }
});

export { expect } from "@playwright/test";