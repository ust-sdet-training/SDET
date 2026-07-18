const fs = require('node:fs');
const path = require('node:path');
const url = require('node:url');
const https = require('node:https');
const http = require('node:http');

const baseURL = process.env.PLAYWRIGHT_BASE_URL ?? 'https://tripstack.doomple.com';
const samplePath = '/api/flights?from=BLR&to=HYD';
const thresholdMs = 8500;
const artifactsDir = path.resolve(process.cwd(), 'test-results', 'artifacts');
const outputFile = path.join(artifactsDir, 'perf-baseline.json');

function measureRequest(targetUrl) {
  return new Promise((resolve, reject) => {
    const parsed = url.parse(targetUrl);
    const client = parsed.protocol === 'https:' ? https : http;
    const options = {
      ...parsed,
      method: 'GET',
      headers: {
        accept: 'application/json',
        'user-agent': 'tripstack-ui-capstone-perf-generator',
      },
    };

    const start = Date.now();
    const req = client.request(options, (res) => {
      const chunks = [];
      res.on('data', (chunk) => chunks.push(chunk));
      res.on('end', () => {
        const body = Buffer.concat(chunks).toString('utf8');
        resolve({ status: res.statusCode, body, elapsedMs: Date.now() - start });
      });
    });

    req.on('error', (error) => reject(error));
    req.end();
  });
}

async function main() {
  if (!fs.existsSync(artifactsDir)) {
    fs.mkdirSync(artifactsDir, { recursive: true });
  }

  const targetUrl = `${baseURL.replace(/\/\s*$/, '')}${samplePath}`;
  console.log('Generating perf baseline from:', targetUrl);

  try {
    const result = await measureRequest(targetUrl);
    const baseline = {
      generatedAt: new Date().toISOString(),
      sampleUrl: targetUrl,
      method: 'GET',
      latestMs: result.elapsedMs,
      thresholdMs,
      status: result.status,
      statusBody: result.body,
    };

    fs.writeFileSync(outputFile, JSON.stringify(baseline, null, 2));
    console.log(`Perf baseline created at ${outputFile}`);
    console.log(`Latest response time: ${baseline.latestMs}ms, threshold: ${baseline.thresholdMs}ms`);

    if (baseline.status !== 200) {
      console.error(`Warning: baseline request returned HTTP ${baseline.status}. Check the target endpoint or credentials.`);
      process.exit(1);
    }
  } catch (error) {
    console.error('Perf baseline generation failed:', error.message || error);
    process.exit(1);
  }
}

main();
