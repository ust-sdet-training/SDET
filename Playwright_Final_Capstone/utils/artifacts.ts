import type { Page, TestInfo } from '@playwright/test';
import { appendFile, mkdir, writeFile } from 'node:fs/promises';
import path from 'node:path';


export const createArtifactsFolder = async (
  testInfo: TestInfo,
): Promise<string> => {
  const testName = testInfo.title.replace(
    /[^a-zA-Z0-9._-]+/g,
    '-',
  );

  const artifactsFolder = path.resolve(
    'artifacts',
    'runs',
    testName,
  );

  await mkdir(artifactsFolder, { recursive: true });

  return artifactsFolder;
};


export const saveDiagnosticsLog = async (
  testInfo: TestInfo,
  message: string,
): Promise<string> => {
  const artifactsFolder =
    await createArtifactsFolder(testInfo);

  const logFilePath = path.join(
    artifactsFolder,
    'diagnostics.log',
  );

  await appendFile(logFilePath, message, 'utf8');

  return logFilePath;
};


export const saveStructuredTestLog = async (
  testInfo: TestInfo,
  logContent: string,
): Promise<string> => {
  const artifactsFolder =
    await createArtifactsFolder(testInfo);

  const logFilePath = path.join(
    artifactsFolder,
    'test-log.ndjson',
  );

  await writeFile(logFilePath, logContent, 'utf8');

  return logFilePath;
};

export const savePageSnapshot = async (
  page: Page,
  testInfo: TestInfo,
): Promise<string> => {
  const artifactsFolder =
    await createArtifactsFolder(testInfo);

  const snapshotFilePath = path.join(
    artifactsFolder,
    'page-snapshot.txt',
  );

  const pageText = await page
    .locator('body')
    .innerText()
    .catch(() => '');

  await writeFile(snapshotFilePath, pageText, 'utf8');

  return snapshotFilePath;
};

// Backward-compatible names used by existing fixtures.
export const writeDiagnostics = saveDiagnosticsLog;
export const writeStructuredTestLog = saveStructuredTestLog;
export const capturePageSnapshot = savePageSnapshot;
