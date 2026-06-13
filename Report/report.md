<h1>Retail Website Testing</h1>
<h2>Functions</h2>
<h3>CreateOrderNoToken()</h3>
Trying to create a secure order without token <strong>401</strong> error]
<br>
<h3>DeleteUnknownOrder()</h3>
Trying to delete an order that has not been placed [<strong>401</strong> error]
<br>
<h3>CreateOrderExpiredToken()</h3>
Trying to create a secure using an expired token [<strong>401</strong> error]
<br>
<h3>CreateOrderGarbageToken()</h3>
Trying to create a secure using a garbage token [<strong>401</strong> error]
<br>
<h3>CreateAllocateShipSecureOrderandVerifyDB()</h3>
Happy Path<br>
Creates, Allocates and Ships Order
<br>
Checks Status at each stages
<br>
Ensures the state is correct in Database
<br>
<h3>CreateOrderViewerToken()</h3>
Trying to create a secure using a viewer token with no permission to create[<strong>403</strong> error]
<br>
<h3>DBConnectTest()</h3>
Checking the connectivity of database
<br>
<h3>CheckXMLFormat()</h3>
Checking XML Schema using the <strong>/legacy</strong> api
<br>
<h3>ShipWithoutAllocation()</h3>
Trying to ship an order without allocating it [<strong>409</strong> error]

<h2>
Folders
</h2>
<strong>TEST</strong> --> Contains the main test <strong>SDETMainTest</strong>
<br>
<strong>spec</strong> --> Contains the necessary headers and assertions
<br>
<strong>DB</strong> --> Contains the necessary database connectivity and operations. OrderRow model for the database.
<br>
<strong>Containers</strong> --> Contains the docker container test environment
<br>
<strong>API</strong> --> Contains the api test functions for supporting the middle path

<h2>ENV KEYS REQUIRED</h2>
OAUTH_CLIENT_ID <br>
OAUTH_CLIENT_SECRET<br>
OAUTH_VIEWER_CLIENT_SECRET<br>
OAUTH_EXPIRED_CLIENT_SECRET<br>
DEMO_JWT_SECRET<br>
RETAIL_API_KEY<br>
DATABASE_URL<br>
DB_HOST<br>
DB_PORT<br>
DB_USER<br>
DB_PASSWORD<br>
DB_NAME<br>
