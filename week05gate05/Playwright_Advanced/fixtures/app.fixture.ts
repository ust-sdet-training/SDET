import { test as diagnosticTest, expect } from "./diagnostic.fixture";
// Defining type of the evidance
type Evidence = Record<string, unknown>;

export const test = diagnosticTest.extend<{
  evidence: Evidence;
}>({
  // creating the evidance fixture
  evidence: async ({}, use, testInfo) => {
    const evidence: Evidence = {};
// give it to the test
    await use(evidence);
// loop for everything and ignoring undifined values
    for (const [name, value] of Object.entries(evidence)) {
      if (value === undefined) {
        continue;
      }
// checking wheather it is a text 
      const isText = typeof value === "string";
// attach evidance
      await testInfo.attach(
        `${name}.${isText ? "txt" : "json"}`,
        {
          body: isText
            ? value
            // converting object into JSON
            : JSON.stringify(value, null, 2), 
          contentType: isText
            ? "text/plain"
            : "application/json",
        }
      );
    }
  },
});

export { expect };