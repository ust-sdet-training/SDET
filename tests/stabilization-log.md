```md id="t5"
# Stabilization Fix Log

| Symptom | Root Cause | Fix | Evidence |
|---|---|---|---|
| Partial refund mismatch | Tax proration issue | Paise-safe calculation | refund-response.json |
| Over refund allowed | Missing qty validation | Added OVER_REFUND validation | ledger-after.json |
| Duplicate payout | Missing idempotency | Added Idempotency-Key | refund-response.json |
| Refund balance mismatch | Ledger inconsistency | Added ledger assertions | ledger-before.json |
```
