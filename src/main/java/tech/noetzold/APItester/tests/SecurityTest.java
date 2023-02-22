package tech.noetzold.APItester.tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import tech.noetzold.APItester.model.Result;
import tech.noetzold.APItester.util.TEST_TYPE;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.*;

public class SecurityTest {

    public Result testSecureResponse(RequestSpecification request, String url) {
        Response response = request.with().get(url);
        return new Result();
    }
}
