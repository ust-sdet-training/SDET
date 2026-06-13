API Automation Testing Report

Overview
In this project, I developed an API automation framework for a Retail Application using Java and Rest Assured. The goal was to automate API testing and validate that the application behaves correctly under different scenarios such as authentication, cart operations, product retrieval, order creation, and database verification.

Design
The project was organized into separate folders to keep the framework clean and reusable.
•	auth – handles login and OAuth token generation
•	config – stores environment and database configuration
•	models – stores request body models
•	specs – reusable request and response specifications
•	tests – contains all test cases
•	schemas – contains JSON schemas and XML validation files
•	dbframework – used for database connectivity and validation

Testing Performed
The following API scenarios were tested:
•	Authentication – Login validation, invalid login handling, OAuth token validation
•	Products – Product retrieval, response validation, XML API testing
•	Cart – Add, update, delete, and negative scenario testing
•	Orders – Order creation, secure order flow, and database validation
Validation Methods
•	Status code validation
•	Response validation
•	JSON/XML schema validation
•	Database validation

Conclusion
This project helped me learn API automation, framework design, authentication handling, schema validation, and database testing using Rest Assured.

