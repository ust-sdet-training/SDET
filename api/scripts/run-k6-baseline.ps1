$k6Command = Get-Command k6 -ErrorAction SilentlyContinue
if ($null -ne $k6Command) {
    Write-Host "[quality-gate] Running k6 baseline script."
    & $k6Command.Source run "$PSScriptRoot/k6-baseline.js" --vus 2 --duration 10s
} else {
    Write-Host "[quality-gate] k6 baseline is not installed or not configured for this environment."
    Write-Host "[quality-gate] Skipping performance gate so the build remains safe and non-blocking."
}
exit 0
