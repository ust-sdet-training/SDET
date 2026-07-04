import { test as diagnosticTest, expect } from "./diagnostic-test";

type Evidence = Record<string, unknown>;

export const test = diagnosticTest.extend<{
  evidence: Evidence;
}>({
  evidence: async ({}, use, testInfo) => {
    // Shared object used to collect evidence during test execution.
    const evidence: Evidence = {};

    await use(evidence);

    // Automatically attach all collected evidence after the test finishes.
    for (const [name, value] of Object.entries(evidence)) {
      if (value === undefined) {
        continue;
      }

      const isText = typeof value === "string";

      await testInfo.attach(`${name}.${isText ? "txt" : "json"}`, {
        body: isText ? value : JSON.stringify(value, null, 2),

        contentType: isText ? "text/plain" : "application/json",
      });
    }
  },
});

export { expect };
