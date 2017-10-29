# Introduction
This is the mathematics calculation. Currently it supports addition, subtraction, multiplication and division.

* This is the spring boot restful application.
* Gradle is being used to build this service.
* lombok is being used to build all the java classes.

## How to run this application

* Navigate the the root folder /calculation-test under the command line
* run the below gradle command to build the whole project: **gradle clean install**

* run the below gradle command to start the spring boot application: **gradle bootRun**



## How to access the spring boot restful application
* You may use postman or soapUI to access the endpoint
The endpoint providing the calculation function is exposed. The details is as follows:<br>
URL: /calculation<br>
METHOD: POST<br>
PAYLOAD:  {
          	"operand1":4.7,
          	"operation":"+",
          	"operand2":2
          }<br>
ContentType: application/json<br>
Accept: application/json<br>


## Advantages of this application
### Super easy to extend functions
If you want to add one more calculation let's say Modulo function, There is nothing to do at all except add one class which just simply implements the interface FunctionInterface.
All the other business logic can be reused.
### All the exceptions are handle in one place
When we develop our business logic classes, we just simply need to throw the exception directly. Don't need to worries about how to handle it in our main business logic. All the exceptions are handled in ControllerExceptionHandler class.
### Lombok makes our life easier
Lombok is being used to make our java classes are much simpler and easy to change. It automatically generates getter,setter, constructor, hashcode etc.


