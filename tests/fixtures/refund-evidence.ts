
import { test as base }
from "@playwright/test";

type RefundEvidence = {

  refundRequest?: unknown;

  refundResponse?: unknown;

  ledgerBefore?: unknown;

  ledgerAfter?: unknown;

  eligibilityResult?: unknown;

  notes?: string;
};

export const test =
  base.extend<{

    evidence: RefundEvidence;

  }>({

    evidence:
      async ({}, use, testInfo) => {

        const evidence:
          RefundEvidence = {};

        await use(evidence);

        const attachJson =
          async (
            name: string,
            body: unknown
          ) => {

            await testInfo.attach(
              name,
              {
                body: JSON.stringify(
                  body,
                  null,
                  2
                ),

                contentType:
                  "application/json"
              }
            );
          };

        // Refund Request
        if (
          evidence.refundRequest
        ) {

          await attachJson(
            "refund-request.json",

            evidence.refundRequest
          );
        }

        // Refund Response
        if (
          evidence.refundResponse
        ) {

          await attachJson(
            "refund-response.json",

            evidence.refundResponse
          );
        }

        // Ledger Before
        if (
          evidence.ledgerBefore
        ) {

          await attachJson(
            "ledger-before.json",

            evidence.ledgerBefore
          );
        }

        // Ledger After
        if (
          evidence.ledgerAfter
        ) {

          await attachJson(
            "ledger-after.json",

            evidence.ledgerAfter
          );
        }

        // Eligibility Result
        if (
          evidence.eligibilityResult
        ) {

          await attachJson(
            "eligibility-result.json",

            evidence.eligibilityResult
          );
        }

        // Notes
        if (
          evidence.notes
        ) {

          await testInfo.attach(
            "notes.txt",
            {
              body:
                evidence.notes,

              contentType:
                "text/plain"
            }
          );
        }
      }
  });

