Week 2 Gate 2 Assessment Report
 
Objective:
Order Lifecycle: Create -> Allocate -> Ship -> Fetch
Verify MySQL Database Connectivity --- PASSED
Verify Product Catalog Retrieval --- PASSED
Verify Cart Cleanup Before Test Execution --- PASSED
Verify Add Item To Cart --- PASSED
Verify Order Creation --- PASSED
Verify Order Data Persistence In Database --- PASSED
Verify Order Allocation --- PASSED
Verify Allocated Status In Database --- PASSED
Verify Order Shipment --- PASSED
Verify Shipped Status In Database --- PASSED
Verify Fetch Order By ID --- PASSED
Verify Order Cleanup After Execution --- PASSED
 
AuthenticationTest
Objective: Verify API security and access control.
Steps:
Generate customer token.
Access secured endpoints using valid token.
Verify successful authentication.
Access endpoints without token.
Access endpoints with invalid token.
Validate error responses.



ProductCatalogBaselineTest
Objective: Validate product catalog functionality.
Steps:
Send GET request to retrieve product details.
Verify status code is 200.
Validate product ID.
Validate product name.
Validate product price.
Validate response schema using Product.schema.json.
Validate product list schema using ProductList.schema.json.



CreateOrderTest
Objective: Validate successful order creation and database persistence.
Steps:
Verify database connectivity.
Generate authentication token.
Clear existing cart.
Add product to cart.
Verify cart update response.
Create order request.
Validate HTTP 201 Created response.
Validate response using Ops_OrderSchema (Partner.schema.json).
Verify order status is CREATED.
Extract generated Order ID.
Query MySQL database.
Verify order record exists.
Verify Order Number matches API response.
Verify Order Status matches API response.
Delete created test data after execution.



FlowTest (Ops_OrderLifeCycle)
Objective: Validate complete order lifecycle workflow.
Steps:
Authenticate customer.
Retrieve product catalog.
Select product.
Add product to cart.
Verify cart details.
Create order.
Validate order creation response.
Validate response against Ops_OrderLifeCycle schema.
Verify order status transitions.
Validate business workflow completion.
Verify order details in database.



NegativeAssertionsTest
Objective: Verify API behavior for invalid scenarios.
Steps:
Send requests with missing mandatory fields.
Send requests with invalid data.
Access secured APIs without token.
Access secured APIs with invalid token.
Access invalid resources.
Validate HTTP error codes.
Validate error messages.
Verify API stability under invalid requests.



Schema Validation
JSON Schemas Validated
Product.schema.json
ProductList.schema.json
Ops_OrderSchema (Partner.schema.json)
Ops_OrderLifeCycle Schema
Validation Checks
Required fields
Data types
Object structure
Array structure
Property constraints
XML Validation
product.xsd
Result: All API responses successfully matched their contract definitions.



Database Reconciliation
Validation Process
Create order through API.
Capture generated Order ID.
Retrieve corresponding record from MySQL database.
Compare API response with database values.
Fields Verified
Order ID
Order Number
Order Status
Record Existence