import { htmlReport } from 'https://jslib.k6.io/k6-html-report/0.0.2/index.js';

export function handleSummary(data) {
  return {
    'perf/k6/results/summary.html': htmlReport(data),
    'perf/k6/results/summary.json': JSON.stringify(data, null, 2)
  };
}
