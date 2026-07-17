const fs = require('fs');
const path = require('path');

const artifactsRoot = path.resolve(__dirname, '..', 'test-results', 'artifacts');
const indexFile = path.resolve(artifactsRoot, 'artifact-index.json');

function collectArtifacts(dir) {
  const entries = [];
  for (const name of fs.readdirSync(dir, { withFileTypes: true })) {
    const fullPath = path.join(dir, name.name);
    if (name.isDirectory()) {
      entries.push(...collectArtifacts(fullPath));
      continue;
    }
    const relative = path.relative(artifactsRoot, fullPath).replace(/\\/g, '/');
    const stat = fs.statSync(fullPath);
    entries.push({
      path: relative,
      size: stat.size,
      modifiedAt: stat.mtime.toISOString(),
    });
  }
  return entries;
}

if (!fs.existsSync(artifactsRoot)) {
  console.error('Artifacts directory not found:', artifactsRoot);
  process.exit(1);
}

const artifacts = collectArtifacts(artifactsRoot);
fs.writeFileSync(indexFile, JSON.stringify({ generatedAt: new Date().toISOString(), artifacts }, null, 2));
console.log(`Artifact index written to ${indexFile}, total ${artifacts.length} files.`);
