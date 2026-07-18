#!/usr/bin/env bash
set -euo pipefail

BASE_URL="${BASE_URL:-https://tripstack.doomple.com}"
API_URL="${API_URL:-https://api.tripstack.doomple.com}"
REPORT_DIR="${REPORT_DIR:-security/zap/reports}"
mkdir -p "$REPORT_DIR"

docker run --rm -v "$PWD":/zap/wrk/:rw -t owasp/zap2docker-stable \
  zap-baseline.py -t "$BASE_URL" -t "$API_URL" -r "$REPORT_DIR/zap-report.html" -J "$REPORT_DIR/zap-report.json" || true
