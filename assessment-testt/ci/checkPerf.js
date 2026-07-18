const fs = require("fs");

function readSummary(path) {
  const raw = fs.readFileSync(path, "utf-8");
  return JSON.parse(raw);
}

function extractMetrics(summary) {
  const metrics = summary.metrics || summary;
  // try common keys
  const durationMetric =
    metrics["http_req_duration"] || metrics.http_req_duration;
  const failedMetric = metrics["http_req_failed"] || metrics.http_req_failed;

  const p95 =
    (durationMetric &&
      ((durationMetric &&
        durationMetric.values &&
        durationMetric.values["p(95)"]) ||
        durationMetric["p(95)"])) ||
    null;
  const errorRate =
    (failedMetric &&
      ((failedMetric && failedMetric.values && failedMetric.values["rate"]) ||
        failedMetric["rate"])) ||
    null;

  return { p95, errorRate };
}

if (process.argv.length < 4) {
  console.error(
    "Usage: node checkPerf.js <currentSummary.json> <baseline.json>",
  );
  process.exit(2);
}

const current = readSummary(process.argv[2]);
const baseline = readSummary(process.argv[3]);

const cur = extractMetrics(current);
const base = extractMetrics(baseline);

if (!base.p95 || !base.errorRate) {
  console.error(
    "Baseline missing required metrics (p95 or errorRate). Provide a proper baseline.json",
  );
  process.exit(2);
}

if (!cur.p95 || cur.errorRate === null) {
  console.error("Current run missing required metrics");
  process.exit(2);
}

console.log("Baseline p95:", base.p95, "errorRate:", base.errorRate);
console.log("Current  p95:", cur.p95, "errorRate:", cur.errorRate);

const p95Threshold = base.p95 * 1.5; // allow 50% regression
const errorThreshold = Math.max(0.01, base.errorRate * 2); // allow error rate up to 2x but at least 1%

let failed = false;
if (cur.p95 > p95Threshold) {
  console.error(`PERF FAIL: p95 ${cur.p95} > threshold ${p95Threshold}`);
  failed = true;
}
if (cur.errorRate > errorThreshold) {
  console.error(
    `PERF FAIL: errorRate ${cur.errorRate} > threshold ${errorThreshold}`,
  );
  failed = true;
}

if (failed) process.exit(1);
console.log("Perf check passed");
process.exit(0);
