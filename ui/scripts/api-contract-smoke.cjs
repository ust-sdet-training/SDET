const http = require('node:http');
const https = require('node:https');

function fetchText(url) {
  return new Promise((resolve, reject) => {
    const client = url.startsWith('https') ? https : http;
    client.get(url, (res) => {
      let data = '';
      res.on('data', (chunk) => { data += chunk; });
      res.on('end', () => resolve({ statusCode: res.statusCode, body: data }));
    }).on('error', reject);
  });
}

(async () => {
  const url = process.env.PLAYWRIGHT_BASE_URL || 'https://tripstack.doomple.com';
  const specUrl = `${url}/openapi.yaml`;
  const response = await fetchText(specUrl);
  if (response.statusCode !== 200 || !String(response.body).includes('openapi')) {
    console.error(`OpenAPI contract check failed for ${specUrl}`);
    process.exit(1);
  }
  console.log(`OpenAPI contract reachable: ${specUrl}`);
})().catch((error) => {
  console.error(error);
  process.exit(1);
});
