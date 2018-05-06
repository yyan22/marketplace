What you’ll need:
JDK 1.8+
Maven 3.2+
Spring Boot 2.0.1+

Testing Environment:
Postman

You can run the application using ./mvnw spring-boot:run. Or you can build the JAR file with ./mvnw clean package. Then you can run the JAR file:

java -jar target/marketplace-0.1.0-SNAPSHOT.jar

High Level Requirement:

1. Create a Project:
http://localhost:8080/api/projects

Example JSON Input: 
{ "title":"test_title", "description": "test_description", "deadline": "2018-05-15"} 

2. Get a Project by ID. Returned fields should include the lowest bid amount, which should be calculated efficiently:
http://localhost:8080/api/projects/1

This will return a HashMap<Float, Project>, the key will be the lowest bid amount for the project.

3. Add a new Bid:
Step 1: Create a new Buyer account:
http://localhost:8080/api/buyers

Example JSON Input:
{ "firstName": "Jane", "lastName": "Doe"} 

Step 2: Create a new Bid with Buyer ID:
http://localhost:8080/api/projects/1/bids?buyerId=1

Example JSON Input: 
{ "amount": 2} 

Note: Please make sure you have created a Buyer before adding a new Bid.

4. The buyer with the lowest bid amount wins the bid when the deadline is reached:
This is implemented using task scheduler from java.util.concurrent package.
The main business logic resides in the createProject method inside ProjectController class.

More information about project implementation:

The time the exercise took (after dev environment is set up) : 6 hours

Exercise Difficulty: Moderate

How did you feel about the exercise itself? 9

How do you feel about coding an exercise as a step in the interview process?  10

What would you change in the exercise and/or process?

Maybe adding some more data structures, algorithms, design patterns, currency and transactions.

If you have any questions or concerns, please contact yanyyj@gmail.com.

Thank you!

