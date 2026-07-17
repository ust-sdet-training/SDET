import type { Page, TestInfo } from '@playwright/test';
import { mkdir, writeFile } from 'node:fs/promises';
import path from 'node:path';

export const ensureArtifactsDir = async (testInfo: TestInfo) => {
  const safeTitle = testInfo.title.replace(/[^a-zA-Z0-9._-]+/g, '-');
  const baseDir = path.resolve('artifacts', 'runs', safeTitle);
  await mkdir(baseDir, { recursive: true });
  return baseDir;
};

export const writeDiagnostics = async (testInfo: TestInfo, content: string) => {
  const dir = await ensureArtifactsDir(testInfo);
  const target = path.join(dir, 'diagnostics.log');
  await writeFile(target, content, 'utf8');
  return target;
};

export const capturePageSnapshot = async (page: Page, testInfo: TestInfo) => {
  const dir = await ensureArtifactsDir(testInfo);
  const snapshotPath = path.join(dir, 'page-snapshot.txt');
  const body = await page.locator('body').innerText().catch(() => '');
  await writeFile(snapshotPath, body, 'utf8');
  return snapshotPath;
};
