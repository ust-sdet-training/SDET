API Automation + DB Validation Framework Documentation
Overview
This project is an API automation framework built using RestAssured and JUnit 5. It also includes database validation using JDBC/Testcontainers.
The main goal of this framework is to validate API responses, database data, security rules, and complete business workflows.
________________________________________
Project Structure
The framework is divided into two main parts:
API Framework
This layer handles all API-related operations.
•	BaseTest is used to set the base URL and common configuration. 
•	ConfigManager is used to read environment values like keys
•	TokenManager handles authentication tokens such as Ops token, Viewer token, and Expired token. 
•	AllSpec contains reusable request and response specifications. 
•	ModelObject is used to create request payloads. 
________________________________________
DB Framework
This layer is used for database validation.
•	DatabaseConfig is used to load database connection details from environment. 
•	DBSupport is used to perform database operations like fetch and delete. 
•	OrderRow represents the database table structure. 
•	DbAssertions is used to validate database values like status and existence. 
•	TestEnvironment is used for database setup and configuration. 
________________________________________
Environment Setup
An .env file is used to store sensitive information such as base URL and database credentials.
All configurations are loaded dynamically using ConfigManager and DatabaseConfig classes.
________________________________________
Test Scenarios
1. Create Order (Positive Flow)
This test creates a new order using the Ops token.
Steps:
•	Send POST request to /api/secure/orders 
•	Validate response status code is 201 
•	Validate order status is CREATED 
•	Validate JSON schema 
Purpose:
To verify that order creation API is working correctly.
________________________________________
2. Get Order
This test fetches an existing order using order ID.
Steps:
•	Send GET request with order ID 
•	Validate response status code is 200 
•	Validate correct order ID is returned 
________________________________________
3. Security Test Cases
This includes authentication and authorization testing.
•	Request without token returns 401 Unauthorized 
•	Request with expired token returns 401 Unauthorized 
•	Viewer token trying to create order returns 403 Forbidden 
Purpose:
To ensure API security is working correctly.
________________________________________
4. Customer Flow (End-to-End Flow)
This test simulates a real customer workflow.
Steps:
•	Login and get user token 
•	Clear cart 
•	Add item to cart 
•	Create order 
Purpose:
To validate full business flow from customer side.
________________________________________
5. Order Lifecycle Flow (API + DB Validation)
This is a complete end-to-end validation flow.
Steps:
•	Create order (status CREATED) 
•	Validate data in database 
•	Allocate order (status ALLOCATED) 
•	Validate database update 
•	Ship order (status SHIPPED) 
•	Final API validation 
Purpose:
To ensure API and database are always in sync and business flow is correct.
________________________________________
6. Negative Scenario
This test validates business rules.
Scenario:
Trying to ship an order before allocation.
Expected result:
•	Status code 409 Conflict 
•	Error message returned 
•	Order remains in CREATED state 
Purpose:
To ensure invalid state transitions are not allowed.
________________________________________
JSON Schema Validation
All create order responses are validated using JSON schema.
This ensures that the API response structure does not change unexpectedly.
________________________________________
Cleanup Process
After each test execution:
•	Created order IDs are stored in a list 
•	AfterEach method deletes those records from the database 
•	This ensures the test environment is clean every time 
________________________________________
Execution Flow
•	Get authentication token 
•	Send API request 
•	Validate response 
•	Validate database state 
•	Cleanup test data 
________________________________________

•	API and database both are validated 
•	Positive and negative test cases are covered 
•	Security testing is included 
•	End-to-end business flow is tested 
•	Clean and reusable framework design is implemented 
________________________________________
Conclusion
This framework ensures that the API system is working correctly, database data is consistent, and business rules are properly enforced. It also helps in maintaining stable and reliable test automation for the order management system.
________________________________________


