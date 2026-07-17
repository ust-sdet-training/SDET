# k6 baseline workflow

1. Export BASE_URL to the API target.
2. Run `k6 run baseline.js`.
3. Review the summary and investigate regression spikes in duration or failure rate.
