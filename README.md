# SDET
Repository for SDET training batch
#Week2_Gate2_Test
RequestSpecFactory.java
This file is like a reusable for API calls. Instead of writing the server address, login token, and content format every single time, you define it once here and reuse it. It is used for Request
ResponseSpecFactory.java
This is also a Reusable but in a response side
Gate2test.java
It is the main Test file it showing the end-to-end flow of the test from -create-allocate-ship-get with also validating the results
EdgeCases.java
This file showing 409 error and catches the error also validating the invalid token,expiry token,missing token.
order.schema.json
Order schema shows the shape of response used to validate the response.
