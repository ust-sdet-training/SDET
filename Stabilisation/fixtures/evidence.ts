import { test as base, type TestInfo } from "@playwright/test";

type Evidence = Record<string, unknown>;

function summarizeEvidence(value: unknown) {
  if (value === null || value === undefined) {
    return value;
  }

  if (typeof value === "string") {
    return value;
  }

  if (Array.isArray(value)) {
    return value;
  }

  if (typeof value !== "object") {
    return value;
  }

  const record = value as Record<string, unknown>;
  const selectedFields = Object.entries(record).filter(([key]) =>
    [
      "id",
      "status",
      "refundCount",
      "refundableBalancePaise",
      "amountPaise",
      "lineAmountPaise",
      "taxPaise",
      "verdict",
      "reason",
      "replayed",
    ].includes(key)
  );

  return Object.fromEntries(selectedFields);
}

export const test = base.extend<{
  evidence: Evidence;
}>({
  evidence: async ({}, use, testInfo: TestInfo) => {
    const evidence: Evidence = {};

    await use(evidence);

    for (const [name, value] of Object.entries(evidence)) {
      if (value === undefined) {
        continue;
      }

      const payload = summarizeEvidence(value);
      const isText = typeof payload === "string";

      await testInfo.attach(`${name}.${isText ? "txt" : "json"}`, {
        body: isText ? payload : JSON.stringify(payload, null, 2),
        contentType: isText ? "text/plain" : "application/json",
      });
    }
  },
});

export { expect } from "@playwright/test";

