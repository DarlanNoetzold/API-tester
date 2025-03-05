# API-tester
This application is an API created to test other APIs, with security, performance and automated tests.

## Development:
* Java was used with SpringBoot;
* For security, a token system was implemented as a login;
* There are individual tests for POST, GET, DELETE and PUT requests;
* Each test request made will be saved in a PostgreSQL database;
* RestAssured was used to make requests with a Web Template.

## Project:
* The project has some features related to request testing;
* For security testing there are SQL Injection, Command Injection, XSS Injection, weak passwords and data validation tests;
* There is an integration with GPT-3 to check for API problems;
* It is possible to carry out performance tests with several parallel requests;
* It is also possible to automate tests, causing the application to send several requests to different endpoints;
* In these automated tests it is possible to define variables that are fed with responses from other requests, facilitating automation.

## Postman documentation:
[Click here](https://documenter.getpostman.com/view/16000387/2s93XsZnC7) to view an initial doc of requests for the API.

---
⭐️ From [DarlanNoetzold](https://github.com/DarlanNoetzold)
