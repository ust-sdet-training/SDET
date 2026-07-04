import { test as base } from "@playwright/test";

export type Evidence = Record<string, unknown>;

export const test = base.extend<{
  evidence: Evidence;
}>({
  evidence: async ({}, use, testInfo) => {
    const evidence: Evidence = {};

    await use(evidence);

    for (const [name, value] of Object.entries(evidence)) {
      await testInfo.attach(name, {
        body: JSON.stringify(value, null, 2),
        contentType: "application/json",
      });
    }
  },
});

export { expect } from "@playwright/test";