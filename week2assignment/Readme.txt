I have Completed Api and Db Testing
My test follows the following Cases
I have covered-Create order to delete order (order lifecycle)
and Seperate function tests-Create order,get order,delete order Using Oauth
 i have covered the happy-path, edge cases,negatives status codes-200,201,204,404,401,403,409
 Then my testcases covered the api and db validation(asserts) in test life cycle

 I have seperated reusable datas to SpecFactory file and For Important data i used Config file

 I have create a seperate file for Db validations -LocalMySqlDbVAlidationDemo

 Container folder contains TestContainer file
 test folder contains all the tests
 spec folder have SpecFactory
 config folder have Config file for the entire tests.

 Xsd and Schema validation Also done
 for Xsd i have created seperate file and runned the test.-XMlschemaTest

 dbframework folder contains DB related COnfig ,model,support files

 In the AuthTest file i have writtened all the api testing and json schema validation and covered dbvalidation

 All the tests as been covered and tests are passing
 Total tests-18
 AuthTests-10
 LocalMySqlDbVAlidationDemo-6
 XmlschemaTest-1
 DbTest-1(for sql validation purpose)



