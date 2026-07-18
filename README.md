# TripStack Capstone

This repository contains:
- Playwright UI automation under [playwrightUI](playwrightUI)
- RestAssured + JUnit + JDBC automation under [APITesting](APITesting)
- A GitHub Actions workflow at [.github/workflows/tripstack-ci.yml](.github/workflows/tripstack-ci.yml)

## CI/CD
The workflow runs UI and API tests in parallel, publishes Playwright and Allure artifacts, and deploys a simple GitHub Pages bundle from the latest successful run on the main branch.

## Required GitHub secrets
Set these repository secrets before enabling the workflow:
- TRIPSTACK_EMAIL
- TRIPSTACK_PASSWORD
"# TrainerCapstone" 
