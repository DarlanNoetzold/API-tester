package tech.noetzold.APItester.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.Arrays;
import java.util.Base64;

import java.util.List;
import java.util.Map;

public class SecurityTest extends BaseTest{

    private String username;
    List<String> weakPasswords;

    public SecurityTest() {
        createUserAttributes();;
    }

    public Result testGetSecureResponse(RequestSpecification request, String url) {

        for(String weakPassword: this.weakPasswords) {
            String token = Base64.getEncoder().encodeToString((this.username + ":" + weakPassword).getBytes());
            Response responseBasic = request.header("Authorization", "Basic " + token)
                    .when()
                    .get(url);

            Result resultBasicTests = testStatusCode(responseBasic.getStatusCode());

            if(!("Success".equals(resultBasicTests.getDetails()))){
                return resultBasicTests;
            }

            Response responseBearer = request.header("Authorization", "Bearer " + token)
                    .when()
                    .get(url);

            return testStatusCode(responseBearer.getStatusCode());

        }

        return new Result(TEST_TYPE.SECURITY, "Success");
    }

    public Result testPostSecureResponse(RequestSpecification request, String url, Map<String, Object> body, Map<String, String> headers) {

        for(String weakPassword: this.weakPasswords) {
            String token = Base64.getEncoder().encodeToString((this.username + ":" + weakPassword).getBytes());
            Response responseBasic = request.header("Authorization", "Basic " + token)
                    .when()
                    .post(url);

            Result resultBasicTests = testStatusCode(responseBasic.getStatusCode());

            if(!("Success".equals(resultBasicTests.getDetails()))){
                return resultBasicTests;
            }

            Response responseBearer = request.header("Authorization", "Bearer " + token)
                    .when()
                    .post(url);

            return testStatusCode(responseBearer.getStatusCode());

        }

        return new Result(TEST_TYPE.SECURITY, "Success");
    }

    private Result testStatusCode(int statusCode){
        if (statusCode <= 300) {
            return fail(TEST_TYPE.SECURITY, "Security failed in token by weak password");
        } else if (statusCode >= 500) {
            return fail(TEST_TYPE.SECURITY, "Server error: " + statusCode);
        }
        return success(TEST_TYPE.SECURITY);
    }

    private void createUserAttributes(){
        this.username = "user";
        this.weakPasswords = Arrays.asList("123456", "password", "12345678", "qwerty", "12345", "123456789", "letmein", "1234567", "football", "iloveyou", "admin", "welcome", "monkey", "login", "abc123", "starwars", "123123", "dragon", "passw0rd", "master", "hello", "freedom", "whatever", "qazwsx", "trustno1", "654321", "jordan23", "harley", "password1", "1234");
    }

    public Result testPutSecureResponse(RequestSpecification request, String url, Map<String, Object> body, Map<String, String> headers) {
        for(String weakPassword: this.weakPasswords) {
            String token = Base64.getEncoder().encodeToString((this.username + ":" + weakPassword).getBytes());
            Response responseBasic = request.header("Authorization", "Basic " + token)
                    .when()
                    .put(url);

            Result resultBasicTests = testStatusCode(responseBasic.getStatusCode());

            if(!("Success".equals(resultBasicTests.getDetails()))){
                return resultBasicTests;
            }

            Response responseBearer = request.header("Authorization", "Bearer " + token)
                    .when()
                    .post(url);

            return testStatusCode(responseBearer.getStatusCode());

        }

        return new Result(TEST_TYPE.SECURITY, "Success");
    }

    public Result testDeleteSecureResponse(RequestSpecification request, String url) {
        for(String weakPassword: this.weakPasswords) {
            String token = Base64.getEncoder().encodeToString((this.username + ":" + weakPassword).getBytes());
            Response responseBasic = request.header("Authorization", "Basic " + token)
                    .when()
                    .delete(url);

            Result resultBasicTests = testStatusCode(responseBasic.getStatusCode());

            if(!("Success".equals(resultBasicTests.getDetails()))){
                return resultBasicTests;
            }

            Response responseBearer = request.header("Authorization", "Bearer " + token)
                    .when()
                    .get(url);

            return testStatusCode(responseBearer.getStatusCode());

        }

        return new Result(TEST_TYPE.SECURITY, "Success");
    }
}
