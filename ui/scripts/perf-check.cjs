const fs = require('node:fs');
const path = require('node:path');

const filePath = path.join(process.cwd(), 'test-results', 'artifacts', 'perf-baseline.json');
if (!fs.existsSync(filePath)) {
  console.log('No perf baseline found yet. Create one manually with a sample payload.');
  process.exit(0);
}

const baseline = JSON.parse(fs.readFileSync(filePath, 'utf8'));
const latest = baseline.latestMs ?? 0;
const threshold = baseline.thresholdMs ?? 8500;
if (latest > threshold) {
  console.error(`Performance regression detected: ${latest}ms > ${threshold}ms`);
  process.exit(1);
}
console.log(`Performance check passed: ${latest}ms <= ${threshold}ms`);
